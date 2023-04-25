package me.pepsiplaya.darkanddarker.loot;

import org.bukkit.Location;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class LootChest {
    private Location location;
    private List<ItemStack> lootTable;

    public LootChest(Location location) {
        this.location = location;
        this.lootTable = new ArrayList<>();
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<ItemStack> getLootTable() {
        return lootTable;
    }

    public void setLootTable(List<ItemStack> lootTable) {
        this.lootTable = lootTable;
    }

    public void refillChest() {
        Inventory chestInventory = ((org.bukkit.block.Chest) location.getBlock().getState()).getInventory();
        chestInventory.clear();

        int numberOfItems = ThreadLocalRandom.current().nextInt(1, 4);
        for (int i = 0; i < numberOfItems; i++) {
            ItemStack randomLoot = lootTable.get(ThreadLocalRandom.current().nextInt(lootTable.size()));
            chestInventory.addItem(randomLoot);
        }
    }
}