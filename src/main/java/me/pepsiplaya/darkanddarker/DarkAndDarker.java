package me.pepsiplaya.darkanddarker;

import me.pepsiplaya.darkanddarker.actions.AbilityUseListener;
import me.pepsiplaya.darkanddarker.actions.ActionSpeed;
import me.pepsiplaya.darkanddarker.actions.LifestealListener;
import me.pepsiplaya.darkanddarker.classes.ClassSelectionManager;
import me.pepsiplaya.darkanddarker.commands.DungeonCommandExecutor;
import me.pepsiplaya.darkanddarker.game.*;
import me.pepsiplaya.darkanddarker.mobs.MobDataManager;
import me.pepsiplaya.darkanddarker.players.PlayerData;
import me.pepsiplaya.darkanddarker.players.PlayerDataManager;
import me.pepsiplaya.darkanddarker.players.PlayerJoinQuitListener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;

public class DarkAndDarker extends JavaPlugin {

    private ClassSelectionManager classSelectionManager;
    private PlayerDataManager playerDataManager;
    private LobbyManager lobbyManager;
    private GameManager gameManager;
    private GameSession gameSession;
    private ActionSpeed actionSpeed;
    private PlayerData playerData;
    private MobDataManager mobDataManager;
    private int lobbyX;
    private int lobbyY;
    private int lobbyZ;
    private HashMap<UUID, PlayerData> playerDataMap;

    @Override
    public void onEnable() {
        // Set the lobby coordinates
        lobbyX = 0;
        lobbyY = 70;
        lobbyZ = 0;

        // Instantiate the PlayerDataManager
        playerDataManager = new PlayerDataManager(getDataFolder(), this, classSelectionManager);
        playerDataMap = new HashMap<>();
        playerData = new PlayerData(0, 0, 0, 0, null, null, null, null);
        getServer().getPluginManager().registerEvents(new PlayerJoinQuitListener(playerDataManager), this);

        // Instantiate the ClassSelectionManager and DungeonGenerator
        classSelectionManager = new ClassSelectionManager(this, gameManager, playerDataManager, playerData);

        // Instantiate the GameManager
        gameManager = new GameManager(playerDataManager, this, lobbyX, lobbyY, lobbyZ);
        mobDataManager = new MobDataManager(getDataFolder());
        // Instantiate the LobbyManager
        lobbyManager = new LobbyManager(this, gameManager, playerDataManager, lobbyManager, lobbyX, lobbyY, lobbyZ);

        // Instantiate the GameSessionManager
        int requiredPlayers = getConfig().getInt("required-players", 2);
        int countdownSeconds = getConfig().getInt("countdown-seconds", 10);
        gameSession = new GameSession(gameManager, this, requiredPlayers, countdownSeconds, playerDataManager);

        // Register the dnd command
        DungeonCommandExecutor dungeonCommandExecutor = new DungeonCommandExecutor(gameManager, this);
        getCommand("dnd").setExecutor(dungeonCommandExecutor);

        // Register the lobby and game events
        getServer().getPluginManager().registerEvents(lobbyManager, this);
        getServer().getPluginManager().registerEvents(gameSession, this);
        getServer().getPluginManager().registerEvents(classSelectionManager, this);
        getServer().getPluginManager().registerEvents(new AbilityUseListener(this), this);
        getServer().getPluginManager().registerEvents(new CustomItemDropListener(), this);
        actionSpeed = new ActionSpeed(playerDataManager);
        getServer().getPluginManager().registerEvents(actionSpeed, this);
        getServer().getPluginManager().registerEvents(new CustomGameRulesListener(), this);
        getServer().getPluginManager().registerEvents(new LifestealListener(this), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public ClassSelectionManager getClassSelectionManager() {
        return classSelectionManager;
    }
}