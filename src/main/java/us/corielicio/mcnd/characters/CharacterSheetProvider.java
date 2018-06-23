package us.corielicio.mcnd.characters;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CharacterSheetProvider implements ICapabilitySerializable<NBTBase> {
  private final Capability<CharacterSheet> cap = CapabilityCharacterSheet.CHARACTER_STATS_CAPABILITY;
  private final CharacterSheet instance = this.cap.getDefaultInstance();

  @Override
  @Nullable
  public NBTBase serializeNBT() {
    return this.cap.writeNBT(this.instance, null);
  }

  @Override
  public void deserializeNBT(final NBTBase nbt) {
    this.cap.readNBT(this.instance, null, nbt);
  }

  @Override
  public boolean hasCapability(@Nonnull final Capability<?> capability, @Nullable final EnumFacing facing) {
    return capability == this.cap;
  }

  @Nullable
  @Override
  public <T> T getCapability(@Nonnull final Capability<T> capability, @Nullable final EnumFacing facing) {
    return capability == this.cap ? this.cap.cast(this.instance) : null;
  }
}
