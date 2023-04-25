package me.pepsiplaya.darkanddarker.actions;

import me.pepsiplaya.darkanddarker.DarkAndDarker;
import me.pepsiplaya.darkanddarker.players.PlayerData;
import me.pepsiplaya.darkanddarker.players.PlayerDataManager;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.data.type.Door;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class ActionSpeed implements Listener {
    private PlayerDataManager playerDataManager;

    public ActionSpeed(PlayerDataManager playerDataManager) {
        this.playerDataManager = playerDataManager;
    }

    private void displayLoadingBar(Player player, long delay, String loadingText) {
        int totalChars = 20;
        long interval = delay / totalChars;
        StringBuilder progressBar = new StringBuilder();

        new BukkitRunnable() {
            int count = 0;

            @Override
            public void run() {
                if (count < totalChars) {
                    progressBar.append("|");
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(loadingText + progressBar.toString()));
                    count++;
                } else {
                    this.cancel();
                }
            }
        }.runTaskTimer(JavaPlugin.getPlugin(DarkAndDarker.class), 0, interval);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Block clickedBlock = event.getClickedBlock();
            Player player = event.getPlayer();
            PlayerData playerData = PlayerData.getPlayerData(player);
            int actionSpeed = playerDataManager.getActionSpeed(player);

            if (playerData.isPerformingAction()) {
                event.setCancelled(true);
                return;
            }

            if (clickedBlock != null) {
                double actionSpeedModifier = (actionSpeed - 100) / 100.0;
                switch (clickedBlock.getType()) {
                    case OAK_DOOR:
                        event.setCancelled(true);

                        Door door = (Door) clickedBlock.getBlockData();
                        boolean isOpen = door.isOpen();

                        long defaultDoorOpenTime = 60;
                        long defaultDoorCloseTime = 20;
                        long doorOpenDelay = (long) (defaultDoorOpenTime - (actionSpeedModifier * defaultDoorOpenTime));
                        long doorCloseDelay = (long) (defaultDoorCloseTime - (actionSpeedModifier * defaultDoorCloseTime));

                        playerData.setPerformingAction(true);

                        if (!isOpen) {
                            displayLoadingBar(player, doorCloseDelay, "Closing: ");

                            BukkitTask closeTask = new BukkitRunnable() {
                                @Override
                                public void run() {
                                    door.setOpen(false);
                                    clickedBlock.setBlockData(door);
                                    playerData.setPerformingAction(false);
                                }
                            }.runTaskLater(JavaPlugin.getPlugin(DarkAndDarker.class), doorCloseDelay);

                            playerData.setOngoingTask(closeTask);
                        } else {
                            displayLoadingBar(player, doorOpenDelay, "Opening: ");

                            BukkitTask openTask = new BukkitRunnable() {
                                @Override
                                public void run() {
                                    door.setOpen(true);
                                    clickedBlock.setBlockData(door);
                                    playerData.setPerformingAction(false);
                                }
                            }.runTaskLater(JavaPlugin.getPlugin(DarkAndDarker.class), doorOpenDelay);

                            playerData.setOngoingTask(openTask);
                        }
                        break;

                    case CHEST:
                        event.setCancelled(true);
                        Chest chest = (Chest) clickedBlock.getState();
                        Inventory chestInventory = chest.getInventory();

                        playerData.setPerformingAction(true);
                        long defaultChestOpenTime = 40;
                        long chestOpenDelay = (long) (defaultChestOpenTime - (actionSpeedModifier * defaultChestOpenTime));
                        displayLoadingBar(player, chestOpenDelay, "Opening chest: ");

                        BukkitTask chestTask = new BukkitRunnable() {
                            @Override
                            public void run() {
                                player.openInventory(chestInventory);
                                playerData.setPerformingAction(false);
                            }
                        }.runTaskLater(JavaPlugin.getPlugin(DarkAndDarker.class), chestOpenDelay);

                        playerData.setOngoingTask(chestTask);
                        break;

                    default:
                        break;
                }
            }
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        PlayerData playerData = PlayerData.getPlayerData(player);
        if (playerData.isPerformingAction()) {
            playerData.setPerformingAction(false);
            playerData.cancelOngoingTask();
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("")); // Reset action bar
        }
    }
}
