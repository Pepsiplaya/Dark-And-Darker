package me.pepsiplaya.darkanddarker.mobs;

import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

public class CustomMobFactory {

    private final JavaPlugin plugin;

    public CustomMobFactory(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void spawnMummy(Location location) {
        new Mummy(location, plugin);
    }
}