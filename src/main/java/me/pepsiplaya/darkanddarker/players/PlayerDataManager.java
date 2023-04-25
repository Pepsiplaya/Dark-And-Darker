package me.pepsiplaya.darkanddarker.players;

import me.pepsiplaya.darkanddarker.classes.ClassSelectionManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.*;

public class PlayerDataManager {
    private final File playerDataFolder;
    private JavaPlugin plugin;
    private HashMap<UUID, PlayerData> playerDataMap;
    private ClassSelectionManager classSelectionManager;


    public PlayerDataManager(File dataFolder, JavaPlugin plugin, ClassSelectionManager classSelectionManager) {
        this.plugin = plugin;
        this.playerDataMap = new HashMap<>();
        this.playerDataFolder = new File(dataFolder, "playerdata");
        this.playerDataFolder.mkdirs();
        this.classSelectionManager = classSelectionManager;
    }

    private File getPlayerDataFile(Player player) {
        return new File(playerDataFolder, player.getUniqueId().toString() + ".yml");
    }

    private Map<String, Object> loadPlayerDataFromFile(Player player) {
        File playerDataFile = getPlayerDataFile(player);
        if (!playerDataFile.exists()) {
            return new HashMap<>();
        }

        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        Yaml yaml = new Yaml(options);

        try (FileInputStream inputStream = new FileInputStream(playerDataFile)) {
            return yaml.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
            return new HashMap<>();
        }
    }

    public void updatePlayerData(Player player, PlayerData newPlayerData) {
        UUID playerUUID = player.getUniqueId();
        playerDataMap.put(playerUUID, newPlayerData);
    }

    private void savePlayerDataToFile(Player player, Map<String, Object> playerData) {
        File playerDataFile = getPlayerDataFile(player);
        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        Yaml yaml = new Yaml(options);

        try (FileWriter writer = new FileWriter(playerDataFile)) {
            yaml.dump(playerData, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadPlayerData(Player player) {
        UUID uuid = player.getUniqueId();
        File playerDataFile = new File(plugin.getDataFolder() + "/playerdata", uuid.toString() + ".yml");
        if (!playerDataFile.exists()) {
            createNewPlayerDataFile(player); // Method to create a new player data file with default values
            return;
        }
        FileConfiguration playerDataConfig = YamlConfiguration.loadConfiguration(playerDataFile);

        // Load class level, experience, perks, and abilities from the config
        String classPath = playerDataConfig.getString("currentClass");
        int classLevel = playerDataConfig.getInt(classPath + ".classLevel");
        int experience = playerDataConfig.getInt(classPath + ".experience");
        int movespeed = playerDataConfig.getInt(classPath + ".movespeed");
        int actionspeed = playerDataConfig.getInt(classPath + ".actionspeed");
        List<String> selectedPerks = loadSelectedPerks(player, classPath);
        List<String> selectedAbilities = loadSelectedAbilities(player, classPath);
        List<?> inventoryContentsList = playerDataConfig.getList(classPath + ".inventory_contents");
        List<?> enderChestContentsList = playerDataConfig.getList(classPath + ".ender_chest_contents");
        ItemStack[] inventoryContents = inventoryContentsList == null ? null : inventoryContentsList.toArray(new ItemStack[0]);
        ItemStack[] enderChestContents = enderChestContentsList == null ? null : enderChestContentsList.toArray(new ItemStack[0]);

        // Create a new PlayerData instance with the loaded data
        PlayerData playerData = new PlayerData(classLevel, experience, movespeed, actionspeed, selectedPerks.toArray(new String[0]), selectedAbilities.toArray(new String[0]), inventoryContents, enderChestContents);

        // Add the player data to the map
        playerDataMap.put(uuid, playerData);

        // Apply the loaded data (class level, experience, perks, and abilities) to the player
        applyPlayerData(player, playerData);
    }

    private void applyPlayerData(Player player, PlayerData playerData) {
        if (playerData.getInventoryContents() != null) {
            player.getInventory().setContents(playerData.getInventoryContents());
        }
        if (playerData.getEnderChestContents() != null) {
            player.getEnderChest().setContents(playerData.getEnderChestContents());
        }

        // Apply movespeed and actionspeed
        setPlayerDataSpeeds(player, playerData.getMovespeed(), playerData.getActionspeed());
    }

    public void savePlayerData(Player player) {
        UUID uuid = player.getUniqueId();
        PlayerData playerData = playerDataMap.get(uuid);
        if (playerData == null) {
            return;
        }

        // Retrieve class level, experience, perks, and skills from the player data
        String[] perks = playerData.getPerks();
        String[] skills = playerData.getSkills();

        // Create a File object for the UUID file and a FileConfiguration object to load and save the data
        File playerDataFile = new File(plugin.getDataFolder() + "/playerdata", uuid.toString() + ".yml");
        FileConfiguration playerDataConfig = YamlConfiguration.loadConfiguration(playerDataFile);

        // Save the player's data to the playerDataConfig
        String classPath = getPlayerClass(player);
        playerDataConfig.set(classPath + ".classLevel", playerData.getClassLevel());
        playerDataConfig.set(classPath + ".experience", playerData.getExperience());
        playerDataConfig.set(classPath + ".movespeed", getPlayerDataMovespeed(player));
        playerDataConfig.set(classPath + ".actionspeed", getPlayerDataActionspeed(player));
        playerDataConfig.set(classPath + ".perks", Arrays.asList(perks));
        playerDataConfig.set(classPath + ".abilities", Arrays.asList(skills));
        playerDataConfig.set(classPath + ".inventory_contents", Arrays.asList(player.getInventory().getContents()));
        playerDataConfig.set(classPath + ".ender_chest_contents", Arrays.asList(player.getEnderChest().getContents()));
        saveSelectedPerks(player, classPath, Arrays.asList(perks));
        saveSelectedAbilities(player, classPath, Arrays.asList(skills));
        // Save the player data file
        try {
            playerDataConfig.save(playerDataFile);
        } catch (IOException e) {
            plugin.getLogger().severe("Could not save player data for " + player.getName() + ": " + e.getMessage());
        }
    }

    public void unloadPlayerData(Player player) {
        playerDataMap.remove(player.getUniqueId());
    }

    private void createNewPlayerDataFile(Player player) {
        File playerDataFile = new File(playerDataFolder, player.getUniqueId().toString() + ".yml");
        FileConfiguration playerData = YamlConfiguration.loadConfiguration(playerDataFile);

        UUID playerUUID = player.getUniqueId();
        playerData.set("UUID", playerUUID.toString());

        try {
            playerData.save(playerDataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getPlayerClass(Player player) {
        File playerDataFile = new File(playerDataFolder, player.getUniqueId().toString() + ".yml");

        FileConfiguration playerData = YamlConfiguration.loadConfiguration(playerDataFile);
        String playerClass = playerData.getString("currentClass");

        if (playerClass == null) {
            // Set a default class name
            playerClass = "Fighter"; // This is just an example, choose an appropriate default value

            // Save the default class to the playerData configuration
            playerData.set("currentClass", playerClass);
            try {
                playerData.save(playerDataFile);
            } catch (IOException e) {
                plugin.getLogger().severe("Could not save player data for " + player.getName() + ": " + e.getMessage());
            }
        }

        return playerClass;
    }

    public void saveSelectedAbilities(Player player, String className, List<String> abilities) {
        File playerDataFile = new File(playerDataFolder, player.getUniqueId().toString() + ".yml");
        FileConfiguration playerData = YamlConfiguration.loadConfiguration(playerDataFile);

        playerData.set(className + ".abilities", abilities);

        try {
            playerData.save(playerDataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> loadSelectedAbilities(Player player, String className) {
        File playerDataFile = new File(playerDataFolder, player.getUniqueId().toString() + ".yml");
        FileConfiguration playerData = YamlConfiguration.loadConfiguration(playerDataFile);

        return playerData.getStringList(className + ".abilities");
    }

    public void saveSelectedPerks(Player player, String className, List<String> perks) {
        File playerDataFile = new File(playerDataFolder, player.getUniqueId().toString() + ".yml");
        FileConfiguration playerData = YamlConfiguration.loadConfiguration(playerDataFile);

        playerData.set(className + ".perks", perks);

        try {
            playerData.save(playerDataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> loadSelectedPerks(Player player, String className) {
        File playerDataFile = new File(playerDataFolder, player.getUniqueId().toString() + ".yml");
        FileConfiguration playerData = YamlConfiguration.loadConfiguration(playerDataFile);

        return playerData.getStringList(className + ".perks");
    }

    public int getClassLevel(Player player, String className) {
        Map<String, Object> classData = getPlayerClassData(player, className);
        if (classData != null && classData.containsKey("level")) {
            return (int) classData.get("level");
        }
        return 1; // Return 1 as the default level if the level is not found
    }

    private Map<String, Object> getPlayerClassData(Player player, String className) {
        Map<String, Object> playerData = loadPlayerDataFromFile(player);
        if (playerData.containsKey("classes") && playerData.get("classes") instanceof Map) {
            Map<String, Object> classes = (Map<String, Object>) playerData.get("classes");
            if (classes.containsKey(className) && classes.get(className) instanceof Map) {
                return (Map<String, Object>) classes.get(className);
            }
        }
        return null;
    }

    public void setPlayerClass(Player player, String newClassName) {
        String currentClassName = getPlayerClass(player);
        if (!newClassName.equalsIgnoreCase(currentClassName)) {
            saveCurrentClassData(player, currentClassName);
        }

        File playerDataFile = getPlayerDataFile(player);
        if (!playerDataFile.exists()) {
            createNewPlayerDataFile(player); // Create a new player data file if it doesn't exist
        }

        FileConfiguration playerData = YamlConfiguration.loadConfiguration(playerDataFile);
        playerData.set("currentClass", newClassName);

        try {
            playerData.save(playerDataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        loadNewClassData(player, newClassName);
    }

    public void saveCurrentClassData(Player player, String className) {
        File playerDataFile = new File(playerDataFolder, player.getUniqueId().toString() + ".yml");
        FileConfiguration playerData = YamlConfiguration.loadConfiguration(playerDataFile);

        playerData.set(className + ".inventory_contents", Arrays.asList(player.getInventory().getContents()));
        playerData.set(className + ".ender_chest_contents", Arrays.asList(player.getEnderChest().getContents()));

        try {
            playerData.save(playerDataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadNewClassData(Player player, String className) {
        File playerDataFile = new File(playerDataFolder, player.getUniqueId().toString() + ".yml");
        FileConfiguration playerData = YamlConfiguration.loadConfiguration(playerDataFile);

        List<?> inventoryContentsList = playerData.getList(className + ".inventory_contents");
        List<?> enderChestContentsList = playerData.getList(className + ".ender_chest_contents");
        ItemStack[] inventoryContents = inventoryContentsList == null ? null : inventoryContentsList.toArray(new ItemStack[0]);
        ItemStack[] enderChestContents = enderChestContentsList == null ? null : enderChestContentsList.toArray(new ItemStack[0]);

        if (inventoryContents != null) {
            player.getInventory().setContents(inventoryContents);
        } else {
            player.getInventory().clear();
        }

        if (enderChestContents != null) {
            player.getEnderChest().setContents(enderChestContents);
        } else {
            player.getEnderChest().clear();
        }
    }

    public boolean isFirstTimeSelectingClass(Player player, String currentClass) {
        Map<String, Object> playerData = loadPlayerDataFromFile(player);
        if (playerData.containsKey("classes") && playerData.get("classes") instanceof Map) {
            Map<String, Object> classes = (Map<String, Object>) playerData.get("classes");
            if (classes.containsKey(currentClass) && classes.get(currentClass) instanceof Map) {
                Map<String, Object> classData = (Map<String, Object>) classes.get(currentClass);
                if (classData.containsKey("first-time")) {
                    return (boolean) classData.get("first-time");
                }
            }
        }
        return true; // Assume it's the first time if the "first-time" flag is not found
    }

    public void setFirstTimeSelectingClass(Player player, String currentClass, boolean isFirstTime) {
        // Load the player data from file, set the "first-time" flag for the current class, and save the player data
        Map<String, Object> playerData = loadPlayerDataFromFile(player);
        Map<String, Object> classes = (Map<String, Object>) playerData.getOrDefault("classes", new HashMap<>());
        Map<String, Object> classData = (Map<String, Object>) classes.getOrDefault(currentClass, new HashMap<>());
        classData.put("first-time", isFirstTime);
        classes.put(currentClass, classData);
        playerData.put("classes", classes);
        savePlayerDataToFile(player, playerData);
    }

    private File getPlayerFile(Player player) {
        return new File(playerDataFolder, player.getUniqueId().toString() + ".yml");
    }

    public int getActionSpeed(Player player) {
        File playerFile = getPlayerFile(player);
        FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerFile);
        String classPath = playerConfig.getString("currentClass");

        return playerConfig.getInt(classPath + ".actionspeed");
    }

    public PlayerData getPlayerData(Player player) {
        return PlayerData.getPlayerData(player);
    }

    public void setPlayerDataSpeeds(Player player, int movespeed, int actionspeed) {
        PlayerData playerData = getPlayerData(player);
        playerData.setMovespeed(movespeed);
        playerData.setActionspeed(actionspeed);
        updatePlayerData(player, playerData);
    }

    public int getPlayerDataMovespeed(Player player) {
        PlayerData playerData = getPlayerData(player);
        return playerData.getMovespeed();
    }

    public int getPlayerDataActionspeed(Player player) {
        PlayerData playerData = getPlayerData(player);
        return playerData.getActionspeed();
    }

    public void saveInitialStats(Player player, PlayerData playerData) {
        File playerFile = getPlayerFile(player);
        FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerFile);

        String classPath = playerConfig.getString("currentClass");

        playerConfig.set(classPath + ".classLevel", playerData.getClassLevel());
        playerConfig.set(classPath + ".experience", playerData.getExperience());
        playerConfig.set(classPath + ".movespeed", playerData.getMoveSpeed());
        playerConfig.set(classPath + ".actionspeed", playerData.getActionSpeed());

        try {
            playerConfig.save(playerFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}