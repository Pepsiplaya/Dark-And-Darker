package me.pepsiplaya.darkanddarker.game;

import me.pepsiplaya.darkanddarker.players.PlayerDataManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class GameManager {
    private Map<UUID, GameSession> activeGameSessions;
    private PlayerDataManager playerDataManager;
    private JavaPlugin plugin;
    private int lobbyX;
    private int lobbyY;
    private int lobbyZ;

    public GameManager(PlayerDataManager playerDataManager, JavaPlugin plugin, int lobbyX, int lobbyY, int lobbyZ) {
        this.activeGameSessions = new HashMap<>();
        this.playerDataManager = playerDataManager;
        this.plugin = plugin;
        this.lobbyX = lobbyX;
        this.lobbyY = lobbyY;
        this.lobbyZ = lobbyZ;
    }

    public Location getLobbyLocation() {
        return new Location(Bukkit.getWorld("world"), lobbyX, lobbyY, lobbyZ);
    }

    public GameSession getGameSessionByPlayer(Player player) {
        UUID playerId = player.getUniqueId();
        for (GameSession gameSession : activeGameSessions.values()) {
            if (gameSession.getPlayers().contains(playerId)) {
                return gameSession;
            }
        }
        return null;
    }

    public void addPlayerToGame(Player player) {
        int requiredPlayers = plugin.getConfig().getInt("required-players", 2);
        GameSession gameSession = findOpenGameSession();
        if (gameSession == null) {
            gameSession = createNewGameSession();
        }
        gameSession.addPlayer(player);
        plugin.getServer().broadcastMessage(ChatColor.GREEN + player.getName() + " has joined the game lobby. (" + gameSession.getPlayers().size() + "/" + requiredPlayers + ")");
    }

    private GameSession findOpenGameSession() {
        for (GameSession session : activeGameSessions.values()) {
            if (session.getGameState() == GameState.WAITING_FOR_PLAYERS) {
                return session;
            }
        }
        return null;
    }

    public GameSession getGameSessionForPlayer(Player player) {
        for (GameSession gameSession : activeGameSessions.values()) {
            if (gameSession.getPlayers().contains(player.getUniqueId())) {
                return gameSession;
            }
        }
        return null;
    }

    private GameSession createNewGameSession() {
        int requiredPlayers = plugin.getConfig().getInt("required-players", 2);
        int countdownSeconds = plugin.getConfig().getInt("countdown-seconds", 10);
        GameSession gameSession = new GameSession(this, plugin, requiredPlayers, countdownSeconds, playerDataManager);
        UUID sessionId = UUID.randomUUID();
        activeGameSessions.put(sessionId, gameSession);
        return gameSession;
    }
}
