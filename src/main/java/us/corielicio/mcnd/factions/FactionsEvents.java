package us.corielicio.mcnd.factions;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import us.corielicio.mcnd.Mcnd;

@Mod.EventBusSubscriber(modid = Mcnd.MODID)
public class FactionsEvents {
  private FactionsEvents() { }

  @SubscribeEvent
  public static void attachOnSpawn(final AttachCapabilitiesEvent<Entity> event) {
    if(event.getObject() instanceof EntityPlayer) {
      event.addCapability(CapabilityFactions.ID, new FactionsProvider());
    }
  }

  @SubscribeEvent
  public static void playerClone(final PlayerEvent.Clone event) {
    if(event.isWasDeath()) {
      final Factions newFactions = event.getEntityPlayer().getCapability(CapabilityFactions.FACTIONS_CAPABILITY, null);
      final Factions oldFactions = event.getOriginal().getCapability(CapabilityFactions.FACTIONS_CAPABILITY, null);

      newFactions.add(oldFactions);
    }
  }
}
