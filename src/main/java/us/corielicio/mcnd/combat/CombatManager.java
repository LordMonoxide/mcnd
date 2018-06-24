package us.corielicio.mcnd.combat;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;
import us.corielicio.mcnd.Mcnd;

import java.util.ArrayList;
import java.util.List;

public class CombatManager {
  //TODO: this is set low for testing
  private static final int COMBAT_RADIUS = 8;

  private final List<CombatZone> zones = new ArrayList<>();

  public boolean isCombatStarted() {
    return !this.zones.isEmpty();
  }

  public void clear() {
    Mcnd.logger.info("Combat cleared");

    this.zones.clear();
  }

  private CombatZone getZoneForCombattant(final EntityLivingBase combattant) {
    Mcnd.logger.info("Getting combat zone for {}", combattant);

    for(final CombatZone zone : this.zones) {
      if(zone.contains(combattant)) {
        Mcnd.logger.info("Found zone {}", zone.id);
        return zone;
      }
    }

    return this.addCombatZone(new BlockPos(combattant), COMBAT_RADIUS);
  }

  private CombatZone addCombatZone(final BlockPos center, final int radius) {
    Mcnd.logger.info("Adding a new combat radius at {} with a radius of {}", center, radius);

    final CombatZone.CombatRadius newRadius = new CombatZone.CombatRadius(center, radius);

    // We need to figure out if this radius falls within already-existing zones...

    CombatZone firstZoneFound = null;
    final List<CombatZone> toRemove = new ArrayList<>();

    for(final CombatZone zone : this.zones) {
      if(zone.intersects(newRadius)) {
        if(firstZoneFound == null) {
          // This is the first zone it's intersected with, just add it to that zone

          Mcnd.logger.info("Adding radius to zone {}", zone.id);
          firstZoneFound = zone;
          zone.addRadius(newRadius);
        } else {
          // If we find MORE zones that it intersects with, we need to merge those zones

          Mcnd.logger.info("Multiple intersections, merging zone {} into zone {}", zone.id, firstZoneFound.id);
          toRemove.add(zone);
          firstZoneFound.merge(zone);
        }
      }
    }

    if(firstZoneFound != null) {
      // Remove all merged zones (if any)
      this.zones.removeAll(toRemove);

      return firstZoneFound;
    }

    // No zone found
    final CombatZone zone = new CombatZone();

    Mcnd.logger.info("Adding new zone {}", zone.id);

    zone.addRadius(newRadius);
    this.zones.add(zone);
    return zone;
  }

  public void addCombattant(final WorldServer world, final EntityLivingBase instigator) {
    final CombatZone zone = this.getZoneForCombattant(instigator);

    final List<EntityLivingBase> entities = world.getEntities(EntityLivingBase.class, zone::contains);

    for(final EntityLivingBase combattant : entities) {
      if(!zone.hasCombattant(combattant)) {
        Mcnd.logger.info("{} is joining combat zone {}", combattant, zone.id);

        zone.addCombattant(combattant);
      }
    }
  }
}
