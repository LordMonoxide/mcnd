package us.corielicio.mcnd.factions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Factions implements Iterable<Faction> {
  private final List<Faction> factions = new ArrayList<>();

  public boolean isEmpty() {
    return this.factions.isEmpty();
  }

  public void add(final Faction faction) {
    this.factions.add(faction);
  }

  public void add(final Factions factions) {
    this.factions.addAll(factions.factions);
  }

  public void clear() {
    this.factions.clear();
  }

  @Override
  public Iterator<Faction> iterator() {
    return this.factions.iterator();
  }
}
