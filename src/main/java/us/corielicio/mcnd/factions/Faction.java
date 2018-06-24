package us.corielicio.mcnd.factions;

import us.corielicio.mcnd.utils.loader.GameData;

public class Faction extends GameData {
  public final String name;

  public Faction(final String id, final String name) {
    super(id);
    this.name = name;
  }
}
