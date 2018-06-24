package us.corielicio.mcnd.guis;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import us.corielicio.mcnd.Mcnd;
import us.corielicio.mcnd.characters.CapabilityCharacterSheet;
import us.corielicio.mcnd.characters.CharacterSheet;
import us.corielicio.mcnd.characters.Stats;

@SideOnly(Side.CLIENT)
public class GuiCharacterSheet extends GuiScreen {
  private static final ResourceLocation BG_TEXTURE = new ResourceLocation(Mcnd.MODID, "textures/guis/character_sheet.png");
  private static final int BG_WIDTH = 176;
  private static final int BG_HEIGHT = 166;

  private final EntityPlayer player;

  private int guiX;
  private int guiY;

  public GuiCharacterSheet(final EntityPlayer player) {
    this.player = player;
  }

  @Override
  public void initGui() {
    this.guiX = (this.width - BG_WIDTH) / 2;
    this.guiY = (this.height - BG_HEIGHT) / 2;
  }

  @Override
  public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
    this.drawDefaultBackground();

    GlStateManager.color(1, 1, 1, 1);
    this.mc.getTextureManager().bindTexture(BG_TEXTURE);
    this.drawTexturedModalRect(this.guiX, this.guiY, 0, 0, BG_WIDTH, BG_HEIGHT);

    GlStateManager.pushMatrix();
    GlStateManager.translate(this.guiX, this.guiY, 0.0F);

    this.fontRenderer.drawString(I18n.format("stats"), 8, 8, 0x404040);

    final CharacterSheet stats = this.player.getCapability(CapabilityCharacterSheet.CHARACTER_SHEET_CAPABILITY, null);

    for(final Stats stat : Stats.values()) {
      this.fontRenderer.drawString(I18n.format("stats.display", stats.stat(stat).raw(), stats.stat(stat).bonus(), stat.name()), 8, 8 + (stat.ordinal() + 1) * this.fontRenderer.FONT_HEIGHT, 0x404040);
    }

    GlStateManager.popMatrix();

    super.drawScreen(mouseX, mouseY, partialTicks);
  }
}
