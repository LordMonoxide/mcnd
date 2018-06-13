package us.corielicio.mcnd.guis;

import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import us.corielicio.mcnd.containers.ContainerDynamicItem;

import javax.annotation.Nullable;

public class GuiHandler implements IGuiHandler {
  public static final int GUI_DYNAMIC_ITEM = 1;

  @Nullable
  @Override
  public Container getServerGuiElement(final int id, final EntityPlayer player, final World world, final int x, final int y, final int z) {
    switch(id) {
      case GUI_DYNAMIC_ITEM:
        return new ContainerDynamicItem(player.inventory);
    }

    return null;
  }

  @Nullable
  @Override
  public Gui getClientGuiElement(final int id, final EntityPlayer player, final World world, final int x, final int y, final int z) {
    switch(id) {
      case GUI_DYNAMIC_ITEM:
        return new GuiDynamicItem((ContainerDynamicItem)this.getServerGuiElement(id, player, world, x, y, z));
    }

    return null;
  }
}
