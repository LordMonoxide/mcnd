package us.corielicio.mcnd.packets;

import com.google.common.base.Charsets;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import us.corielicio.mcnd.containers.ContainerDynamicItem;

import javax.annotation.Nullable;

public class PacketDynamicItemSync implements IMessage {
  public static void send(final int id, final String name) {
    McndNet.CHANNEL.sendToServer(new PacketDynamicItemSync(id, name));
  }

  private int id;
  private String text;

  public PacketDynamicItemSync() { }

  public PacketDynamicItemSync(final int id, final String text) {
    this.id = id;
    this.text = text;
  }

  @Override
  public void fromBytes(final ByteBuf buf) {
    try {
      this.id = buf.readInt();

      final int length = buf.readInt();
      this.text = buf.readCharSequence(length, Charsets.UTF_8).toString();
    } catch(final IndexOutOfBoundsException e) {
      System.out.println("Invalid type in PacketDynamicItemRename");
      e.printStackTrace();
      this.text = "Corrupt data";
    }
  }

  @Override
  public void toBytes(final ByteBuf buf) {
    buf.writeInt(this.id);
    buf.writeInt(this.text.length());
    buf.writeCharSequence(this.text, Charsets.UTF_8);
  }

  public static class Handler implements IMessageHandler<PacketDynamicItemSync, IMessage> {
    @Override
    @Nullable
    public IMessage onMessage(final PacketDynamicItemSync packet, final MessageContext ctx) {
      if(packet.text == null) {
        return null;
      }

      ctx.getServerHandler().player.getServerWorld().addScheduledTask(() -> {
        if(ctx.getServerHandler().player.openContainer instanceof ContainerDynamicItem) {
          final ContainerDynamicItem container = (ContainerDynamicItem)ctx.getServerHandler().player.openContainer;
          container.updateField(packet.id, packet.text);
        }
      });

      return null;
    }
  }
}
