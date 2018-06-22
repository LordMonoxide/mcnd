package us.corielicio.mcnd.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import us.corielicio.mcnd.Mcnd;
import us.corielicio.mcnd.equipment.armour.Armour;
import us.corielicio.mcnd.items.DynamicItem;
import us.corielicio.mcnd.items.McndItems;

public class CommandArmour extends CommandBase {
  @Override
  public String getName() {
    return "armour";
  }

  @Override
  public String getUsage(final ICommandSender sender) {
    return "commands.armour.usage";
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

    if(args.length < 1) {
      throw new WrongUsageException("commands.armour.usage");
    }

    final Armour armour = Mcnd.ARMOUR.get(args[0]);

    if(armour == null) {
      throw new WrongUsageException("commands.armour.invalid_armour_id");
    }

    final ItemStack stack = new ItemStack(McndItems.DYNAMIC_ITEM);
    DynamicItem.addArmour(stack, armour);

    final EntityPlayer player = (EntityPlayer)sender;
    if(!player.inventory.addItemStackToInventory(stack)) {
      player.dropItem(stack, false);
    }
  }
}
