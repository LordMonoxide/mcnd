package us.corielicio.mcnd.combat;

import net.minecraft.util.math.BlockPos;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class CombatManager {
  private final List<CombatZone> zones = new ArrayList<>();

  public void addCombatZone(final CombatZone zone) {
    this.zones.add(zone);
  }

  @Nullable
  public CombatZone getZone(final BlockPos pos) {
    for(final CombatZone zone : this.zones) {
      if(zone.contains(pos)) {
        return zone;
      }
    }

    return null;
  }
}
