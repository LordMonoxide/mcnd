package us.corielicio.mcnd.combat;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import us.corielicio.mcnd.Mcnd;
import us.corielicio.mcnd.dice.Die;
import us.corielicio.mcnd.equipment.weapons.DamageType;
import us.corielicio.mcnd.equipment.weapons.Weapon;
import us.corielicio.mcnd.equipment.weapons.WeaponProperty;
import us.corielicio.mcnd.equipment.weapons.WeaponType;
import us.corielicio.mcnd.items.DynamicItem;
import us.corielicio.mcnd.stats.CapabilityCharacterStats;
import us.corielicio.mcnd.stats.CharacterStats;
import us.corielicio.mcnd.stats.Stats;

@Mod.EventBusSubscriber(modid = Mcnd.MODID)
public class CombatEvents {
  @SubscribeEvent
  public static void onAttackEntity(final AttackEntityEvent event) {
    event.setCanceled(true);

    final EntityPlayer player = event.getEntityPlayer();

    if(player.getEntityWorld().isRemote) {
      return;
    }

    final ItemStack stack = player.getHeldItem(player.getActiveHand());
    final Weapon weapon = DynamicItem.getWeapon(stack);

    final int damage;
    final DamageType damageType;

    final CharacterStats character = player.getCapability(CapabilityCharacterStats.CHARACTER_STATS_CAPABILITY, null);

    //TODO: hit check
    if(weapon != null && weapon.type == WeaponType.MELEE) {
      final CharacterStats.Stat stat =
          weapon.hasProperty(WeaponProperty.VERSATILE) &&
          character.stat(Stats.DEX).bonus() > character.stat(Stats.STR).bonus() ?
              character.stat(Stats.DEX) :
              character.stat(Stats.STR);

      damage = weapon.damage.roll(character) + stat.bonus();
      damageType = weapon.damageType;
    } else {
      damage = Die.D4.roll(character);
      damageType = DamageType.BLUDGEONING;
    }

    event.getEntityPlayer().sendStatusMessage(new TextComponentTranslation(damageType.lang + ".display", damage), false);
  }
}
