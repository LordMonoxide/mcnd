package us.corielicio.mcnd;

import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

public interface IProxy {
  void serverStarting(final FMLServerStartingEvent event);
}
