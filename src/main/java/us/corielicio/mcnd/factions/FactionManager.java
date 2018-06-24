package us.corielicio.mcnd.factions;

import us.corielicio.mcnd.utils.loader.Loader;
import us.corielicio.mcnd.utils.loader.ObjectBuilder;

import java.util.Map;

public class FactionManager extends Loader<Faction> {
  public FactionManager() {
    super("faction");
  }

  @Override
  protected Faction builder(final ObjectBuilder builder, final Map<String, Object> root) {
    final String id = builder.string(root.get("id"), "Invalid ID");
    final String name = builder.string(root.get("name"), "Invalid name");
    return new Faction(id, name);
  }
}
