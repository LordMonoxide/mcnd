package us.corielicio.mcnd.packets;

import com.google.common.base.Charsets;
import io.netty.buffer.ByteBuf;
import net.minecraft.inventory.ContainerRepair;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import us.corielicio.mcnd.containers.ContainerDynamicItem;

import javax.annotation.Nullable;

public class PacketDynamicItemRename implements IMessage {
  public static void send(final String name) {
    McndNet.CHANNEL.sendToServer(new PacketDynamicItemRename(name));
  }

  private String name;

  public PacketDynamicItemRename() { }

  public PacketDynamicItemRename(final String name) {
    this.name = name;
  }

  public String getName() {
    return this.name;
  }

  @Override
  public void fromBytes(final ByteBuf buf) {
    try {
      final int length = buf.readInt();
      this.name = buf.readCharSequence(length, Charsets.UTF_8).toString();
    } catch(final IndexOutOfBoundsException e) {
      System.out.println("Invalid type in PacketDynamicItemRename");
      e.printStackTrace();
      this.name = "Corrupt data";
    }
  }

  @Override
  public void toBytes(final ByteBuf buf) {
    buf.writeInt(this.name.length());
    buf.writeCharSequence(this.name, Charsets.UTF_8);
  }

  public static class Handler implements IMessageHandler<PacketDynamicItemRename, IMessage> {
    @Override
    @Nullable
    public IMessage onMessage(final PacketDynamicItemRename packet, final MessageContext ctx) {
      if(packet.name == null) {
        return null;
      }

      ctx.getServerHandler().player.getServerWorld().addScheduledTask(() -> {
        if(ctx.getServerHandler().player.openContainer instanceof ContainerDynamicItem) {
          final ContainerDynamicItem container = (ContainerDynamicItem)ctx.getServerHandler().player.openContainer;
          container.updateItemName(packet.name);
        }
      });

      return null;
    }
  }
}
