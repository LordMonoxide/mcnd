package us.corielicio.mcnd.equipment.weapons.ammunition;

import us.corielicio.mcnd.utils.loader.Loader;
import us.corielicio.mcnd.utils.loader.ObjectBuilder;

import java.util.Map;

public class Ammunitions extends Loader<Ammunition> {
  public Ammunitions() {
    super("ammunition");
  }

  @Override
  protected Ammunition builder(final ObjectBuilder builder, final Map<String, Object> root) {
    final String id = builder.string(root.get("id"), "Invalid ID");

    return new Ammunition(id);
  }
}
