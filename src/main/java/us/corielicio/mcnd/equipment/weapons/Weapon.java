package us.corielicio.mcnd.equipment.weapons;

import us.corielicio.mcnd.dice.Dice;
import us.corielicio.mcnd.utils.loader.GameData;

import java.util.HashMap;
import java.util.Map;

public class Weapon extends GameData {
  public final String name;
  public final WeaponType type;
  public final WeaponCategory category;
  public final Dice damage;
  public final DamageType damageType;
  public final int cost;
  public final float weight;

  private final Map<String, WeaponProperty> properties = new HashMap<>();

  public Weapon(final String id, final String name, final WeaponType type, final WeaponCategory category, final Dice damage, final DamageType damageType, final int cost, final float weight, final Map<String, ? extends WeaponProperty> properties) {
    super(id);
    this.name = name;
    this.type = type;
    this.category = category;
    this.damage = damage;
    this.damageType = damageType;
    this.cost = cost;
    this.weight = weight;
    this.properties.putAll(properties);
  }

  public boolean hasProperty(final String name) {
    return this.properties.containsKey(name);
  }

  public <T extends WeaponProperty> T getProperty(final String name) {
    //noinspection unchecked
    return (T)this.properties.get(name);
  }
}
