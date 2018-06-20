package us.corielicio.mcnd.classes;

import us.corielicio.mcnd.dice.Dice;
import us.corielicio.mcnd.dice.Die;

public class DndClass {
  public final String id;
  public final String name;
  public final Die hitDie;
  public final Dice hitPointsFirstLevel;
  public final Dice hitPointDice;

  public DndClass(final String id, final String name, final Die hitDie, final Dice hitPointsFirstLevel, final Dice hitPointDice) {
    this.id = id;
    this.name = name;
    this.hitDie = hitDie;
    this.hitPointsFirstLevel = hitPointsFirstLevel;
    this.hitPointDice = hitPointDice;
  }
}
