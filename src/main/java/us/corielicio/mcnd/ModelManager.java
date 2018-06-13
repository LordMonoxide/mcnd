package us.corielicio.mcnd;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import us.corielicio.mcnd.items.McndItems;

@Mod.EventBusSubscriber(value = Side.CLIENT, modid = Mcnd.MODID)
public class ModelManager {
  private ModelManager() { }

  @SubscribeEvent
  public static void registerModels(final ModelRegistryEvent event) {
    Mcnd.logger.info("Registering models");

    registerItemModels();
  }

  private static void registerItemModels() {
    McndItems.ITEMS.forEach(ModelManager::registerItemModel);
  }

  private static void registerItemModel(final Item item) {
    ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
  }
}
