package us.corielicio.mcnd.overrides;

import net.minecraft.entity.item.EntityXPOrb;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import us.corielicio.mcnd.Mcnd;

@Mod.EventBusSubscriber(modid=Mcnd.MODID)
public class DisableExperienceOrbs {
  private DisableExperienceOrbs() { }

  @SubscribeEvent
  public static void onEntityJoinWorld(final EntityJoinWorldEvent event) {
    if(event.getEntity() instanceof EntityXPOrb) {
      event.setCanceled(true);
    }
  }
}
