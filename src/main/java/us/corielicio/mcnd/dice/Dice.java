package us.corielicio.mcnd.dice;

import us.corielicio.mcnd.characters.CharacterSheet;

import java.util.Arrays;

public class Dice extends Die {
  private final Die[] dice;

  public Dice(final Die... dice) {
    super(0);
    this.dice = dice;
  }

  @Override
  public int roll(final CharacterSheet character) {
    int total = 0;

    for(final Die die : this.dice) {
      total += die.roll(character);
    }

    return total;
  }

  public String toString() {
    return Arrays.toString(this.dice);
  }
}
