package us.corielicio.mcnd;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.model.*;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import us.corielicio.mcnd.commands.CommandDynamicItem;
import us.corielicio.mcnd.commands.CommandShowCharacterSheet;
import us.corielicio.mcnd.items.McndItems;

import javax.annotation.Nullable;
import java.util.ArrayList;

@Mod.EventBusSubscriber(modid = Mcnd.MODID, value = Side.CLIENT)
public class ClientProxy implements IProxy {
  @Override
  public void preInit(final FMLPreInitializationEvent event) {
    ModelLoaderRegistry.registerLoader(new ICustomModelLoader() {
      @Override
      public boolean accepts(final ResourceLocation modelLocation) {
        if(modelLocation.getResourceDomain().endsWith(Mcnd.MODID)) {
          System.out.println(modelLocation);
        }

        return modelLocation.getResourceDomain().equals(Mcnd.MODID) && modelLocation.getResourcePath().endsWith(McndItems.DYNAMIC_ITEM.getRegistryName().getResourcePath());
      }

      @Override
      public IModel loadModel(final ResourceLocation modelLocation) {
        return new ItemLayerModel(ImmutableList.of(Mcnd.resource("items/nether_star")), new ItemOverrideList(new ArrayList<>(0)) {
          @Override
          public IBakedModel handleItemState(final IBakedModel originalModel, final ItemStack stack, @Nullable final World world, @Nullable final EntityLivingBase entity) {
            if(!stack.isEmpty()) {
//              final String name = Item.REGISTRY.getNameForObject(stack.getItem()).toString();
//
//              ResourceLocation resourcelocation = new ResourceLocation(name.replaceAll("#.*", ""));
//              resourcelocation = new ResourceLocation(resourcelocation.getResourceDomain(), "item/" + resourcelocation.getResourcePath());
//
//              return Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getModelManager().getModel();

              //final ResourceLocation location = this.applyOverride(stack, world, entity);
              final ResourceLocation location = new ResourceLocation("mcnd", "models/item/dynamic_item");

              if(location != null) {
                return Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getModelManager().getModel(ModelLoader.getInventoryVariant(location.toString()));
              }
            }

            return originalModel;
          }
        });
      }

      @Override
      public void onResourceManagerReload(final IResourceManager resourceManager) {

      }
    });
  }

  @SubscribeEvent
  public static void onModelBake(final ModelBakeEvent event) {
    for(final ResourceLocation res : event.getModelRegistry().getKeys()) {
      System.out.println(res);
    }
  }

  @Override
  public void serverStarting(final FMLServerStartingEvent event) {
    event.registerServerCommand(new CommandDynamicItem());
    event.registerServerCommand(new CommandShowCharacterSheet());
  }
}
