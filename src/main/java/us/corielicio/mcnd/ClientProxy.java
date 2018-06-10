package us.corielicio.mcnd;

import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import us.corielicio.mcnd.stats.CommandShowCharacterSheet;

public class ClientProxy implements IProxy {
  @Override
  public void serverStarting(final FMLServerStartingEvent event) {
    event.registerServerCommand(new CommandShowCharacterSheet());
  }
}
