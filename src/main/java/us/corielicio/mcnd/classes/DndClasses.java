package us.corielicio.mcnd.classes;

import us.corielicio.mcnd.dice.Dice;
import us.corielicio.mcnd.utils.loader.Loader;
import us.corielicio.mcnd.utils.loader.ObjectBuilder;

import java.util.Map;

public class DndClasses extends Loader<DndClass> {
  public DndClasses() {
    super("class");
  }

  @Override
  protected DndClass builder(final ObjectBuilder builder, final Map<String, Object> root) {
    final String id = builder.string(root.get("id"), "Invalid ID");
    final Dice hitDie = builder.dice(root.get("hitDie"), "Invalid hitDie");
    final Dice hitPointsFirstLevel = builder.dice(root.get("hitPointsFirstLevel"), "Invalid hitPointsFirstLevel");
    final Dice hitPointDice = builder.dice(root.get("hitPointDice"), "Invalid hitPointDice");

    return new DndClass(id, hitDie, hitPointsFirstLevel, hitPointDice);
  }
}
