package us.corielicio.mcnd.equipment.weapons;

public enum WeaponCategory {
  SIMPLE("weapon.category.simple"),
  MARTIAL("weapon.category.martial");

  public final String lang;

  WeaponCategory(final String lang) {
    this.lang = lang;
  }
}
