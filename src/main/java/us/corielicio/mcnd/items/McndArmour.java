package us.corielicio.mcnd.items;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import us.corielicio.mcnd.equipment.armour.Armour;

import javax.annotation.Nullable;
import java.util.List;

public class McndArmour extends Item {
  private Armour armour;

  public McndArmour(final Armour armour) {
    this.armour = armour;

    final String name = "armour." + armour.id;
    this.setUnlocalizedName(name);
    this.setRegistryName(name);
  }

  @Override
  @SideOnly(Side.CLIENT)
  public void addInformation(final ItemStack stack, @Nullable final World world, final List<String> tooltip, final ITooltipFlag flag) {
    tooltip.add(I18n.format("armour.name") + ", " + I18n.format(this.armour.type.lang));
    tooltip.add(I18n.format("armour.ac", this.armour.ac));
  }
}
