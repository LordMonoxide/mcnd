package us.corielicio.mcnd.equipment.armour;

import org.yaml.snakeyaml.Yaml;
import us.corielicio.mcnd.Mcnd;
import us.corielicio.mcnd.dice.Dice;
import us.corielicio.mcnd.utils.Assertions;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;

public class Armours {
  private Armours() { }

  public static final Map<String, Armour> armours = new HashMap<>();

  public static void addArmourInDirectory(final Path path) throws IOException {
    Mcnd.logger.info("Loading armours from {}", path.toString());

    for(final Path file : Files.newDirectoryStream(path, "*.yaml")) {
      try {
        addArmourFromPath(file);
      } catch(final IOException e) {
        Mcnd.logger.error("Error opening armour", e);
      } catch(final Assertions.AssertionException e) {
        Mcnd.logger.error("Malformed armour", e);
      }
    }
  }

  public static Armour addArmourFromPath(final Path path) throws Assertions.AssertionException, IOException {
    final Yaml yaml = new Yaml();

    final Map<String, Object> root = yaml.load(Files.newInputStream(path, StandardOpenOption.READ));

    final ArmourState state = new ArmourState();

    Assertions.check(assertions -> {
      state.id = assertions.string(root.get("id"), "Invalid ID");
      state.name = assertions.string(root.get("name"), "Invalid name");
      state.cost = assertions.integer(root.get("cost"), "Invalid cost");
      state.ac = assertions.dice(root.get("ac"), "Invalid ac");
      state.weight = assertions.integer(root.get("weight"), "Invalid weight");
      state.strengthRequired = assertions.integer(root.get("strengthRequired"), "Invalid strengthRequired");
      state.stealthDisadvantage = assertions.bool(root.get("stealthDisadvantage"), "Invalid stealthDisadvantage");
    });

    return addArmour(new Armour(state.id, state.name, state.cost, state.ac, state.weight, state.strengthRequired, state.stealthDisadvantage));
  }

  public static Armour addArmour(final Armour armour) {
    if(armours.put(armour.id, armour) == null) {
      Mcnd.logger.info("Loaded armour {}", armour.id);
    } else {
      Mcnd.logger.info("Reloaded armour {}", armour.id);
    }

    return armour;
  }

  private static class ArmourState {
    private String id;
    private String name;
    private int cost;
    private Dice ac;
    private int weight;
    private int strengthRequired;
    private boolean stealthDisadvantage;
  }
}
