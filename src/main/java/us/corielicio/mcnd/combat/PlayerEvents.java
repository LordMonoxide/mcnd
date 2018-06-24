package us.corielicio.mcnd.combat;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.WorldServer;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import us.corielicio.mcnd.Mcnd;
import us.corielicio.mcnd.characters.CapabilityCharacterSheet;
import us.corielicio.mcnd.characters.CharacterSheet;
import us.corielicio.mcnd.characters.Stats;
import us.corielicio.mcnd.dice.Die;
import us.corielicio.mcnd.equipment.weapons.DamageType;
import us.corielicio.mcnd.equipment.weapons.Weapon;
import us.corielicio.mcnd.equipment.weapons.WeaponProperty;
import us.corielicio.mcnd.equipment.weapons.WeaponType;
import us.corielicio.mcnd.items.DynamicItem;

@Mod.EventBusSubscriber(modid = Mcnd.MODID)
public class PlayerEvents {
  private PlayerEvents() { }

  @SubscribeEvent
  public static void onAttackEntity(final AttackEntityEvent event) {
    event.setCanceled(true);

    final EntityPlayer player = event.getEntityPlayer();

    if(player.getEntityWorld().isRemote) {
      return;
    }

    final WorldServer world = (WorldServer)player.getEntityWorld();

    if(!Mcnd.COMBAT.isCombatStarted()) {
      world.getMinecraftServer().getPlayerList().sendMessage(new TextComponentTranslation("combat.started", player.getDisplayNameString()));
    }

    Mcnd.COMBAT.addCombattant(world, player);

    final ItemStack stack = player.getHeldItem(player.getActiveHand());
    final Weapon weapon = DynamicItem.getWeapon(stack);

    final int damage;
    final DamageType damageType;

    final CharacterSheet character = player.getCapability(CapabilityCharacterSheet.CHARACTER_SHEET_CAPABILITY, null);

    //TODO: hit check
    if(weapon != null && weapon.type == WeaponType.MELEE) {
      final CharacterSheet.Stat stat =
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

  @SubscribeEvent
  public static void onMove(final LivingEvent.LivingUpdateEvent event) {
    if(Mcnd.COMBAT.isCombatStarted()) {
      event.setCanceled(true);
    }
  }
}
