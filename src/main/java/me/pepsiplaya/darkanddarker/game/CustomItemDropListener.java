package me.pepsiplaya.darkanddarker.game;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CustomItemDropListener implements Listener {
    private static final int MIN_CUSTOM_MODEL_DATA = 1001;
    private static final int MAX_CUSTOM_MODEL_DATA = 1100;

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        ItemMeta itemMeta = event.getItemDrop().getItemStack().getItemMeta();

        if (itemMeta != null && itemMeta.hasCustomModelData()) {
            int customModelData = itemMeta.getCustomModelData();

            if (customModelData >= MIN_CUSTOM_MODEL_DATA && customModelData <= MAX_CUSTOM_MODEL_DATA) {
                event.setCancelled(true);
                event.getPlayer().sendMessage("You cannot drop this item.");
            }
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getClickedInventory() == null) {
            return;
        }
        ItemStack clickedItem = event.getCurrentItem();

        Inventory clickedInventory = event.getClickedInventory();
        if (clickedInventory.getHolder() instanceof Player) {
            ItemStack cursorItem = event.getCursor();

            // Check if the clicked item is one of the lobby menu items
            if (clickedItem != null && (clickedItem.getType() == Material.COMPASS || clickedItem.getType() == Material.CHEST || clickedItem.getType() == Material.PLAYER_HEAD)) {
                event.setCancelled(true);
                return;
            }

            // Check if the cursor item is one of the lobby menu items
            if (cursorItem != null && (cursorItem.getType() == Material.COMPASS || cursorItem.getType() == Material.CHEST || cursorItem.getType() == Material.PLAYER_HEAD)) {
                event.setCancelled(true);
                return;
            }
        }

        if (clickedItem != null) {
            ItemMeta itemMeta = clickedItem.getItemMeta();

            if (itemMeta != null && itemMeta.hasCustomModelData()) {
                int customModelData = itemMeta.getCustomModelData();

                if (customModelData >= MIN_CUSTOM_MODEL_DATA && customModelData <= MAX_CUSTOM_MODEL_DATA) {
                    if (event.getClickedInventory().getType() != InventoryType.PLAYER) {
                        event.setCancelled(true);
                        event.getWhoClicked().sendMessage("You cannot store this item.");
                    }
                }
            }
        }
    }

    @EventHandler
    public void onInventoryMoveItem(InventoryMoveItemEvent event) {
        ItemStack movedItem = event.getItem();

        if (movedItem != null) {
            ItemMeta itemMeta = movedItem.getItemMeta();

            if (itemMeta != null && itemMeta.hasCustomModelData()) {
                int customModelData = itemMeta.getCustomModelData();

                if (customModelData >= MIN_CUSTOM_MODEL_DATA && customModelData <= MAX_CUSTOM_MODEL_DATA) {
                    if (movedItem.getAmount() > 1) {
                        movedItem.setAmount(1);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        ItemStack placedItem = event.getItemInHand();
        ItemMeta itemMeta = placedItem.getItemMeta();

        if (itemMeta != null && itemMeta.hasCustomModelData()) {
            int customModelData = itemMeta.getCustomModelData();

            if (customModelData >= MIN_CUSTOM_MODEL_DATA && customModelData <= MAX_CUSTOM_MODEL_DATA) {
                event.setCancelled(true);
            }
        }
    }
}