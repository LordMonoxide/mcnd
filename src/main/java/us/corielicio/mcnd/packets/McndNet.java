package us.corielicio.mcnd.packets;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import us.corielicio.mcnd.Mcnd;

public class McndNet {
  private McndNet() { }

  public static final SimpleNetworkWrapper CHANNEL = NetworkRegistry.INSTANCE.newSimpleChannel(Mcnd.MODID);

  private static int id = 0;

  public static void register() {
    CHANNEL.registerMessage(PacketDynamicItemRename.Handler.class, PacketDynamicItemRename.class, id++, Side.SERVER);
  }
}
