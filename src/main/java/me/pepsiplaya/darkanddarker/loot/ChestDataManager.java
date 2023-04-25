package me.pepsiplaya.darkanddarker.loot;

import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ChestDataManager {
    private Plugin plugin;
    private File chestDataFile;
    private FileConfiguration chestData;

    public ChestDataManager(Plugin plugin) {
        this.plugin = plugin;
        saveDefaultChestData();
    }

    private void saveDefaultChestData() {
        if (chestDataFile == null) {
            chestDataFile = new File(plugin.getDataFolder(), "chestData.yml");
        }

        if (!chestDataFile.exists()) {
            plugin.saveResource("chestData.yml", false);
        }
    }

    public FileConfiguration getChestData() {
        if (chestData == null) {
            chestData = YamlConfiguration.loadConfiguration(chestDataFile);
        }
        return chestData;
    }

    public void saveChestData() {
        if (chestData == null || chestDataFile == null) {
            return;
        }

        try {
            getChestData().save(chestDataFile);
        } catch (IOException e) {
            plugin.getLogger().warning("Could not save chest data: " + e.getMessage());
        }
    }
}