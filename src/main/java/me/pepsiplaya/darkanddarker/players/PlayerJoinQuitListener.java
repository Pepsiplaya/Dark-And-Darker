package me.pepsiplaya.darkanddarker.players;

import me.pepsiplaya.darkanddarker.players.PlayerDataManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class PlayerJoinQuitListener implements Listener {
    private PlayerDataManager playerDataManager;

    public PlayerJoinQuitListener(PlayerDataManager playerDataManager) {
        this.playerDataManager = playerDataManager;
    }

    private void checkLobbyInventory(Player player) {
        Inventory inv = player.getInventory();
        for (int i = 0; i < inv.getSize(); i++) {
            ItemStack item = inv.getItem(i);
            if (item != null) {
                if ((item.getType() == Material.COMPASS || item.getType() == Material.CHEST || item.getType() == Material.PLAYER_HEAD) && (i != 6 && i != 7 && i != 8)) {
                    inv.clear(i);
                }
            }
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        checkLobbyInventory(player);
        playerDataManager.loadPlayerData(player);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        playerDataManager.savePlayerData(player);
        playerDataManager.unloadPlayerData(player);
    }
}
