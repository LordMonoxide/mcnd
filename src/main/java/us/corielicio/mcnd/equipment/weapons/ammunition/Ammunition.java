package us.corielicio.mcnd.equipment.weapons.ammunition;

import us.corielicio.mcnd.utils.loader.GameData;

public class Ammunition extends GameData {
  private final String name;

  public Ammunition(final String id, final String name) {
    super(id);
    this.name = name;
  }
}
