package us.corielicio.mcnd.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import us.corielicio.mcnd.Mcnd;
import us.corielicio.mcnd.equipment.weapons.Weapon;
import us.corielicio.mcnd.items.DynamicItem;
import us.corielicio.mcnd.items.McndItems;

public class CommandWeapon extends CommandBase {
  @Override
  public String getName() {
    return "weapon";
  }

  @Override
  public String getUsage(final ICommandSender sender) {
    return "commands.weapon.usage";
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
      throw new WrongUsageException("commands.weapon.usage");
    }

    final Weapon weapon = Mcnd.WEAPONS.get(args[0]);

    if(weapon == null) {
      throw new WrongUsageException("commands.weapon.invalid_weapon_id");
    }

    final ItemStack stack = new ItemStack(McndItems.DYNAMIC_ITEM);
    DynamicItem.addWeapon(stack, weapon);

    final EntityPlayer player = (EntityPlayer)sender;
    if(!player.inventory.addItemStackToInventory(stack)) {
      player.dropItem(stack, false);
    }
  }
}
