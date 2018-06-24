package us.corielicio.mcnd.combat;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;

public class CombatZone {
  private static int ZONE_ID = 0;

  public final int id = ZONE_ID++;

  private final List<CombatRadius> radii = new ArrayList<>();
  private final List<EntityLivingBase> combattants = new ArrayList<>();

  public void addRadius(final CombatRadius radius) {
    this.radii.add(radius);
  }

  public void addCombattant(final EntityLivingBase combattant) {
    this.combattants.add(combattant);
  }

  public void merge(final CombatZone other) {
    this.radii.addAll(other.radii);
    this.combattants.addAll(other.combattants);
  }

  public boolean contains(final EntityLivingBase combattant) {
    for(final CombatRadius radius : this.radii) {
      if(radius.contains(combattant)) {
        return true;
      }
    }

    return false;
  }

  public boolean hasCombattant(final EntityLivingBase combattant) {
    return this.combattants.contains(combattant);
  }

  public boolean intersects(final CombatRadius other) {
    for(final CombatRadius radius : this.radii) {
      if(other.intersects(radius)) {
        return true;
      }
    }

    return false;
  }

  public static class CombatRadius {
    public final BlockPos center;
    public final int radius;

    public CombatRadius(final BlockPos center, final int radius) {
      this.center = center.add(0, -center.getY(), 0); // Don't compare heights
      this.radius = radius;
    }

    public boolean contains(final EntityLivingBase combattant) {
      return combattant.getDistance(this.center.getX(), combattant.posY, this.center.getZ()) <= this.radius;
    }

    public boolean intersects(final CombatRadius other) {
      return this.center.getDistance(other.center.getX(), 0, other.center.getZ()) <= this.radius + other.radius;
    }
  }
}
