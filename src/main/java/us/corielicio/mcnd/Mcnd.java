package us.corielicio.mcnd;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import org.apache.logging.log4j.Logger;
import us.corielicio.mcnd.characters.CapabilityCharacterSheet;
import us.corielicio.mcnd.classes.DndClasses;
import us.corielicio.mcnd.combat.CombatManager;
import us.corielicio.mcnd.commands.CommandCombatClear;
import us.corielicio.mcnd.equipment.armour.Armours;
import us.corielicio.mcnd.equipment.weapons.Weapons;
import us.corielicio.mcnd.equipment.weapons.ammunition.Ammunitions;
import us.corielicio.mcnd.factions.CapabilityFactions;
import us.corielicio.mcnd.factions.FactionManager;
import us.corielicio.mcnd.guis.GuiHandler;
import us.corielicio.mcnd.packets.McndNet;
import us.corielicio.mcnd.utils.loader.GameData;
import us.corielicio.mcnd.utils.loader.Loader;

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

  public static final FactionManager FACTIONS = new FactionManager();
  public static final Ammunitions AMMUNITION = new Ammunitions();
  public static final Weapons WEAPONS = new Weapons();
  public static final Armours ARMOUR = new Armours();
  public static final DndClasses CLASSES = new DndClasses();

  public static final CombatManager COMBAT = new CombatManager();

  @EventHandler
  public void preInit(final FMLPreInitializationEvent event) throws IOException {
    logger = event.getModLog();

    CapabilityCharacterSheet.register();
    CapabilityFactions.register();

    NetworkRegistry.INSTANCE.registerGuiHandler(Mcnd.instance, new GuiHandler());

    this.loadGameData(event, FACTIONS, "factions");
    this.loadGameData(event, AMMUNITION, "ammunition");
    this.loadGameData(event, WEAPONS, "weapons");
    this.loadGameData(event, ARMOUR, "armour");
    this.loadGameData(event, CLASSES, "classes");

    proxy.preInit(event);
  }

  @EventHandler
  public void init(final FMLInitializationEvent event) {
    McndNet.register();
  }

  @EventHandler
  public void serverStarting(final FMLServerStartingEvent event) {
    event.registerServerCommand(new CommandCombatClear());

    proxy.serverStarting(event);
  }

  public static ResourceLocation resource(final String name) {
    return new ResourceLocation(MODID, name);
  }

  private void loadGameData(final FMLPreInitializationEvent event, final Loader<? extends GameData> loader, final String directory) throws IOException {
    // Load from assets
    try {
      loader.addFromDirectory(Paths.get(this.getClass().getClassLoader().getResource("assets/" + MODID + '/' + directory).toURI()));
    } catch(final URISyntaxException e) {
      logger.error("Error getting assets/" + directory + "/ directory URI (this shouldn't happen)", e);
    }

    // Load from config
    final Path configDir = event.getModConfigurationDirectory().toPath().resolve(MODID + '/' + directory);
    Files.createDirectories(configDir);

    loader.addFromDirectory(configDir);
  }
}
