package us.corielicio.mcnd.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.apache.commons.lang3.StringUtils;
import us.corielicio.mcnd.containers.slots.SlotOutput;
import us.corielicio.mcnd.items.McndItems;

import javax.annotation.Nonnull;

public class ContainerDynamicItem extends Container {
  private final IItemHandler inventory;

  public ContainerDynamicItem(final InventoryPlayer playerInventory) {
    this.inventory = new ItemStackHandler(2) {
      @Override
      @Nonnull
      public ItemStack extractItem(final int slot, final int amount, final boolean simulate) {
        final ItemStack stack = super.extractItem(slot, amount, simulate);

        if(slot == 0 && !simulate) {
          this.insertItem(0, new ItemStack(McndItems.DYNAMIC_ITEM), false);
        }

        return stack;
      }

      @Override
      protected void onContentsChanged(final int slot) {
        if(slot == 1) {
          final ItemStack dynamicStack = this.getStackInSlot(0);

          if(!dynamicStack.isEmpty()) {
            final ItemStack spriteStack = this.getStackInSlot(1);

            if(!spriteStack.isEmpty()) {
              final ResourceLocation location = spriteStack.getItem().getRegistryName();
              final NBTTagCompound sprite = new NBTTagCompound();
              sprite.setString("Domain", location.getResourceDomain());
              sprite.setString("Path", location.getResourcePath());

              dynamicStack.getOrCreateSubCompound("display").setTag("Sprite", sprite);
            } else {
              dynamicStack.getOrCreateSubCompound("display").removeTag("Sprite");
            }
          }
        }
      }
    };

    this.addSlotToContainer(new SlotOutput(this.inventory, 0, 152, 62));
    this.addSlotToContainer(new SlotItemHandler(this.inventory, 1, 29, 56));

    for(int y = 0; y < 3; ++y) {
      for(int x = 0; x < 9; ++x) {
        this.addSlotToContainer(new Slot(playerInventory, x + y * 9 + 9, 8 + x * 18, 84 + y * 18));
      }
    }

    for(int hotSlot = 0; hotSlot < 9; ++hotSlot) {
      this.addSlotToContainer(new Slot(playerInventory, hotSlot, 8 + hotSlot * 18, 142));
    }

    this.inventory.insertItem(0, new ItemStack(McndItems.DYNAMIC_ITEM), false);
  }

  @Override
  public void onContainerClosed(final EntityPlayer player) {
    for(int i = 1; i < this.inventory.getSlots(); i++) {
      final ItemStack stack = this.inventory.getStackInSlot(i);

      if(!stack.isEmpty()) {
        player.dropItem(stack, false);
      }
    }

    super.onContainerClosed(player);
  }

  @Override
  public boolean canInteractWith(final EntityPlayer player) {
    return true;
  }

  public void updateField(final int id, final String text) {
    switch(id) {
      case 0:
        this.updateItemName(text);
        break;

      case 1:
        this.updateItemDesc(text);
        break;
    }
  }

  public void updateItemName(final String name) {
    final ItemStack stack = this.inventory.getStackInSlot(0);

    if(StringUtils.isBlank(name)) {
      stack.clearCustomName();
    } else {
      stack.setStackDisplayName(name);
    }
  }

  public void updateItemDesc(final String desc) {
    final ItemStack stack = this.inventory.getStackInSlot(0);
    final NBTTagCompound display = stack.getOrCreateSubCompound("display");

    if(StringUtils.isBlank(desc)) {
      if(display.hasKey("Desc")) {
        display.removeTag("Desc");
      }
    } else {
      display.setString("Desc", desc);
    }
  }
}
