package me.pepsiplaya.darkanddarker.game;

import me.pepsiplaya.darkanddarker.classes.ClassSelectionManager;
import me.pepsiplaya.darkanddarker.players.PlayerData;
import me.pepsiplaya.darkanddarker.players.PlayerDataManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class LobbyManager implements Listener {
    private JavaPlugin plugin;
    private Location lobbyLocation;
    private ClassSelectionManager classSelectionManager;
    private GameManager gameManager;
    private PlayerDataManager playerDataManager;
    private PlayerData playerData;
    private int x;
    private int y;
    private int z;

    public LobbyManager(JavaPlugin plugin, GameManager gameManager, PlayerDataManager playerDataManager, LobbyManager lobbyManager, int x, int y, int z) { // Modify this constructor
        this.plugin = plugin;
        this.lobbyLocation = new Location(Bukkit.getWorld("world"), x, y, z);
        this.classSelectionManager = new ClassSelectionManager(plugin, gameManager, playerDataManager, playerData);
        this.gameManager = gameManager;
        this.playerDataManager = playerDataManager;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.teleport(lobbyLocation);
        setupLobbyInventory(player);
    }

    private void setupLobbyInventory(Player player) {
        Inventory inv = player.getInventory();

        // Compass
        ItemStack compass = new ItemStack(Material.COMPASS);
        ItemMeta compassMeta = compass.getItemMeta();
        compassMeta.setDisplayName("Join Game");
        compass.setItemMeta(compassMeta);
        inv.setItem(6, compass);

        // Chest
        ItemStack chest = new ItemStack(Material.CHEST);
        ItemMeta chestMeta = chest.getItemMeta();
        chestMeta.setDisplayName("Choose Class");
        chest.setItemMeta(chestMeta);
        inv.setItem(7, chest);

        // Player head
        ItemStack playerHead = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) playerHead.getItemMeta();
        meta.setOwningPlayer(player);
        meta.setDisplayName("Choose Abilities/Perks");
        playerHead.setItemMeta(meta);
        inv.setItem(8, playerHead);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getClickedInventory() == null) {
            return;
        }

        Inventory clickedInventory = event.getClickedInventory();
        if (clickedInventory.getHolder() instanceof Player) {
            ItemStack clickedItem = event.getCurrentItem();
            if (clickedItem == null || clickedItem.getType() == Material.AIR) {
                return;
            }

            if (clickedItem.getType() == Material.COMPASS || clickedItem.getType() == Material.CHEST || clickedItem.getType() == Material.PLAYER_HEAD) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        ItemStack droppedItem = event.getItemDrop().getItemStack();
        if (droppedItem.getType() == Material.COMPASS || droppedItem.getType() == Material.CHEST || droppedItem.getType() == Material.PLAYER_HEAD) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack itemInHand = player.getInventory().getItemInMainHand();

        if (itemInHand == null || itemInHand.getType() == Material.AIR) {
            return;
        }

        if (itemInHand.getType() == Material.COMPASS) {
            gameManager.addPlayerToGame(player);
            player.sendMessage("Compass clicked!");
        } else if (itemInHand.getType() == Material.CHEST) {
            classSelectionManager.openClassSelectionGUI(player);
        } else if (itemInHand.getType() == Material.PLAYER_HEAD) {
            String className = playerDataManager.getPlayerClass(player);
            if (className != null && !className.isEmpty()) {
                ClassSelectionManager.openAbilitiesPerksGUI(player, className);
            } else {
                player.sendMessage(ChatColor.RED + "You need to select a class first.");
            }
            // TODO: Implement more functionality related to player profiles, statistics, etc.
        }
    }
}