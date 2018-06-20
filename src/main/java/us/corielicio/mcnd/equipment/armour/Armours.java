package us.corielicio.mcnd.equipment.armour;

import us.corielicio.mcnd.dice.Dice;
import us.corielicio.mcnd.utils.loader.Loader;
import us.corielicio.mcnd.utils.loader.ObjectBuilder;

import java.util.Map;

public class Armours extends Loader<Armour> {
  public Armours() {
    super("armour");
  }

  @Override
  protected Armour builder(final ObjectBuilder builder, final Map<String, Object> root) {
    final String id = builder.string(root.get("id"), "Invalid ID");
    final String name = builder.string(root.get("name"), "Invalid name");
    final int cost = builder.integer(root.get("cost"), "Invalid cost");
    final Dice ac = builder.dice(root.get("ac"), "Invalid ac");
    final int weight = builder.integer(root.get("weight"), "Invalid weight");
    final int strengthRequired = builder.integer(root.get("strengthRequired"), "Invalid strengthRequired");
    final boolean stealthDisadvantage = builder.bool(root.get("stealthDisadvantage"), "Invalid stealthDisadvantage");

    return new Armour(id, name, cost, ac, weight, strengthRequired, stealthDisadvantage);
  }
}
