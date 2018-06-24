package us.corielicio.mcnd.factions;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import us.corielicio.mcnd.Mcnd;

public class CapabilityFactions {
  public static final ResourceLocation ID = Mcnd.resource("factions");

  @CapabilityInject(Factions.class)
  public static Capability<Factions> FACTIONS_CAPABILITY;

  public static void register() {
    CapabilityManager.INSTANCE.register(Factions.class, new Capability.IStorage<Factions>() {
      @Override
      public NBTBase writeNBT(final Capability<Factions> capability, final Factions instance, final EnumFacing side) {
        final NBTTagList tags = new NBTTagList();

        for(final Faction faction : instance) {
          tags.appendTag(new NBTTagString(faction.id));
        }

        return tags;
      }

      @Override
      public void readNBT(final Capability<Factions> capability, final Factions instance, final EnumFacing side, final NBTBase base) {
        if(!(base instanceof NBTTagList)) {
          return;
        }

        for(final NBTBase tag : (NBTTagList)base) {
          if(tag instanceof NBTTagString) {
            instance.add(Mcnd.FACTIONS.get(((NBTTagString)tag).getString()));
          }
        }
      }
    }, Factions::new);
  }
}
