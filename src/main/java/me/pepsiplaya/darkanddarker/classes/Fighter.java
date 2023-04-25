package me.pepsiplaya.darkanddarker.classes;

import me.pepsiplaya.darkanddarker.DarkAndDarker;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;

public class Fighter extends DarkAndDarker {
    private Player player;
    private int health;
    private int actionSpeed;
    private int movementSpeed;
    private int level;
    private int comboAttackHits = 0;
    private long lastHitTime = 0;
    private Plugin plugin;

    private List<String> selectedAbilities;
    private List<String> activePerks;

    public Fighter(Player player, int level, Plugin plugin) {
        this.player = player;
        this.level = level;
        this.selectedAbilities = new ArrayList<>();
        this.activePerks = new ArrayList<>();
        this.plugin = plugin;
        // Initialize the Fighter's stats, abilities, and perks
    }

    // Adrenaline Rush
    public static void adrenalineRush(JavaPlugin plugin, Player player) {
        int duration = 8 * 20; // 8 seconds, in ticks
        int amplifier = 4; // 25% action speed increase

        PotionEffect adrenalineRushEffect = new PotionEffect(PotionEffectType.SPEED, duration, amplifier);
        player.addPotionEffect(adrenalineRushEffect);

        // Schedule a delayed task to apply debuffs after the effect ends
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
            int debuffDuration = 4 * 20;
            int speedDebuffAmplifier = 1;
            int actionSpeedDebuffAmplifier = 1;

            PotionEffect speedDebuff = new PotionEffect(PotionEffectType.SLOW, debuffDuration, speedDebuffAmplifier);
            PotionEffect actionSpeedDebuff = new PotionEffect(PotionEffectType.SLOW_DIGGING, debuffDuration, actionSpeedDebuffAmplifier);

            player.addPotionEffect(speedDebuff);
            player.addPotionEffect(actionSpeedDebuff);
        }, duration);
    }

    public static void breakthrough(JavaPlugin plugin, Player player) {
        player.getActivePotionEffects().stream()
                .filter(effect -> effect.getType().equals(PotionEffectType.SLOW) || effect.getType().equals(PotionEffectType.SLOW_DIGGING))
                .forEach(effect -> player.removePotionEffect(effect.getType()));
    }

    public static void secondWind(JavaPlugin plugin, Player player) {
        int duration = 12 * 20;
        int amplifier = 0;

        PotionEffect regenEffect = new PotionEffect(PotionEffectType.REGENERATION, duration, amplifier);
        player.addPotionEffect(regenEffect);

        player.removePotionEffect(PotionEffectType.SLOW);
        player.removePotionEffect(PotionEffectType.SLOW_DIGGING);
    }

    public static void sprint(JavaPlugin plugin, Player player) {
        int duration = 6 * 20;
        int amplifier = 0;

        PotionEffect speedEffect = new PotionEffect(PotionEffectType.SPEED, duration, amplifier);
        player.addPotionEffect(speedEffect);
    }

    public static void taunt(JavaPlugin plugin, Player player) {
        // This method requires custom implementation for increasing aggro and defense rating.
        // Minecraft does not have built-in support for these mechanics, so you'll need to create them using additional plugins or custom code.
    }

    public static void victoryStrike(JavaPlugin plugin, Player player) {
        // This method requires custom implementation for adding weapon damage and healing.
        // Minecraft does not have built-in support for these mechanics, so you'll need to create them using additional plugins or custom code.
    }

    public static void perfectBlock(JavaPlugin plugin, Player player) {
        // This method requires custom implementation for perfect blocking.
        // Minecraft does not have built-in support for this mechanic, so you'll need to create it using additional plugins or custom code.
    }

    public static void shieldSlam(JavaPlugin plugin, Player player) {
        // This method requires custom implementation for inflicting damage and reducing movement speed on hit.
        // Minecraft does not have built-in support for these mechanics, so you'll need to create them using additional plugins or custom code.
    }

    // Second Wind
    public void secondWind(Player player) {
        double maxHealth = player.getMaxHealth();
        double healthToRegain = maxHealth * 0.5;
        int duration = 12 * 20; // 12 seconds, in ticks
        int ticksBetweenHeals = 20;

        // Calculate the health to regain per tick
        double healthPerTick = healthToRegain / (duration / ticksBetweenHeals);

        // Schedule a repeating task to heal the player
        BukkitTask healTask = Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            double newHealth = Math.min(player.getHealth() + healthPerTick, maxHealth);
            player.setHealth(newHealth);
        }, 0, ticksBetweenHeals);

        // Schedule a delayed task to cancel the healing task after the duration
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, healTask::cancel, duration);
    }

    // Sprint
    public void sprint(Player player) {
        int duration = 6 * 20; // 6 seconds, in ticks
        int amplifier = 2; // 50% movement speed increase

        PotionEffect sprintEffect = new PotionEffect(PotionEffectType.SPEED, duration, amplifier);
        player.addPotionEffect(sprintEffect);
    }

    // Taunt (You'll need to customize the behavior for aggro value and defense rating bonus)
    public void taunt(Player player) {
        double radius = 7.5;
        Location playerLocation = player.getLocation();
        List<Entity> nearbyEntities = player.getNearbyEntities(radius, radius, radius);

        // Increase aggro value for nearby monsters
        for (Entity entity : nearbyEntities) {
            if (entity instanceof Monster) {
                // Increase the aggro value for this monster
            }
        }

        // Apply the defense rating bonus (customize according to your plugin's mechanics)
    }

    // Victory Strike (You'll need to customize the behavior for applying the damage bonus on the next attack)
    public void victoryStrike(Player player) {
        // Apply a 20% weapon damage bonus on the next attack
    }

    // Shield Slam
    public void shieldSlam(Player player) {
        int damage = 25;
        int movementSpeedDebuffDuration = 2 * 20;
        int movementSpeedDebuffAmplifier = 1;

        // Get a targeted entity (customize the logic for targeting)
        Entity targetedEntity = null;

        if (targetedEntity instanceof LivingEntity) {
            LivingEntity livingTarget = (LivingEntity) targetedEntity;

            // Apply damage
            livingTarget.damage(damage, player);

            // Apply movement speed debuff
            PotionEffect movementSpeedDebuff = new PotionEffect(PotionEffectType.SLOW, movementSpeedDebuffDuration, movementSpeedDebuffAmplifier);
            livingTarget.addPotionEffect(movementSpeedDebuff);
        }
    }

    // Perks

    private void applyBarricadePerk(Player player) {
        /*if (isPlayerInDefensiveStance(player)) {
            // Assuming you have a method to modify the damage reduction
            modifyPlayerDamageReduction(player, 0.15);
        }*/
    }

    private void applyComboAttackPerk(Player player) {
        /*if (hasPlayerLandedTwoConsecutiveAttacks(player)) {
            // Assuming you have a method to modify the player's damage
            modifyPlayerDamage(player, 1.1);
        } else {
            modifyPlayerDamage(player, 1);
        }*/
    }

    private void applyCounterattackPerk(Player player) {
        /*if (hasPlayerBlockedAttack(player)) {
            // Assuming you have a method to modify the player's movement speed and attack speed
            modifyPlayerMovementSpeed(player, 1.1);
            modifyPlayerAttackSpeed(player, 1.1);

            // Set a timer to remove the bonus after 3 seconds
            new java.util.Timer().schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {
                            modifyPlayerMovementSpeed(player, 1);
                            modifyPlayerAttackSpeed(player, 1);
                        }
                    },
                    3000
            );
        }*/
    }

    private void applyDefenseExpertPerk(Player player) {
        int duration = 10000 * 20; // 8 seconds, in ticks
        int amplifier = 1; // 25% action speed increase

        PotionEffect defenseExpertEffect = new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, duration, amplifier);
        player.addPotionEffect(defenseExpertEffect);
        //modifyPlayerArmorRating(player, 1.1);
    }

    private void applyDualWieldPerk(Player player) {
        /*if (isPlayerDualWielding(player)) {
            // Assuming you have a method to modify the player's attack speed
            modifyPlayerAttackSpeed(player, 1.15);
        }*/
    }

    private void applyProjectileResistancePerk(Player player) {
        //modifyPlayerProjectileDamageReduction(player, 0.1);
    }

    private void applyShieldExpertPerk(Player player) {
        /*if (isPlayerInDefensiveStance(player)) {
            // Assuming you have a method to modify the player's movement speed
            modifyPlayerMovementSpeed(player, 1.1);
        } else {
            modifyPlayerMovementSpeed(player, 1);
        }*/
    }

    private void applySwiftPerk(Player player) {
        //modifyPlayerArmorMovementSpeedPenalty(player, -0.2);
    }

    private void applyWeaponMasteryPerk(Player player) {
        /*allowAllWeaponsForPlayer(player);

        // Assuming you have a method to check if the weapon is non-native for the player's class
        if (isNonNativeWeapon(player)) {
            // Assuming you have a method to modify the player's damage
            modifyPlayerDamage(player, 0.9);
        }*/
    }

    private void applyAdrenalineSpikePerk(Player player) {
        // Implement the 15% action speed increase when the Fighter's health reaches below 15%
    }

    private void applySlayerPerk(Player player) {
        // Implement the inability to equip plate armor and 5 weapon damage bonus when holding a weapon in each hand
    }

    public void applyPerkByName(String perkName) {
        if (activePerks.contains(perkName)) {
            applyPerk(perkName);
        } else {
            System.out.println("Perk not found in active perks: " + perkName);
        }
    }

    public void applyPerk(String perkName) {
        switch (perkName) {
            case "Barricade":
                applyBarricadePerk(player);
                break;
            case "ComboAttack":
                applyComboAttackPerk(player);
                break;
            case "Counterattack":
                applyCounterattackPerk(player);
                break;
            case "DefenseExpert":
                applyDefenseExpertPerk(player);
                break;
            case "DualWield":
                applyDualWieldPerk(player);
                break;
            case "ProjectileResistance":
                applyProjectileResistancePerk(player);
                break;
            case "ShieldExpert":
                applyShieldExpertPerk(player);
                break;
            case "Swift":
                applySwiftPerk(player);
                break;
            case "WeaponMastery":
                applyWeaponMasteryPerk(player);
                break;
            case "AdrenalineSpike":
                applyAdrenalineSpikePerk(player);
                break;
            case "Slayer":
                applySlayerPerk(player);
                break;
            default:
                break;
        }
    }

    public void removePerk(String perkName) {
        // Remove the perk effects
    }

    private ItemStack createAbilityItem(String abilityName) {
        Material material = Material.NETHER_STAR; // You can change this to another item type
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(abilityName);
            item.setItemMeta(meta);
        }
        return item;
    }
}