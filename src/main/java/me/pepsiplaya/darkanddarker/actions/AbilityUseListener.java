package me.pepsiplaya.darkanddarker.actions;

import me.pepsiplaya.darkanddarker.classes.Fighter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AbilityUseListener implements Listener {
    private final JavaPlugin plugin;
    private final Map<UUID, Map<Integer, Long>> abilityCooldowns;

    public AbilityUseListener(JavaPlugin plugin) {
        this.plugin = plugin;
        this.abilityCooldowns = new HashMap<>();
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();

        if (item == null || !item.hasItemMeta() || !item.getItemMeta().hasCustomModelData()) {
            return;
        }

        int customModelData = item.getItemMeta().getCustomModelData();
        Action action = event.getAction();
        boolean rightClick = action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK;

        if (rightClick) {
            UUID playerUUID = player.getUniqueId();
            long currentTimestamp = System.currentTimeMillis();
            Map<Integer, Long> playerCooldowns = abilityCooldowns.computeIfAbsent(playerUUID, k -> new HashMap<>());
            long cooldownEnd = playerCooldowns.getOrDefault(customModelData, currentTimestamp);

            if (currentTimestamp < cooldownEnd) {
                long remainingCooldown = (cooldownEnd - currentTimestamp) / 1000;
                player.sendMessage("You cannot use this ability for another " + remainingCooldown + " seconds.");
                return;
            }

            long cooldownDuration = 0;

            switch (customModelData) {
                case 1001: // Adrenaline Rush
                    Fighter.adrenalineRush(plugin, player);
                    cooldownDuration = 28000;
                    break;
                case 1002: // Breakthrough
                    Fighter.breakthrough(plugin, player);
                    cooldownDuration = 28000;
                    break;
                case 1003: // Second Wind
                    Fighter.secondWind(plugin, player);
                    // TODO: Implement the one-time-use restriction
                    break;
                case 1004: // Sprint
                    Fighter.sprint(plugin, player);
                    cooldownDuration = 28000;
                    break;
                case 1005: // Taunt
                    Fighter.taunt(plugin, player);
                    cooldownDuration = 18000;
                    break;
                case 1006: // Victory Strike
                    Fighter.victoryStrike(plugin, player);
                    cooldownDuration = 24000;
                    break;
                case 1007: // Perfect Block
                    Fighter.perfectBlock(plugin, player);
                    cooldownDuration = 12000;
                    break;
                case 1008: // Shield Slam
                    Fighter.shieldSlam(plugin, player);
                    cooldownDuration = 16000;
                    break;
            }

            playerCooldowns.put(customModelData, currentTimestamp + cooldownDuration);
        }
    }
}