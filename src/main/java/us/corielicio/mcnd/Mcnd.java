package us.corielicio.mcnd;

import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import org.apache.logging.log4j.Logger;
import us.corielicio.mcnd.classes.DndClasses;
import us.corielicio.mcnd.equipment.armour.Armours;
import us.corielicio.mcnd.guis.GuiHandler;
import us.corielicio.mcnd.packets.McndNet;
import us.corielicio.mcnd.stats.CapabilityCharacterStats;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Mod(modid = Mcnd.MODID, name = Mcnd.NAME, version = Mcnd.VERSION)
public class Mcnd {
  public static final String MODID = "mcnd";
  public static final String NAME = "MCnD";
  public static final String VERSION = "1.0";

  @Mod.Instance(MODID)
  public static Mcnd instance;

  @SidedProxy(serverSide = "us.corielicio.mcnd.ServerProxy", clientSide = "us.corielicio.mcnd.ClientProxy")
  public static IProxy proxy;

  public static Logger logger;

  @EventHandler
  public void preInit(final FMLPreInitializationEvent event) throws IOException {
    logger = event.getModLog();

    CapabilityCharacterStats.register();

    NetworkRegistry.INSTANCE.registerGuiHandler(Mcnd.instance, new GuiHandler());

    this.loadArmour(event);
    this.loadClasses(event);

    proxy.preInit(event);
  }

  @EventHandler
  public void init(final FMLInitializationEvent event) {
    logger.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());

    McndNet.register();
  }

  @EventHandler
  public void serverStarting(final FMLServerStartingEvent event) {
    proxy.serverStarting(event);
  }

  public static ResourceLocation resource(final String name) {
    return new ResourceLocation(MODID, name);
  }

  private void loadArmour(final FMLPreInitializationEvent event) throws IOException {
    // Load from assets
    try {
      Armours.addArmourInDirectory(Paths.get(this.getClass().getClassLoader().getResource("assets/" + MODID + "/armour").toURI()));
    } catch(final URISyntaxException e) {
      Mcnd.logger.error("Error getting assets/classes/ directory URI (this shouldn't happen)", e);
    }

    // Load from config
    final Path configDir = event.getModConfigurationDirectory().toPath().resolve("mcnd/armour");
    Files.createDirectories(configDir);

    Armours.addArmourInDirectory(configDir);
  }

  private void loadClasses(final FMLPreInitializationEvent event) throws IOException {
    // Load from assets
    try {
      DndClasses.addClassesInDirectory(Paths.get(this.getClass().getClassLoader().getResource("assets/" + MODID + "/classes").toURI()));
    } catch(final URISyntaxException e) {
      Mcnd.logger.error("Error getting assets/classes/ directory URI (this shouldn't happen)", e);
    }

    // Load from config
    final Path configDir = event.getModConfigurationDirectory().toPath().resolve("mcnd/classes");
    Files.createDirectories(configDir);

    DndClasses.addClassesInDirectory(configDir);
  }
}
