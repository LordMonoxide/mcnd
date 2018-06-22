package us.corielicio.mcnd.items;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import us.corielicio.mcnd.equipment.weapons.Weapon;

import javax.annotation.Nullable;
import java.util.List;

public class McndWeapon extends Item {
  private final Weapon weapon;

  public McndWeapon(final Weapon weapon) {
    this.weapon = weapon;

    final String name = "weapon." + weapon.id;
    this.setUnlocalizedName(name);
    this.setRegistryName(name);
  }

  @Override
  @SideOnly(Side.CLIENT)
  public void addInformation(final ItemStack stack, @Nullable final World world, final List<String> tooltip, final ITooltipFlag flag) {
    tooltip.add(I18n.format("weapon.name") + ", " + I18n.format(this.weapon.type.lang) + ", " + I18n.format(this.weapon.category.lang));
    tooltip.add(I18n.format("weapon.damage", this.weapon.damage, I18n.format(this.weapon.damageType.lang)));
  }
}
