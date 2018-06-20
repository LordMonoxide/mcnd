package us.corielicio.mcnd.dice;

import us.corielicio.mcnd.stats.CharacterStats;

public class ConstantBonus extends Die {
  public ConstantBonus(final int value) {
    super(value);
  }

  @Override
  public int roll(final CharacterStats character) {
    return this.value;
  }

  @Override
  public String toString() {
    return Integer.toString(this.value);
  }
}
