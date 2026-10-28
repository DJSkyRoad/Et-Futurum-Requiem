package ganymedes01.etfuturum.client.gui.inventory;

import ganymedes01.etfuturum.core.utils.Logger;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerMerchant;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import net.minecraft.util.ResourceLocation;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class GuiModernMerchant extends GuiContainer {

    public static final ResourceLocation TEXTURE = new ResourceLocation("textures/gui/container/villager2.png");

    private IMerchant merchant;
    private String title;

    private final ContainerMerchant container;

    public GuiModernMerchant(InventoryPlayer player, IMerchant merchant, World world, String title) {
        super(new ContainerMerchant(player, merchant, world));
        this.merchant = merchant;
        this.title = title != null && !title.isEmpty() ? title : I18n.format("entity.Villager.name", new Object[0]);

        this.xSize = 276;
        this.ySize = 166;

        this.container = (ContainerMerchant) this.inventorySlots;

        updateGuiPositions();
    }

    private void updateGuiPositions() {
        this.container.inventorySlots.get(0).xDisplayPosition = 136;
        this.container.inventorySlots.get(0).yDisplayPosition = 37;

        this.container.inventorySlots.get(1).xDisplayPosition = 162;
        this.container.inventorySlots.get(1).yDisplayPosition = 37;

        this.container.inventorySlots.get(2).xDisplayPosition = 220;
        this.container.inventorySlots.get(2).yDisplayPosition = 37;

        for (int i = 3; i <= 38; i++) {
            this.container.inventorySlots.get(i).xDisplayPosition += 100;
        }
    }

    public IMerchant getMerchant() {
        return merchant;
    }

    @Override
    public void initGui() {
        super.initGui();
        for (int i = 0; i < 7; i++) {
            this.buttonList.add(new GuiModernMerchant.
                    GuiTradeListButton(i, this.guiLeft + 5, this.guiTop + 18 + i * 20, ""));
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(TEXTURE);

        func_146110_a(guiLeft, guiTop, 0, 0, this.xSize, this.ySize, 512, 256);

        MerchantRecipeList merchantrecipelist = this.merchant.getRecipes(this.mc.thePlayer);

        if (merchantrecipelist != null && !merchantrecipelist.isEmpty()) {

        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        this.fontRendererObj.drawString(this.title, 187 - this.fontRendererObj.getStringWidth(this.title) / 2, 6, 4210752);

        this.fontRendererObj.drawString(I18n.format("container.inventory"), 107, 72, 4210752);

        String trades = I18n.format("merchant.trades");
        this.fontRendererObj.drawString(trades, 53 - this.fontRendererObj.getStringWidth(trades) / 2, 6, 4210752);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);

        MerchantRecipeList merchantrecipelist = merchant.getRecipes(this.mc.thePlayer);
        if (merchantrecipelist == null) {
            return;
        }

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        RenderHelper.disableStandardItemLighting();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(TEXTURE);


        for (int i = 0; i < 7; i++) {
            if (i >= merchantrecipelist.size()) {
                break;
            }
            int x = guiLeft + 60;
            int y = guiTop + 22 + i * 20;


            MerchantRecipe merchantrecipe = (MerchantRecipe)merchantrecipelist.get(i);
            int u = merchantrecipe.isRecipeDisabled() ? 25 : 15;

            func_146110_a(x, y, u, 171, 10, 9, 512, 256);


        }

        GL11.glPushMatrix();
        RenderHelper.enableGUIStandardItemLighting();
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glEnable(GL11.GL_COLOR_MATERIAL);
        GL11.glEnable(GL11.GL_LIGHTING);
        itemRender.zLevel = 100.0F;

        for (int i = 0; i < 7; i++) {
            if (i >= merchantrecipelist.size()) {
                break;
            }
            int x = guiLeft;
            int y = guiTop + 19 + i * 20;

            MerchantRecipe merchantrecipe = (MerchantRecipe)merchantrecipelist.get(i);


            ItemStack firstCost = merchantrecipe.getItemToBuy();
            ItemStack secondCost = merchantrecipe.getSecondItemToBuy();
            ItemStack result = merchantrecipe.getItemToSell();


            itemRender.renderItemAndEffectIntoGUI(this.fontRendererObj, this.mc.getTextureManager(), firstCost, x + 10, y);
            itemRender.renderItemOverlayIntoGUI(this.fontRendererObj, this.mc.getTextureManager(), firstCost, x + 10, y);

            if (secondCost != null) {
                itemRender.renderItemAndEffectIntoGUI(this.fontRendererObj, this.mc.getTextureManager(), secondCost, x + 40, y);
                itemRender.renderItemOverlayIntoGUI(this.fontRendererObj, this.mc.getTextureManager(), secondCost, x + 40, y);
            }

            itemRender.renderItemAndEffectIntoGUI(this.fontRendererObj, this.mc.getTextureManager(), result, x + 73, y);
            itemRender.renderItemOverlayIntoGUI(this.fontRendererObj, this.mc.getTextureManager(), result, x + 73, y);

        }
        itemRender.zLevel = 0.0F;
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glPopMatrix();
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        RenderHelper.enableStandardItemLighting();
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        container.setCurrentRecipeIndex(button.id);
        ByteBuf bytebuf = Unpooled.buffer();

        try {
            bytebuf.writeInt(button.id);
            this.mc.getNetHandler().addToSendQueue(new C17PacketCustomPayload("MC|TrSel", bytebuf));
        }
        catch (Exception exception) {
            Logger.error(exception);
        }
        finally {
            bytebuf.release();
        }
    }

    private class GuiTradeListButton extends GuiButton {

        public GuiTradeListButton(int buttonId, int x, int y, String buttonText) {
            super(buttonId, x, y, 89, 20, buttonText);
        }

        @Override
        public void drawButton(Minecraft mc, int mouseX, int mouseY) {
            MerchantRecipeList tradingList = merchant.getRecipes(mc.thePlayer);

            if (tradingList != null && this.id < tradingList.size()) {
                visible = true;
            } else {
                visible = false;
            }
            super.drawButton(mc, mouseX, mouseY);
        }
    }
}
