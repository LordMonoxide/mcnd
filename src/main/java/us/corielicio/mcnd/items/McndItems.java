package us.corielicio.mcnd.items;

import com.google.common.collect.ImmutableMap;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;
import us.corielicio.mcnd.Mcnd;
import us.corielicio.mcnd.equipment.armour.Armour;
import us.corielicio.mcnd.equipment.weapons.Weapon;
import us.corielicio.mcnd.equipment.weapons.ammunition.Ammunition;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid=Mcnd.MODID)
public class McndItems {
  private McndItems() { }

  public static final List<Item> ITEMS = new ArrayList<>();

  public static final DynamicItem DYNAMIC_ITEM = register(new DynamicItem());
  public static final ImmutableMap<Ammunition, McndAmmo> AMMO;
  public static final ImmutableMap<Weapon, McndWeapon> WEAPON;
  public static final ImmutableMap<Armour, McndArmour> ARMOUR;

  static {
    final ImmutableMap.Builder<Ammunition, McndAmmo> builder = ImmutableMap.builder();
    Mcnd.AMMUNITION.forEach(ammo -> builder.put(ammo, register(new McndAmmo(ammo))));
    AMMO = builder.build();
  }

  static {
    final ImmutableMap.Builder<Weapon, McndWeapon> builder = ImmutableMap.builder();
    Mcnd.WEAPONS.forEach(weapon -> builder.put(weapon, register(new McndWeapon(weapon))));
    WEAPON = builder.build();
  }

  static {
    final ImmutableMap.Builder<Armour, McndArmour> builder = ImmutableMap.builder();
    Mcnd.ARMOUR.forEach(armour -> builder.put(armour, register(new McndArmour(armour))));
    ARMOUR = builder.build();
  }

  private static <T extends Item> T register(final T item) {
    ITEMS.add(item);
    return item;
  }

  @SubscribeEvent
  public static void registerItems(final RegistryEvent.Register<Item> event) {
    Mcnd.logger.info("Registering items");

    final IForgeRegistry<Item> registry = event.getRegistry();

    for(final Item item : ITEMS) {
      registry.register(item);
    }
  }
}
