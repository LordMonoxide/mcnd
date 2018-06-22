package us.corielicio.mcnd.equipment.weapons;

public enum DamageType {
  BLUDGEONING("weapon.damage.bludgeoning"),
  PIERCING("weapon.damage.piercing"),
  SLASHING("weapon.damage.slashing");

  public final String lang;

  DamageType(final String lang) {
    this.lang = lang;
  }
}
