package us.corielicio.mcnd.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import us.corielicio.mcnd.Mcnd;

public class CommandCombatClear extends CommandBase {
  @Override
  public String getName() {
    return "combat-clear";
  }

  @Override
  public String getUsage(final ICommandSender sender) {
    return "commands.combat_clear.usage";
  }

  @Override
  public int getRequiredPermissionLevel() {
    return 2;
  }

  @Override
  public void execute(final MinecraftServer server, final ICommandSender sender, final String[] args) throws CommandException {
    Mcnd.COMBAT.clear();
  }
}
