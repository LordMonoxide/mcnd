package us.corielicio.mcnd.dice;

import us.corielicio.mcnd.stats.CharacterStats;

import java.util.Random;

public class Die {
  public static final Die D4 = new Die(4);
  public static final Die D6 = new Die(6);
  public static final Die D8 = new Die(8);
  public static final Die D10 = new Die(10);
  public static final Die D20 = new Die(20);

  private static final Random rand = new Random();

  public final int value;

  public Die(final int value) {
    this.value = value;
  }

  public int roll(final CharacterStats character) {
    return rand.nextInt(this.value) + 1;
  }

  @Override
  public String toString() {
    return "1d" + this.value;
  }
}
