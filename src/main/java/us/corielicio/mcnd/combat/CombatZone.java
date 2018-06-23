package us.corielicio.mcnd.combat;

import net.minecraft.util.math.BlockPos;

public class CombatZone {
  private final BlockPos center;
  private final int radius;
  private final int radiusSq;

  public CombatZone(final BlockPos center, final int radius) {
    this.center = center;
    this.radius = radius;
    this.radiusSq = radius * radius;
  }

  public boolean contains(final BlockPos pos) {
    return pos.distanceSq(this.center) <= this.radiusSq;
  }
}
