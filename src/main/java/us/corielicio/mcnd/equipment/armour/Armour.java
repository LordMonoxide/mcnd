package us.corielicio.mcnd.equipment.armour;

import us.corielicio.mcnd.dice.Dice;

public class Armour {
  public final String id;
  public final String name;
  public final int cost;
  public final Dice ac;
  public final int weight;
  public final int strengthRequired;
  public final boolean stealthDisadvantage;

  public Armour(final String id, final String name, final int cost, final Dice ac, final int weight, final int strengthRequired, final boolean stealthDisadvantage) {
    this.id = id;
    this.name = name;
    this.cost = cost;
    this.ac = ac;
    this.weight = weight;
    this.strengthRequired = strengthRequired;
    this.stealthDisadvantage = stealthDisadvantage;
  }
}
