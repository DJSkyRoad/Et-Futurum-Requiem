package ganymedes01.etfuturum.client.gui.inventory;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerMerchant;
import net.minecraft.util.ResourceLocation;
import net.minecraft.village.MerchantRecipeList;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

public class GuiModernMerchant extends GuiContainer {

    public static final ResourceLocation TEXTURE = new ResourceLocation("textures/gui/container/villager2.png");

    private IMerchant merchant;
    private String title;

    public GuiModernMerchant(InventoryPlayer player, IMerchant merchant, World world, String title) {
        super(new ContainerMerchant(player, merchant, world));
        this.merchant = merchant;
        this.title = title;

        this.xSize = 276;
        this.ySize = 166;

        updateGuiPositions();
    }

    private void updateGuiPositions() {
        this.inventorySlots.inventorySlots.get(0).xDisplayPosition = 136;
        this.inventorySlots.inventorySlots.get(0).yDisplayPosition = 37;

        this.inventorySlots.inventorySlots.get(1).xDisplayPosition = 162;
        this.inventorySlots.inventorySlots.get(1).yDisplayPosition = 37;

        this.inventorySlots.inventorySlots.get(2).xDisplayPosition = 220;
        this.inventorySlots.inventorySlots.get(2).yDisplayPosition = 37;

        for (int i = 3; i <= 38; i++) {
            this.inventorySlots.inventorySlots.get(i).xDisplayPosition += 100;
        }
    }

    public IMerchant getMerchant() {
        return merchant;
    }

    @Override
    public void initGui() {
        super.initGui();
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(TEXTURE);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        //this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
        func_146110_a(guiLeft, guiTop, 0, 0, this.xSize, this.ySize, 512, 256);

        MerchantRecipeList merchantrecipelist = this.merchant.getRecipes(this.mc.thePlayer);

        if (merchantrecipelist != null && !merchantrecipelist.isEmpty()) {

        }
    }
}
