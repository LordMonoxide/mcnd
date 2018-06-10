package us.corielicio.mcnd.stats;

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;

public class CommandShowCharacterSheet extends CommandBase {
  @Override
  public String getName() {
    return "sheet";
  }

  @Override
  public String getUsage(final ICommandSender sender) {
    //TODO
    return "translation.goes.here";
  }

  @Override
  public void execute(final MinecraftServer server, final ICommandSender sender, final String[] args) throws CommandException {
    if(!(sender instanceof EntityPlayer)) {
      return;
    }

    Minecraft.getMinecraft().displayGuiScreen(new GuiCharacterSheet((EntityPlayer)sender));
  }
}
