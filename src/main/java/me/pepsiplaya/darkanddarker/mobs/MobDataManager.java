package me.pepsiplaya.darkanddarker.mobs;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MobDataManager {
    private final File dataFile;
    private FileConfiguration dataConfig;

    public MobDataManager(File dataFolder) {
        dataFile = new File(dataFolder, "mobData.yml");
        loadDataFile();
    }

    private void loadDataFile() {
        if (!dataFile.exists()) {
            try {
                dataFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        dataConfig = YamlConfiguration.loadConfiguration(dataFile);
    }

    public void saveMobSpawnLocation(String mobName, Location location) {
        List<String> mobSpawnLocations = dataConfig.getStringList(mobName);
        if (mobSpawnLocations == null) {
            mobSpawnLocations = new ArrayList<>();
        }
        mobSpawnLocations.add(serializeLocation(location));
        dataConfig.set(mobName, mobSpawnLocations);
        saveDataFile();
    }

    public List<Location> getMobSpawnLocations(String mobName) {
        List<String> serializedLocations = dataConfig.getStringList(mobName);
        List<Location> spawnLocations = new ArrayList<>();
        if (serializedLocations != null) {
            for (String serializedLocation : serializedLocations) {
                spawnLocations.add(deserializeLocation(serializedLocation));
            }
        }
        return spawnLocations;
    }

    private void saveDataFile() {
        try {
            dataConfig.save(dataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String serializeLocation(Location location) {
        return location.getWorld().getName() + "," + location.getX() + "," + location.getY() + "," + location.getZ();
    }

    private Location deserializeLocation(String serializedLocation) {
        String[] parts = serializedLocation.split(",");
        String worldName = parts[0];
        double x = Double.parseDouble(parts[1]);
        double y = Double.parseDouble(parts[2]);
        double z = Double.parseDouble(parts[3]);
        return new Location(Bukkit.getWorld(worldName), x, y, z);
    }
}