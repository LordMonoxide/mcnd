package us.corielicio.mcnd.utils.loader;

import us.corielicio.mcnd.dice.ConstantBonus;
import us.corielicio.mcnd.dice.Dice;
import us.corielicio.mcnd.dice.Die;
import us.corielicio.mcnd.dice.StatBonus;
import us.corielicio.mcnd.stats.Stats;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.BiFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class ObjectBuilder {
  private ObjectBuilder() { }

  public static <T> T build(final Map<String, Object> root, final BiFunction<ObjectBuilder, Map<String, Object>, T> callback) throws AssertionException {
    final ObjectBuilder builder = new ObjectBuilder();
    final T ret = callback.apply(builder, root);

    if(!builder.messages.isEmpty()) {
      throw new AssertionException(builder.messages.toArray(new String[0]));
    }

    return ret;
  }

  private final List<String> messages = new ArrayList<>();

  private static final Pattern diePattern = Pattern.compile("^(\\d+)d(\\d+)$");

  public String string(final Object object, final String message) {
    if(object instanceof String) {
      return (String)object;
    }

    this.messages.add(message);
    return "";
  }

  public Dice dice(final Object object, final String message) {
    return this.dice(object, Integer.MIN_VALUE, Integer.MAX_VALUE, message);
  }

  public Dice dice(final Object object, final int min, final int max, final String message) {
    // Match maps that might have min/max
    if(object instanceof Map) {
      final Map<String, Object> map = (Map<String, Object>)object;

      final String type = this.string(map.get("type"), message);
      final int min1 = this.integer(map.getOrDefault("min", Integer.MIN_VALUE), message);
      final int max1 = this.integer(map.getOrDefault("max", Integer.MAX_VALUE), message);

      return this.dice(type, min1, max1, message);
    }

    // Match a list of dice
    if(object instanceof Collection) {
      final Die[] dice = new Die[((Collection)object).size()];

      int index = 0;
      for(final Object obj : (Iterable)object) {
        dice[index++] = this.dice(obj, min, max, message);
      }

      return new Dice(dice);
    }

    // Match constants (eg. 8)
    if(object instanceof Integer) {
      return new Dice(new ConstantBonus((Integer)object)); // No min/max for constants
    }

    if(object instanceof String) {
      // Match stats (eg. CON)
      for(final Stats stat : Stats.values()) {
        if(((String)object).toUpperCase(Locale.ENGLISH).equals(stat.name())) {
          return new Dice(new StatBonus(stat, min, max));
        }
      }

      // Match dice (eg. 3d4)
      final Matcher matcher = diePattern.matcher((CharSequence)object);

      if(matcher.matches()) {
        final Die[] dice = new Die[Integer.parseInt(matcher.group(1))];
        Arrays.fill(dice, new Die(Integer.parseInt(matcher.group(2)), min, max));
        return new Dice(dice);
      }
    }

    this.messages.add(message);
    return new Dice();
  }

  public int integer(final Object object, final String message) {
    if(object instanceof Integer) {
      return (Integer)object;
    }

    this.messages.add(message);
    return 0;
  }

  public boolean bool(final Object object, final String message) {
    if(object instanceof Boolean) {
      return (Boolean)object;
    }

    this.messages.add(message);
    return false;
  }

  @Nullable
  public <T extends Enum<T>> T enumeration(final Object object, final Class<T> enumClass, final String message) {
    try {
      return T.valueOf(enumClass, this.string(object, message).toUpperCase(Locale.ENGLISH));
    } catch(final IllegalArgumentException e) { }

    this.messages.add(message);
    return null;
  }

  public static class AssertionException extends Exception {
    private final String[] messages;

    public AssertionException(final String[] messages) {
      this.messages = messages;
    }
  }
}
