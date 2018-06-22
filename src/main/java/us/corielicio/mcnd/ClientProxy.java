package us.corielicio.mcnd;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.model.*;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import us.corielicio.mcnd.commands.*;
import us.corielicio.mcnd.items.McndItems;

import javax.annotation.Nullable;
import java.util.ArrayList;

public class ClientProxy implements IProxy {
  @Override
  public void preInit(final FMLPreInitializationEvent event) {
    ModelLoaderRegistry.registerLoader(new ICustomModelLoader() {
      @Override
      public boolean accepts(final ResourceLocation modelLocation) {
        return modelLocation.getResourceDomain().equals(Mcnd.MODID) && modelLocation.getResourcePath().endsWith(McndItems.DYNAMIC_ITEM.getRegistryName().getResourcePath());
      }

      @Override
      public IModel loadModel(final ResourceLocation modelLocation) {
        return new ItemLayerModel(ImmutableList.of(Mcnd.resource("items/nether_star")), new ItemOverrideList(new ArrayList<>(0)) {
          @Override
          public IBakedModel handleItemState(final IBakedModel originalModel, final ItemStack stack, @Nullable final World world, @Nullable final EntityLivingBase entity) {
            if(!stack.isEmpty()) {
              final NBTTagCompound display = stack.getSubCompound("display");

              if(display != null) {
                if(display.hasKey("Sprite", Constants.NBT.TAG_COMPOUND)) {
                  final NBTTagCompound sprite = display.getCompoundTag("Sprite");
                  final ResourceLocation location = new ResourceLocation(sprite.getString("Domain"), sprite.getString("Path"));

                  return Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getModelManager().getModel(ModelLoader.getInventoryVariant(location.toString()));
                }
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

  @Override
  public void serverStarting(final FMLServerStartingEvent event) {
    event.registerServerCommand(new CommandDynamicItem());
    event.registerServerCommand(new CommandShowCharacterSheet());
    event.registerServerCommand(new CommandWeapon());
    event.registerServerCommand(new CommandArmour());
    event.registerServerCommand(new CommandAmmo());
  }
}
