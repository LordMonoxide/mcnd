package us.corielicio.mcnd.equipment.weapons;

public enum WeaponType {
  MELEE("weapon.type.melee"),
  RANGED("weapon.type.ranged");

  public final String lang;

  WeaponType(final String lang) {
    this.lang = lang;
  }
}
