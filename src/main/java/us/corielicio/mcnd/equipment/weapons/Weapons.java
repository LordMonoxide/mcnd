package us.corielicio.mcnd.equipment.weapons;

import us.corielicio.mcnd.dice.Dice;
import us.corielicio.mcnd.utils.loader.Loader;
import us.corielicio.mcnd.utils.loader.ObjectBuilder;

import java.util.HashMap;
import java.util.Map;

public class Weapons extends Loader<Weapon> {
  public Weapons() {
    super("weapon");
  }

  @Override
  protected Weapon builder(final ObjectBuilder builder, final Map<String, Object> root) {
    final String id = builder.string(root.get("id"), "Invalid ID");
    final String name = builder.string(root.get("name"), "Invalid name");
    final WeaponType type = builder.enumeration(root.get("type"), WeaponType.class, "Invalid type");
    final WeaponCategory category = builder.enumeration(root.get("category"), WeaponCategory.class, "Invalid category");
    final Dice damage = builder.dice(root.get("damage"), "Invalid damage");
    final DamageType damageType = builder.enumeration(root.get("damageType"), DamageType.class, "Invalid damageType");
    final int cost = builder.integer(root.get("cost"), "Invalid cost");
    final float weight = builder.decimal(root.get("weight"), "Invalid weight");

    final Map<String, WeaponProperty> properties = new HashMap<>();

    if(root.containsKey("properties")) {
      for(final Object item : builder.list(root.get("properties"), "Invalid properties")) {
        final Map<String, Object> property = builder.map(item, "Invalid property");
        final String propName = builder.string(property.get("property"), "Invalid property name");
        final WeaponProperty prop = builder.notNull(WeaponProperty.loadWeaponProperty(propName, builder, property), "Invalid property " + propName);

        properties.put(propName, prop);
      }
    }

    return new Weapon(id, name, type, category, damage, damageType, cost, weight, properties);
  }
}
