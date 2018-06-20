package us.corielicio.mcnd.utils;

import us.corielicio.mcnd.dice.ConstantBonus;
import us.corielicio.mcnd.dice.Dice;
import us.corielicio.mcnd.dice.Die;
import us.corielicio.mcnd.dice.StatBonus;
import us.corielicio.mcnd.stats.Stats;

import java.util.*;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Assertions {
  private Assertions() { }

  public static void check(final Consumer<Assertions> callback) throws AssertionException {
    final Assertions assertions = new Assertions();
    callback.accept(assertions);

    if(!assertions.messages.isEmpty()) {
      throw new AssertionException(assertions.messages.toArray(new String[0]));
    }
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
    // Match a list of dice
    if(object instanceof Collection) {
      final Die[] dice = new Die[((Collection)object).size()];

      int index = 0;
      for(final Object obj : (Iterable)object) {
        dice[index++] = this.dice(obj, message);
      }

      return new Dice(dice);
    }

    // Match constants (eg. 8)
    if(object instanceof Integer) {
      return new Dice(new ConstantBonus((Integer)object));
    }

    if(object instanceof String) {
      // Match stats (eg. CON)
      for(final Stats stat : Stats.values()) {
        if(((String)object).toUpperCase(Locale.ENGLISH).equals(stat.name())) {
          return new Dice(new StatBonus(stat));
        }
      }

      // Match dice (eg. 3d4)
      final Matcher matcher = diePattern.matcher((CharSequence)object);

      if(matcher.matches()) {
        final Die[] dice = new Die[Integer.parseInt(matcher.group(1))];
        Arrays.fill(dice, new Die(Integer.parseInt(matcher.group(2))));
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

  public static class AssertionException extends Exception {
    private final String[] messages;

    public AssertionException(final String[] messages) {
      this.messages = messages;
    }
  }
}
