package us.corielicio.mcnd;

import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

public interface IProxy {
  void preInit(final FMLPreInitializationEvent event);
  void serverStarting(final FMLServerStartingEvent event);
}
