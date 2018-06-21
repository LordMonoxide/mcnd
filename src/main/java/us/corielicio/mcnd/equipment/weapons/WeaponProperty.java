package us.corielicio.mcnd.equipment.weapons;

import us.corielicio.mcnd.dice.Dice;
import us.corielicio.mcnd.utils.loader.ObjectBuilder;

import java.util.HashMap;
import java.util.Map;

public abstract class WeaponProperty {
  private static final Map<String, ObjectBuilder.BuilderFunction<WeaponProperty>> properties = new HashMap<>();

  public static final String AMMUNITION = "ammunition";
  public static final String FINESSE = "finesse";
  public static final String HEAVY = "heavy";
  public static final String LIGHT = "light";
  public static final String LOADING = "loading";
  public static final String RANGE = "range";
  public static final String REACH = "reach";
  public static final String THROWN = "thrown";
  public static final String TWO_HANDED = "two_handed";
  public static final String VERSATILE = "versatile";

  static {
    addProperty(AMMUNITION, Ammunition::new);
    addProperty(FINESSE, Finesse::new);
    addProperty(HEAVY, Heavy::new);
    addProperty(LIGHT, Light::new);
    addProperty(LOADING, Loading::new);
    addProperty(RANGE, Range::new);
    addProperty(REACH, Reach::new);
    addProperty(THROWN, Range::new);
    addProperty(TWO_HANDED, TwoHanded::new);
    addProperty(VERSATILE, Versatile::new);
  }

  public static WeaponProperty loadWeaponProperty(final String name, final ObjectBuilder builder, final Map<String, Object> root) {
    return properties.getOrDefault(name, (b, r) -> null).apply(builder, root);
  }

  public static ObjectBuilder.BuilderFunction<WeaponProperty> addProperty(final String name, final ObjectBuilder.BuilderFunction<WeaponProperty> property) {
    properties.put(name, property);
    return property;
  }

  public static class Ammunition extends WeaponProperty {
    public final us.corielicio.mcnd.equipment.weapons.ammunition.Ammunition ammunition;

    public Ammunition(final ObjectBuilder builder, final Map<String, Object> root) {
      this(null);
      //TODO
      throw new RuntimeException("Ammunition property not yet implemented");
    }

    public Ammunition(final us.corielicio.mcnd.equipment.weapons.ammunition.Ammunition ammunition) {
      this.ammunition = ammunition;
    }
  }

  public static class Finesse extends WeaponProperty {
    public Finesse(final ObjectBuilder builder, final Map<String, Object> root) { }
  }

  public static class Heavy extends WeaponProperty {
    public Heavy(final ObjectBuilder builder, final Map<String, Object> root) { }
  }

  public static class Light extends WeaponProperty {
    public Light(final ObjectBuilder builder, final Map<String, Object> root) { }
  }

  public static class Loading extends WeaponProperty {
    public Loading(final ObjectBuilder builder, final Map<String, Object> root) { }
  }

  public static class Range extends WeaponProperty {
    public final int ideal;
    public final int max;

    public Range(final ObjectBuilder builder, final Map<String, Object> root) {
      this(
          builder.integer(root.get("ideal"), "Error getting ideal range"),
          builder.integer(root.get("max"), "Error getting max range")
      );
    }

    public Range(final int ideal, final int max) {
      this.ideal = ideal;
      this.max = max;
    }
  }

  public static class Reach extends WeaponProperty {
    public Reach(final ObjectBuilder builder, final Map<String, Object> root) { }
  }

  public static class TwoHanded extends WeaponProperty {
    public TwoHanded(final ObjectBuilder builder, final Map<String, Object> root) { }
  }

  public static class Versatile extends WeaponProperty {
    public final Dice damage;

    public Versatile(final ObjectBuilder builder, final Map<String, Object> root) {
      this(builder.dice(root.get("damage"), "Error getting versatile damage"));
    }

    public Versatile(final Dice damage) {
      this.damage = damage;
    }
  }
}
