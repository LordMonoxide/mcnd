package us.corielicio.mcnd.items;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import us.corielicio.mcnd.Mcnd;
import us.corielicio.mcnd.equipment.armour.Armour;
import us.corielicio.mcnd.equipment.weapons.Weapon;
import us.corielicio.mcnd.equipment.weapons.ammunition.Ammunition;

import javax.annotation.Nullable;
import java.util.List;

public class DynamicItem extends Item {
  public DynamicItem() {
    this.setUnlocalizedName("dynamic_item");
    this.setRegistryName("dynamic_item");
  }

  @Override
  @SideOnly(Side.CLIENT)
  public void addInformation(final ItemStack stack, @Nullable final World world, final List<String> tooltip, final ITooltipFlag flag) {
    final NBTTagCompound display = stack.getOrCreateSubCompound("display");

    if(display.hasKey("Desc")) {
      tooltip.add(display.getString("Desc"));
    }

    final Weapon weapon = getWeapon(stack);
    if(weapon != null) {
      tooltip.add(I18n.format("weapon.name") + ", " + I18n.format(weapon.type.lang) + ", " + I18n.format(weapon.category.lang));
      tooltip.add(I18n.format("weapon.damage", weapon.damage, I18n.format(weapon.damageType.lang)));
    }

    final Armour armour = getArmour(stack);
    if(armour != null) {
      tooltip.add(I18n.format("armour.name") + ", " + I18n.format(armour.type.lang));
      tooltip.add(I18n.format("armour.ac", armour.ac));
    }

    final Ammunition ammo = getAmmo(stack);
    if(ammo != null) {
      tooltip.add(I18n.format("ammo.name"));
    }
  }

  public static void addWeapon(final ItemStack stack, final Weapon weapon) {
    final NBTTagCompound nbt = stack.getOrCreateSubCompound("weapon");
    nbt.setString("id", weapon.id);

    stack.setStackDisplayName(McndItems.WEAPON.get(weapon).getItemStackDisplayName(ItemStack.EMPTY));
  }

  @Nullable
  public static Weapon getWeapon(final ItemStack stack) {
    if(!stack.getTagCompound().hasKey("weapon")) {
      return null;
    }

    return Mcnd.WEAPONS.get(stack.getSubCompound("weapon").getString("id"));
  }

  public static void addArmour(final ItemStack stack, final Armour armour) {
    final NBTTagCompound nbt = stack.getOrCreateSubCompound("armour");
    nbt.setString("id", armour.id);

    stack.setStackDisplayName(McndItems.ARMOUR.get(armour).getItemStackDisplayName(ItemStack.EMPTY));
  }

  @Nullable
  public static Armour getArmour(final ItemStack stack) {
    if(!stack.getTagCompound().hasKey("armour")) {
      return null;
    }

    return Mcnd.ARMOUR.get(stack.getSubCompound("armour").getString("id"));
  }

  public static void addAmmo(final ItemStack stack, final Ammunition ammo) {
    final NBTTagCompound nbt = stack.getOrCreateSubCompound("ammo");
    nbt.setString("id", ammo.id);

    stack.setStackDisplayName(McndItems.AMMO.get(ammo).getItemStackDisplayName(ItemStack.EMPTY));
  }

  @Nullable
  public static Ammunition getAmmo(final ItemStack stack) {
    if(!stack.getTagCompound().hasKey("ammo")) {
      return null;
    }

    return Mcnd.AMMUNITION.get(stack.getSubCompound("ammo").getString("id"));
  }
}
