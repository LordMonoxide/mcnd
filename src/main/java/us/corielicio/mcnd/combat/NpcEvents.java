package us.corielicio.mcnd.combat;

import net.minecraft.entity.monster.EntityMob;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import us.corielicio.mcnd.Mcnd;

@Mod.EventBusSubscriber(modid = Mcnd.MODID)
public class NpcEvents {
  private NpcEvents() { }

  @SubscribeEvent
  public static void onEntitySpawn(final EntityJoinWorldEvent event) {
    if(event.getEntity() instanceof EntityMob) {
      final EntityMob mob = (EntityMob)event.getEntity();
      mob.tasks.taskEntries.clear();
      mob.targetTasks.taskEntries.clear();
    }
  }
}
