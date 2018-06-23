package us.corielicio.mcnd.dice;

import us.corielicio.mcnd.characters.CharacterSheet;
import us.corielicio.mcnd.characters.Stats;

public class StatBonus extends Die {
  private final Stats stat;

  public StatBonus(final Stats stat) {
    super(0);
    this.stat = stat;
  }

  public StatBonus(final Stats stat, final int min, final int max) {
    super(0, min, max);
    this.stat = stat;
  }

  @Override
  public int roll(final CharacterSheet character) {
    return this.clamp(character.stat(this.stat).bonus());
  }

  @Override
  public String toString() {
    String string = this.stat.name();

    if(this.min != Integer.MIN_VALUE) {
      string += " >= " + this.min;
    }

    if(this.max != Integer.MAX_VALUE) {
      string += " <= " + this.max;
    }

    return string;
  }
}
