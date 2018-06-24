package us.corielicio.mcnd.characters;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import us.corielicio.mcnd.Mcnd;

public class CapabilityCharacterSheet {
  public static final ResourceLocation ID = Mcnd.resource("stats");

  @CapabilityInject(CharacterSheet.class)
  public static Capability<CharacterSheet> CHARACTER_SHEET_CAPABILITY;

  public static void register() {
    CapabilityManager.INSTANCE.register(CharacterSheet.class, new Capability.IStorage<CharacterSheet>() {
      @Override
      public NBTBase writeNBT(final Capability<CharacterSheet> capability, final CharacterSheet instance, final EnumFacing side) {
        final NBTTagCompound stats = new NBTTagCompound();
        for(final Stats stat : Stats.values()) {
          stats.setInteger(stat.name(), instance.stat(stat).raw());
          stats.setInteger(stat.name() + "SaveProficiency", instance.stat(stat).saveProficiency());
        }

        final NBTTagCompound skills = new NBTTagCompound();
        for(final Skills skill : Skills.values()) {
          skills.setInteger(skill.name() + "Proficiency", instance.skill(skill).proficiency());
        }

        final NBTTagCompound tags = new NBTTagCompound();
        tags.setTag("stats", stats);
        tags.setTag("skills", skills);
        tags.setInteger("level", instance.level());
        tags.setInteger("exp", instance.exp());

        return tags;
      }

      @Override
      public void readNBT(final Capability<CharacterSheet> capability, final CharacterSheet instance, final EnumFacing side, final NBTBase base) {
        if(!(base instanceof NBTTagCompound)) {
          return;
        }

        final NBTTagCompound stats = ((NBTTagCompound)base).getCompoundTag("stats");
        for(final Stats stat : Stats.values()) {
          instance.stat(stat).set(stats.getInteger(stat.name()));
          instance.stat(stat).setSaveProficiency(stats.getInteger(stat.name() + "SaveProficiency"));
        }

        final NBTTagCompound skills = ((NBTTagCompound)base).getCompoundTag("skills");
        for(final Skills skill : Skills.values()) {
          instance.skill(skill).setProficiency(skills.getInteger(skill.name() + "Proficiency"));
        }

        instance.setLevel(((NBTTagCompound)base).getInteger("level"));
        instance.setExp(((NBTTagCompound)base).getInteger("exp"));
      }
    }, CharacterSheet::new);
  }
}
