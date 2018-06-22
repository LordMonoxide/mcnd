package us.corielicio.mcnd.equipment.armour;

public enum ArmourType {
  LIGHT("armour.type.light"),
  MEDIUM("armour.type.medium"),
  HEAVY("armour.type.heavy");

  public final String lang;

  ArmourType(final String lang) {
    this.lang = lang;
  }
}
