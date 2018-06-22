package us.corielicio.mcnd.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import us.corielicio.mcnd.Mcnd;
import us.corielicio.mcnd.equipment.weapons.ammunition.Ammunition;
import us.corielicio.mcnd.items.DynamicItem;
import us.corielicio.mcnd.items.McndItems;

public class CommandAmmo extends CommandBase {
  @Override
  public String getName() {
    return "ammo";
  }

  @Override
  public String getUsage(final ICommandSender sender) {
    return "commands.ammo.usage";
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
      throw new WrongUsageException("commands.ammo.usage");
    }

    final Ammunition ammo = Mcnd.AMMUNITION.get(args[0]);

    if(ammo == null) {
      throw new WrongUsageException("commands.ammo.invalid_weapon_id");
    }

    final ItemStack stack = new ItemStack(McndItems.DYNAMIC_ITEM);
    DynamicItem.addAmmo(stack, ammo);

    final EntityPlayer player = (EntityPlayer)sender;
    if(!player.inventory.addItemStackToInventory(stack)) {
      player.dropItem(stack, false);
    }
  }
}
