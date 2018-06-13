package us.corielicio.mcnd.commands;

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import us.corielicio.mcnd.guis.GuiCharacterSheet;

public class CommandShowCharacterSheet extends CommandBase {
  @Override
  public String getName() {
    return "sheet";
  }

  @Override
  public String getUsage(final ICommandSender sender) {
    return "command.character_sheet";
  }

  @Override
  public void execute(final MinecraftServer server, final ICommandSender sender, final String[] args) throws CommandException {
    if(!(sender instanceof EntityPlayer)) {
      return;
    }

    Minecraft.getMinecraft().displayGuiScreen(new GuiCharacterSheet((EntityPlayer)sender));
  }
}
