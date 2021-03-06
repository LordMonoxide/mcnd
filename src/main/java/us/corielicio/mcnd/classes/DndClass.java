package us.corielicio.mcnd.classes;

import us.corielicio.mcnd.dice.Dice;
import us.corielicio.mcnd.dice.Die;
import us.corielicio.mcnd.utils.loader.GameData;

public class DndClass extends GameData {
  public final Die hitDie;
  public final Dice hitPointsFirstLevel;
  public final Dice hitPointDice;

  public DndClass(final String id, final Die hitDie, final Dice hitPointsFirstLevel, final Dice hitPointDice) {
    super(id);
    this.hitDie = hitDie;
    this.hitPointsFirstLevel = hitPointsFirstLevel;
    this.hitPointDice = hitPointDice;
  }
}
