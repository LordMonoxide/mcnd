package us.corielicio.mcnd.guis.controls;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;

public class Icon {
  private final GuiContainer gui;
  private final int x;
  private final int y;
  private final ItemStack icon;
  private final String tooltip;

  public Icon(final GuiContainer gui, final int x, final int y, final ItemStack icon, final String tooltip) {
    this.gui = gui;
    this.x = x;
    this.y = y;
    this.icon = icon;
    this.tooltip = tooltip;
  }

  public void draw() {
    Minecraft.getMinecraft().getRenderItem().renderItemAndEffectIntoGUI(this.icon, this.x, this.y);
  }

  public void drawTooltip(final int mouseX, final int mouseY) {
    if(mouseX >= this.gui.getGuiLeft() + this.x && mouseX <= this.gui.getGuiLeft() + this.x + 16) {
      if(mouseY >= this.gui.getGuiTop() + this.y && mouseY <= this.gui.getGuiTop() + this.y + 16) {
        this.gui.drawHoveringText(this.tooltip, mouseX, mouseY);
      }
    }
  }
}
