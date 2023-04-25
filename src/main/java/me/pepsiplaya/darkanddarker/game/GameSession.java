package me.pepsiplaya.darkanddarker.game;

import me.pepsiplaya.darkanddarker.loot.ChestDataManager;
import me.pepsiplaya.darkanddarker.loot.LootChest;
import me.pepsiplaya.darkanddarker.players.PlayerDataManager;
import me.pepsiplaya.darkanddarker.mobs.CustomMobFactory;
import me.pepsiplaya.darkanddarker.mobs.MobDataManager;
import me.pepsiplaya.darkanddarker.mobs.Mummy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class GameSession implements Listener {
    private UUID sessionId;
    private Set<UUID> players = new HashSet<>();
    private GameState gameState = GameState.WAITING_FOR_PLAYERS;
    private GameManager gameManager;
    private List<LootChest> lootChests;
    private ChestDataManager chestDataManager;
    private JavaPlugin plugin;
    private int requiredPlayers;
    private int countdownSeconds;
    private BukkitTask countdownTask;
    private PlayerDataManager playerDataManager;
    private final CustomMobFactory customMobFactory;
    private final MobDataManager mobDataManager;
    private List<SpawnBeacon> spawnBeacons = new ArrayList<>();

    public GameSession(GameManager gameManager, JavaPlugin plugin, int requiredPlayers, int countdownSeconds, PlayerDataManager playerDataManager) {
        this.sessionId = UUID.randomUUID();
        this.players = new HashSet<>();
        this.gameState = GameState.WAITING_FOR_PLAYERS;
        this.gameManager = gameManager;
        this.lootChests = new ArrayList<>();
        chestDataManager = new ChestDataManager(plugin);
        this.requiredPlayers = requiredPlayers;
        this.countdownSeconds = countdownSeconds;
        this.playerDataManager = playerDataManager;
        this.customMobFactory = new CustomMobFactory(plugin);
        this.mobDataManager = new MobDataManager(plugin.getDataFolder());
    }

    public UUID getSessionId() {
        return sessionId;
    }

    public void addPlayer(Player player) {
        players.add(player.getUniqueId());
        player.teleport(gameManager.getLobbyLocation());
        if (players.size() >= requiredPlayers) {
            Bukkit.broadcastMessage(ChatColor.YELLOW + "The required number of players has been reached. The game will start in " + countdownSeconds + " seconds.");
            startCountdown();
        }
    }

    private void startCountdown() {
        countdownTask = Bukkit.getScheduler().runTaskTimer(plugin, new Runnable() {
            private int timeLeft = countdownSeconds;

            @Override
            public void run() {
                if (timeLeft <= 0) {
                    countdownTask.cancel();
                    startGame();
                } else {
                    // Send a message to all players in the lobby, for example:
                    for (UUID playerId : players) {
                        Player player = Bukkit.getPlayer(playerId);
                        player.sendMessage("Game starting in " + timeLeft + " seconds...");
                    }
                }
                timeLeft--;
            }
        }, 0L, 20L);
    }

    public void removePlayer(Player player) {
        players.remove(player.getUniqueId());
    }

    public void handlePlayerDeath(Player player) {
        players.remove(player.getUniqueId());
        playerDataManager.getPlayerData(player).setDiedInGame(true);
        checkForGameOver();
    }

    public void startGame() {
        if (countdownTask != null) {
            countdownTask.cancel();
            countdownTask = null;
        }

        this.gameState = GameState.IN_PROGRESS;

        for (UUID playerId : players) {
            Player player = Bukkit.getPlayer(playerId);
            player.teleport(getRandomSpawnLocation());
        }
        spawnBeacons();
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Block block = event.getClickedBlock();

        if (block == null) {
            return;
        }

        if (block.getType() == Material.BEACON) {
            GameSession gameSession = gameManager.getGameSessionByPlayer(player);
            if (gameSession != null) {
                gameSession.removePlayer(player);
                player.teleport(gameManager.getLobbyLocation());
                // Don't forget to save the player's inventory and other data
            }
        }
    }

    public void spawnBeacons() {
        Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
            @Override
            public void run() {
                // Spawn the beacons at random locations
                int numBeacons = 3; // Change this to the desired number of beacons
                for (int i = 0; i < numBeacons; i++) {
                    Location beaconLocation = getRandomSpawnLocation(); // or any other location method you'd like to use
                    SpawnBeacon spawnBeacon = new SpawnBeacon(beaconLocation);
                    spawnBeacon.createBeacon();
                    spawnBeacons.add(spawnBeacon);
                }
            }
        }, 20L * 60 * 5); // This will spawn beacons after 5 minutes (20 ticks * 60 seconds * 5 minutes)
    }

    private Location getRandomSpawnLocation() {
        // Replace the numbers below with the actual range of coordinates for your dungeon
        int x = ThreadLocalRandom.current().nextInt(-20, 20 + 1);
        int y = ThreadLocalRandom.current().nextInt(70, 70 + 1);
        int z = ThreadLocalRandom.current().nextInt(-20, 20 + 1);

        Location spawnLocation = new Location(Bukkit.getWorld("world"), x, y, z);
        return spawnLocation;
    }

    public void checkForGameOver() {
        // Check if the game is over, for example, by checking if only one player is left
        if (players.size() <= 1) {
            endGame();
        }
    }

    public void endGame() {
        // End the game, for example, by declaring the winner and resetting the game session
        this.gameState = GameState.COMPLETED;
        resetGame();
    }

    public void resetGame() {
        // Reset the game state
        gameState = GameState.WAITING_FOR_PLAYERS;

        // Clear players' inventories and reset their scores
        for (UUID playerId : players) {
            Player player = Bukkit.getPlayer(playerId);
            /*if (player != null) {
                player.getInventory().clear();
                // Reset scores or any other player-specific attributes
                // e.g., player.setScore(0);
            }*/
        }

        // Reset the game world, if needed
        // e.g., regenerate the dungeon, restore any destructible environment, etc.

        // Refill loot chests
        refillLootChests();

        spawnCustomMobs();

        // Remove all beacons
        for (SpawnBeacon spawnBeacon : spawnBeacons) {
            spawnBeacon.removeBeacon();
        }
        spawnBeacons.clear(); // Clear the list of beacons

        // Reset the player set
        players.clear();
    }

    private void spawnCustomMobs() {
        // For each mob type you want to spawn, e.g., "mummy"
        String mobName = "mummy";
        List<Location> spawnLocations = mobDataManager.getMobSpawnLocations(mobName);

        for (Location spawnLocation : spawnLocations) {
            spawnMummy(spawnLocation);
        }
    }

    private void spawnMummy(Location spawnLocation) {
        Mummy mummy = new Mummy(spawnLocation, plugin);
    }

    public Set<UUID> getPlayers() {
        return Collections.unmodifiableSet(players);
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public void addLootChest(LootChest lootChest) {
        lootChests.add(lootChest);
    }

    private void refillLootChests() {
        for (LootChest lootChest : lootChests) {
            lootChest.refillChest();
        }
    }

    public void saveLootChest(LootChest lootChest) {
        FileConfiguration chestData = chestDataManager.getChestData();
        String chestKey = "chests." + lootChest.getLocation().toString();
        chestData.set(chestKey + ".location", lootChest.getLocation());
        List<ItemStack> lootTable = lootChest.getLootTable();
        chestData.set(chestKey + ".loot", lootTable);
        chestDataManager.saveChestData();
    }

    public List<LootChest> loadLootChests() {
        FileConfiguration chestData = chestDataManager.getChestData();
        ConfigurationSection chestsSection = chestData.getConfigurationSection("chests");
        List<LootChest> lootChests = new ArrayList<>();

        if (chestsSection == null) {
            return lootChests;
        }

        for (String chestKey : chestsSection.getKeys(false)) {
            Location location = (Location) chestsSection.get(chestKey + ".location");
            List<ItemStack> lootTable = (List<ItemStack>) chestsSection.getList(chestKey + ".loot");
            LootChest lootChest = new LootChest(location);
            lootChest.setLootTable(lootTable);
            lootChests.add(lootChest);
        }

        return lootChests;
    }
    // Other game session methods like starting the game, handling player deaths, etc.
}

enum GameState {
    WAITING_FOR_PLAYERS,
    IN_PROGRESS,
    COMPLETED
}
