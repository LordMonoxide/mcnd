package us.corielicio.mcnd.items;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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
  }
}
