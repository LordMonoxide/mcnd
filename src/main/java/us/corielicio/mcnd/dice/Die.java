package us.corielicio.mcnd.dice;

import us.corielicio.mcnd.characters.CharacterSheet;

import java.util.Random;

public class Die {
  public static final Die D4 = new Die(4);
  public static final Die D6 = new Die(6);
  public static final Die D8 = new Die(8);
  public static final Die D10 = new Die(10);
  public static final Die D20 = new Die(20);

  private static final Random rand = new Random();

  public final int value;
  protected final int min;
  protected final int max;

  public Die(final int value) {
    this(value, Integer.MIN_VALUE, Integer.MAX_VALUE);
  }

  public Die(final int value, final int min, final int max) {
    this.value = value;
    this.min = min;
    this.max = max;
  }

  public int roll(final CharacterSheet character) {
    return this.clamp(rand.nextInt(this.value) + 1);
  }

  protected int clamp(final int value) {
    return Math.max(this.min, Math.min(value, this.max));
  }

  @Override
  public String toString() {
    String string = "1d" + this.value;

    if(this.min != Integer.MIN_VALUE) {
      string += " >= " + this.min;
    }

    if(this.max != Integer.MAX_VALUE) {
      string += " <= " + this.max;
    }

    return string;
  }
}
