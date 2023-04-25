package me.pepsiplaya.darkanddarker.mobs;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Husk;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.Particle;
import org.bukkit.plugin.java.JavaPlugin;

public class Mummy implements CustomMob {
    private LivingEntity target;
    private final Husk husk;
    private final JavaPlugin plugin;

    public Mummy(Location spawnLocation, JavaPlugin plugin) {
        this.target = findNearestPlayer(spawnLocation);
        this.husk = spawnLocation.getWorld().spawn(spawnLocation, Husk.class);
        this.plugin = plugin;
        // Set custom attributes and abilities
        husk.setCustomName("Mummy");
        husk.setCustomNameVisible(true);
        husk.setMaxHealth(50);
        husk.setHealth(50);
        husk.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 1, false, false));
        spawnPoisonCloud();
    }

    private LivingEntity findNearestPlayer(Location location) {
        double minDistance = Double.MAX_VALUE;
        LivingEntity nearestPlayer = null;

        for (Player player : location.getWorld().getPlayers()) {
            double distance = player.getLocation().distance(location);
            if (distance < minDistance) {
                minDistance = distance;
                nearestPlayer = player;
            }
        }

        return nearestPlayer;
    }

    // Add custom behavior and abilities here
    private void spawnPoisonCloud() {
        // Create a custom runnable that periodically spawns a poison cloud at the Mummy's location
        Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            if (target != null && target instanceof Player && ((Player) target).isOnline() && !husk.isDead()) {
                Location mummyLocation = husk.getLocation();
                mummyLocation.getWorld().spawnParticle(Particle.CLOUD, mummyLocation, 10, 0.5, 0.5, 0.5, 0);
            }
        }, 0L, 20L); // Repeat every 20 ticks (1 second)
    }
}