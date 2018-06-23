package us.corielicio.mcnd.characters;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import us.corielicio.mcnd.Mcnd;

@Mod.EventBusSubscriber(modid = Mcnd.MODID)
public class CharacterSheetEvents {
  private CharacterSheetEvents() { }

  @SubscribeEvent
  public static void attachOnSpawn(final AttachCapabilitiesEvent<Entity> event) {
    if(event.getObject() instanceof EntityPlayer) {
      event.addCapability(CapabilityCharacterSheet.ID, new CharacterSheetProvider());
    }
  }

  public static void playerClone(final PlayerEvent.Clone event) {
    if(event.isWasDeath()) {
      System.out.println("Clone on death");

      final CharacterSheet newStats = event.getEntityPlayer().getCapability(CapabilityCharacterSheet.CHARACTER_STATS_CAPABILITY, null);
      final CharacterSheet oldStats = event.getOriginal().getCapability(CapabilityCharacterSheet.CHARACTER_STATS_CAPABILITY, null);

      //TODO: update oldStats
    }
  }
}
