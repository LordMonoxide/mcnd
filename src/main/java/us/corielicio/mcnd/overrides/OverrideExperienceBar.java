package us.corielicio.mcnd.overrides;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import us.corielicio.mcnd.Mcnd;
import us.corielicio.mcnd.characters.CapabilityCharacterSheet;
import us.corielicio.mcnd.characters.CharacterSheet;

import static net.minecraft.client.gui.Gui.ICONS;

@Mod.EventBusSubscriber(modid=Mcnd.MODID, value=Side.CLIENT)
public class OverrideExperienceBar {
  private OverrideExperienceBar() { }

  @SubscribeEvent
  public static void onRenderHud(final RenderGameOverlayEvent event) {
    if(event.getType() == RenderGameOverlayEvent.ElementType.EXPERIENCE) {
      event.setCanceled(true);
      renderExperience(event.getResolution().getScaledWidth(), event.getResolution().getScaledHeight());
    }
  }

  private static void renderExperience(final int width, final int height) {
    final Minecraft mc = Minecraft.getMinecraft();
    final GuiIngame gui = mc.ingameGUI;
    final FontRenderer font = gui.getFontRenderer();

    final CharacterSheet stats = mc.player.getCapability(CapabilityCharacterSheet.CHARACTER_SHEET_CAPABILITY, null);

    mc.getTextureManager().bindTexture(ICONS);
    GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    GlStateManager.disableBlend();

    if(mc.playerController.gameIsSurvivalOrAdventure()) {
      mc.mcProfiler.startSection("expBar");
      final int cap = stats.nextLevelExp();

      if(cap > 0) {
        final short barWidth = 182;
        final int filled = stats.exp() * (barWidth + 1);
        final int left = width / 2 - 91;
        final int top = height - 32 + 3;
        gui.drawTexturedModalRect(left, top, 0, 64, barWidth, 5);

        if(filled > 0) {
          gui.drawTexturedModalRect(left, top, 0, 69, filled, 5);
        }
      }

      mc.mcProfiler.endSection();

      if(mc.playerController.gameIsSurvivalOrAdventure()) {
        mc.mcProfiler.startSection("expLevel");
        final String text = String.valueOf(stats.level());
        final int x = (width - font.getStringWidth(text)) / 2;
        final int y = height - 31 - 4;
        font.drawString(text, x + 1, y, 0);
        font.drawString(text, x - 1, y, 0);
        font.drawString(text, x, y + 1, 0);
        font.drawString(text, x, y - 1, 0);
        final int color = 8453920;
        font.drawString(text, x, y, color);
        mc.mcProfiler.endSection();
      }
    }

    GlStateManager.enableBlend();
    GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
  }
}
