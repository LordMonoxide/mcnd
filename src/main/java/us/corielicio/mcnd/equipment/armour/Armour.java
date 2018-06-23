package us.corielicio.mcnd.equipment.armour;

import us.corielicio.mcnd.dice.Dice;
import us.corielicio.mcnd.utils.loader.GameData;

public class Armour extends GameData {
  public final ArmourType type;
  public final int cost;
  public final Dice ac;
  public final int weight;
  public final int strengthRequired;
  public final boolean stealthDisadvantage;

  public Armour(final String id, final ArmourType type, final int cost, final Dice ac, final int weight, final int strengthRequired, final boolean stealthDisadvantage) {
    super(id);
    this.type = type;
    this.cost = cost;
    this.ac = ac;
    this.weight = weight;
    this.strengthRequired = strengthRequired;
    this.stealthDisadvantage = stealthDisadvantage;
  }
}
