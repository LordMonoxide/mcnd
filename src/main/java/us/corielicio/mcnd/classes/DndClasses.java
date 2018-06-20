package us.corielicio.mcnd.classes;

import org.yaml.snakeyaml.Yaml;
import us.corielicio.mcnd.Mcnd;
import us.corielicio.mcnd.dice.Dice;
import us.corielicio.mcnd.dice.Die;
import us.corielicio.mcnd.utils.Assertions;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;

public class DndClasses {
  private DndClasses() { }

  public static final Map<String, DndClass> classes = new HashMap<>();

  public static void addClassesInDirectory(final Path path) throws IOException {
    Mcnd.logger.info("Loading classes from {}", path.toString());

    for(final Path file : Files.newDirectoryStream(path, "*.yaml")) {
      try {
        addClassFromPath(file);
      } catch(final IOException e) {
        Mcnd.logger.error("Error opening class", e);
      } catch(final Assertions.AssertionException e) {
        Mcnd.logger.error("Malformed class", e);
      }
    }
  }

  public static DndClass addClassFromPath(final Path path) throws Assertions.AssertionException, IOException {
    final Yaml yaml = new Yaml();

    final Map<String, Object> root = yaml.load(Files.newInputStream(path, StandardOpenOption.READ));

    final ClassState state = new ClassState();

    Assertions.check(assertions -> {
      state.id = assertions.string(root.get("id"), "Invalid ID");
      state.name = assertions.string(root.get("name"), "Invalid name");
      state.hitDie = assertions.dice(root.get("hitDie"), "Invalid hitDie");
      state.hitPointsFirstLevel = assertions.dice(root.get("hitPointsFirstLevel"), "Invalid hitPointsFirstLevel");
      state.hitPointDice = assertions.dice(root.get("hitPointDice"), "Invalid hitPointDice");
    });

    return addClass(new DndClass(state.id, state.name, state.hitDie, state.hitPointsFirstLevel, state.hitPointDice));
  }

  public static DndClass addClass(final DndClass dndClass) {
    if(classes.put(dndClass.id, dndClass) == null) {
      Mcnd.logger.info("Loaded class {}", dndClass.id);
    } else {
      Mcnd.logger.info("Reloaded class {}", dndClass.id);
    }

    return dndClass;
  }

  private static class ClassState {
    private String id;
    private String name;
    private Die hitDie;
    private Dice hitPointsFirstLevel;
    private Dice hitPointDice;
  }
}
