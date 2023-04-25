package me.pepsiplaya.darkanddarker.players;

import me.pepsiplaya.darkanddarker.DarkAndDarker;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.UUID;

public class PlayerData {
    private BukkitTask ongoingTask;
    private int classLevel;
    private int experience;
    private int movespeed;
    private int actionspeed;
    private boolean isPerformingAction;
    private String[] perks;
    private String[] skills;
    private ItemStack[] inventoryContents;
    private ItemStack[] enderChestContents;
    private PlayerDataManager playerDataManager;
    private boolean diedInGame;
    private static HashMap<UUID, PlayerData> playerDataMap = new HashMap<>();

    public PlayerData(int classLevel, int experience, int movespeed, int actionspeed, String[] perks, String[] skills, ItemStack[] inventoryContents, ItemStack[] enderChestContents) {
        this.classLevel = classLevel;
        this.experience = experience;
        this.movespeed = movespeed;
        this.actionspeed = actionspeed;
        this.isPerformingAction = false;
        this.perks = perks;
        this.skills = skills;
        this.inventoryContents = inventoryContents;
        this.enderChestContents = enderChestContents;
    }

    public PlayerData(Player player, String className) {
        switch (className) {
            case "Fighter":
                this.classLevel = 1;
                this.experience = 0;
                this.movespeed = 300;
                this.actionspeed = 100;
                break;
            case "Barbarian":
                this.classLevel = 1;
                this.experience = 0;
                this.movespeed = 292;
                this.actionspeed = 88;
                break;
            case "Rogue":
                this.classLevel = 1;
                this.experience = 0;
                this.movespeed = 320;
                this.actionspeed = 125;
                break;
            case "Ranger":
                this.classLevel = 1;
                this.experience = 0;
                this.movespeed = 305;
                this.actionspeed = 105;
                break;
            case "Cleric":
                this.classLevel = 1;
                this.experience = 0;
                this.movespeed = 294;
                this.actionspeed = 88;
                break;
            default:
                // Set a default class name or log an error message
                className = "Fighter"; // This is just an example, choose an appropriate default value
                // or log an error message and return
                this.classLevel = 1;
                this.experience = 0;
                this.movespeed = 300;
                this.actionspeed = 100;
                break;
        }
        this.isPerformingAction = false;
        this.perks = new String[0];
        this.skills = new String[0];
        this.inventoryContents = new ItemStack[0];
        this.enderChestContents = new ItemStack[0];
    }

    public static PlayerData getPlayerData(Player player) {
        UUID uuid = player.getUniqueId();
        if (!playerDataMap.containsKey(uuid)) {
            DarkAndDarker mainPlugin = JavaPlugin.getPlugin(DarkAndDarker.class);
            PlayerDataManager playerDataManager = new PlayerDataManager(mainPlugin.getDataFolder(), mainPlugin, mainPlugin.getClassSelectionManager());
            String className = playerDataManager.getPlayerClass(player);
            PlayerData playerData = new PlayerData(player, className);
            playerDataMap.put(uuid, playerData);
        }
        return playerDataMap.get(uuid);
    }

    public ItemStack[] getInventoryContents() {
        return inventoryContents;
    }

    public void setOngoingTask(BukkitTask task) {
        ongoingTask = task;
    }

    public void cancelOngoingTask() {
        if (ongoingTask != null) {
            ongoingTask.cancel();
            ongoingTask = null;
        }
    }

    public void setInventoryContents(ItemStack[] inventoryContents) {
        this.inventoryContents = inventoryContents;
    }

    public ItemStack[] getEnderChestContents() {
        return enderChestContents;
    }

     public boolean hasDiedInGame() {
        return diedInGame;
    }

    public void setDiedInGame(boolean diedInGame) {
        this.diedInGame = diedInGame;
    }

    public void setEnderChestContents(ItemStack[] enderChestContents) {
        this.enderChestContents = enderChestContents;
    }

    public int getClassLevel() {
        return classLevel;
    }

    public void setClassLevel(int classLevel) {
        this.classLevel = classLevel;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public void addExperience(int expToAdd) {
        this.experience += expToAdd;

        while (shouldLevelUp()) {
            levelUp();
        }
    }

    private boolean shouldLevelUp() {
        if (classLevel >= 20) {
            return false;
        }

        int nextLevelExpThreshold;
        if (classLevel < 5) {
            nextLevelExpThreshold = 40;
        } else if (classLevel < 10) {
            nextLevelExpThreshold = 80;
        } else if (classLevel < 15) {
            nextLevelExpThreshold = 120;
        } else {
            nextLevelExpThreshold = 160;
        }

        return experience >= nextLevelExpThreshold;
    }

    private void levelUp() {
        classLevel++;
        experience -= getNextLevelExpThreshold();

        // Apply any level-up effects, such as increasing stats or granting new abilities
    }

    private int getNextLevelExpThreshold() {
        if (classLevel < 5) {
            return 40;
        } else if (classLevel < 10) {
            return 80;
        } else if (classLevel < 15) {
            return 120;
        } else {
            return 160;
        }
    }

    public int getMoveSpeed() {
        return movespeed;
    }

    public void setMoveSpeed(int movespeed) {
        this.movespeed = movespeed;
    }

    public int getActionSpeed() {
        return actionspeed;
    }

    public int getMovespeed() {
        return movespeed;
    }

    public void setMovespeed(int movespeed) {
        this.movespeed = movespeed;
    }

    public int getActionspeed() {
        return actionspeed;
    }

    public void setActionspeed(int actionspeed) {
        this.actionspeed = actionspeed;
    }

    public void setActionSpeed(int actionspeed) {
        this.actionspeed = actionspeed;
    }

    public boolean isPerformingAction() {
        return isPerformingAction;
    }

    public void setPerformingAction(boolean isPerformingAction) {
        this.isPerformingAction = isPerformingAction;
    }

    public String[] getPerks() {
        return perks;
    }

    public void setPerks(String[] perks) {
        this.perks = perks;
    }

    public String[] getSkills() {
        return skills;
    }

    public void setSkills(String[] skills) {
        this.skills = skills;
    }
}
