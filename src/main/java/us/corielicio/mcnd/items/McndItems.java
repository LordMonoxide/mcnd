package us.corielicio.mcnd.items;

import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;
import us.corielicio.mcnd.Mcnd;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid=Mcnd.MODID)
public class McndItems {
  private McndItems() { }

  private static final List<Item> ITEMS = new ArrayList<>();

  public static final DynamicItem DYNAMIC_ITEM = register(new DynamicItem());

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
