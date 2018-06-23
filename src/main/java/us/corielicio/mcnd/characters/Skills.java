package us.corielicio.mcnd.characters;

public enum Skills {
  acrobatics(Stats.DEX),
  animalHandling(Stats.WIS),
  arcana(Stats.INT),
  athletics(Stats.STR),
  deception(Stats.CHA),
  history(Stats.INT),
  insight(Stats.WIS),
  intimidation(Stats.CHA),
  investigation(Stats.INT),
  medicine(Stats.WIS),
  nature(Stats.INT),
  perception(Stats.WIS),
  performance(Stats.CHA),
  persuasion(Stats.CHA),
  religion(Stats.INT),
  sleightOfHand(Stats.DEX),
  stealth(Stats.DEX),
  survival(Stats.WIS);

  public final Stats stat;

  Skills(final Stats stat) {
    this.stat = stat;
  }
}
