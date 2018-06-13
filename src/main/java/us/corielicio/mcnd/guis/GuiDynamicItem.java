package us.corielicio.mcnd.guis;

import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;
import us.corielicio.mcnd.Mcnd;
import us.corielicio.mcnd.containers.ContainerDynamicItem;
import us.corielicio.mcnd.packets.PacketDynamicItemRename;

import java.io.IOException;

public class GuiDynamicItem extends GuiContainer implements IContainerListener {
  private static final ResourceLocation BG_TEXTURE = new ResourceLocation(Mcnd.MODID, "textures/gui/dynamic_item.png");

  private final ContainerDynamicItem container;
  private GuiTextField txtName;

  public GuiDynamicItem(final ContainerDynamicItem container) {
    super(container);
    this.container = container;
  }

  @Override
  public void initGui() {
    super.initGui();
    Keyboard.enableRepeatEvents(true);
    this.txtName = new GuiTextField(0, this.fontRenderer, this.guiLeft + 28, this.guiTop + this.fontRenderer.FONT_HEIGHT + 10, 103, 12);
    this.txtName.setTextColor(0xFFFFFFFF);
    this.txtName.setDisabledTextColour(0xFFFFFFFF);
    this.txtName.setMaxStringLength(35);
    this.container.removeListener(this);
    this.container.addListener(this);
  }

  @Override
  public void onGuiClosed() {
    super.onGuiClosed();
    Keyboard.enableRepeatEvents(false);
    this.container.removeListener(this);
  }

  @Override
  public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
    this.drawDefaultBackground();

    super.drawScreen(mouseX, mouseY, partialTicks);

    GlStateManager.disableLighting();
    GlStateManager.disableBlend();
    this.txtName.drawTextBox();

    this.renderHoveredToolTip(mouseX, mouseY);

    if(mouseX >= this.guiLeft + 8 && mouseX <= this.guiLeft + 24) {
      if(mouseY >= this.guiTop + 16 && mouseY <= this.guiTop + 32) {
        this.renderToolTip(I18n.format("gui_dynamic_item.item_name"), mouseX, mouseY);
      }
    }

    if(mouseX >= this.guiLeft + 8 && mouseX <= this.guiLeft + 24) {
      if(mouseY >= this.guiTop + 36 && mouseY <= this.guiTop + 52) {
        this.renderToolTip(I18n.format("gui_dynamic_item.item_sprite"), mouseX, mouseY);
      }
    }
  }

  @Override
  protected void drawGuiContainerBackgroundLayer(final float partialTicks, final int mouseX, final int mouseY) {
    GlStateManager.color(1, 1, 1, 1);
    this.mc.getTextureManager().bindTexture(BG_TEXTURE);
    final int x = (this.width - this.xSize) / 2;
    final int y = (this.height - this.ySize) / 2;
    this.drawTexturedModalRect(x, y, 0, 0, this.xSize, this.ySize);
  }

  @Override
  protected void drawGuiContainerForegroundLayer(final int mouseX, final int mouseY) {
    final String name = I18n.format("gui_dynamic_item.name");
    this.fontRenderer.drawString(name, this.xSize / 2 - this.fontRenderer.getStringWidth(name) / 2, 6, 0x404040);
  }

  private void renderToolTip(final String text, final int x, final int y) {
    this.drawHoveringText(text, x, y);
  }

  @Override
  protected void keyTyped(final char typedChar, final int keyCode) throws IOException {
    if(this.txtName.textboxKeyTyped(typedChar, keyCode)) {
      this.renameItem();
    } else {
      super.keyTyped(typedChar, keyCode);
    }
  }

  private void renameItem() {
    final String s = this.txtName.getText();

    this.container.getOutput().setStackDisplayName(s);
    PacketDynamicItemRename.send(s);
  }

  @Override
  protected void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
    super.mouseClicked(mouseX, mouseY, mouseButton);
    this.txtName.mouseClicked(mouseX, mouseY, mouseButton);
  }

  @Override
  public void sendAllContents(final Container containerToSend, final NonNullList<ItemStack> itemsList) {
    this.sendSlotContents(containerToSend, 0, containerToSend.getSlot(0).getStack());
  }

  @Override
  public void sendSlotContents(final Container containerToSend, final int slotInd, final ItemStack stack) {
    if(slotInd == 0) {
      this.txtName.setText(stack.hasDisplayName() ? "" : stack.getDisplayName());
      this.txtName.setEnabled(!stack.isEmpty());

      if(!stack.isEmpty()) {
        this.renameItem();
      }
    }
  }

  @Override
  public void sendWindowProperty(final Container containerIn, final int varToUpdate, final int newValue) {
  }

  @Override
  public void sendAllWindowProperties(final Container containerIn, final IInventory inventory) {
  }
}
