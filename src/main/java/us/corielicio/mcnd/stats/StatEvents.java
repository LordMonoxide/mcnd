package us.corielicio.mcnd.stats;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import us.corielicio.mcnd.Mcnd;

@Mod.EventBusSubscriber(modid = Mcnd.MODID)
public class StatEvents {
  @SubscribeEvent
  public static void attachStats(final AttachCapabilitiesEvent<Entity> event) {
    if(event.getObject() instanceof EntityPlayer) {
      event.addCapability(CapabilityCharacterStats.ID, new CharacterStatsProvider());
    }
  }

  public static void playerClone(final PlayerEvent.Clone event) {
    if(event.isWasDeath()) {
      System.out.println("Clone on death");

      final CharacterStats newStats = event.getEntityPlayer().getCapability(CapabilityCharacterStats.CHARACTER_STATS_CAPABILITY, null);
      final CharacterStats oldStats = event.getOriginal().getCapability(CapabilityCharacterStats.CHARACTER_STATS_CAPABILITY, null);

      //TODO: update oldStats
    }
  }
}
