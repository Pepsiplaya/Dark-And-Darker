package me.pepsiplaya.darkanddarker.actions;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.concurrent.ThreadLocalRandom;

public class LifestealListener implements Listener {
    private JavaPlugin plugin;

    public LifestealListener(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getDamager();
        ItemStack weapon = player.getInventory().getItemInMainHand();

        if (weapon != null && weapon.hasItemMeta()) {
            ItemMeta meta = weapon.getItemMeta();
            PersistentDataContainer data = meta.getPersistentDataContainer();
            NamespacedKey lifestealKey = new NamespacedKey(plugin, Lifesteal.LIFESTEAL_KEY);

            if (data.has(lifestealKey, PersistentDataType.DOUBLE)) {
                double lifestealChance = data.get(lifestealKey, PersistentDataType.DOUBLE);

                if (ThreadLocalRandom.current().nextDouble() < lifestealChance) {
                    double damage = event.getDamage();
                    double lifestealAmount = damage * 0.1; // Adjust the lifesteal percentage as needed
                    player.setHealth(Math.min(player.getHealth() + lifestealAmount, player.getMaxHealth()));
                }
            }
        }
    }
}