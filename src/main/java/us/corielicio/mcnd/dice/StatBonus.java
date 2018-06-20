package us.corielicio.mcnd.dice;

import us.corielicio.mcnd.stats.CharacterStats;
import us.corielicio.mcnd.stats.Stats;

public class StatBonus extends Die {
  private final Stats stat;

  public StatBonus(final Stats stat) {
    super(0);
    this.stat = stat;
  }

  @Override
  public int roll(final CharacterStats character) {
    return character.stat(this.stat).bonus();
  }

  @Override
  public String toString() {
    return this.stat.name();
  }
}
