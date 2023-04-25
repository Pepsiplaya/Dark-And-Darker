package me.pepsiplaya.darkanddarker.game;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;

public class SpawnBeacon {

    private Location location;

    public SpawnBeacon(Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }

    public void createBeacon() {
        Block block = location.getBlock();
        block.setType(Material.BEACON);
    }

    public void removeBeacon() {
        location.getBlock().setType(Material.AIR);
    }
}