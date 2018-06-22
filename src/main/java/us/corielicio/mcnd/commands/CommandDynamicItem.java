package us.corielicio.mcnd.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import us.corielicio.mcnd.Mcnd;
import us.corielicio.mcnd.guis.GuiHandler;

public class CommandDynamicItem extends CommandBase {
  @Override
  public String getName() {
    return "dynamic-item";
  }

  @Override
  public String getUsage(final ICommandSender sender) {
    return "commands.dynamic_item.usage";
  }

  @Override
  public int getRequiredPermissionLevel() {
    return 2;
  }

  @Override
  public void execute(final MinecraftServer server, final ICommandSender sender, final String[] args) throws CommandException {
    if(!(sender instanceof EntityPlayer)) {
      return;
    }

    final EntityPlayer player = (EntityPlayer)sender;
    player.openGui(Mcnd.instance, GuiHandler.GUI_DYNAMIC_ITEM, player.getEntityWorld(), (int)player.posX, (int)player.posY, (int)player.posZ);
  }
}
