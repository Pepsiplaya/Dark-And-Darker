package me.pepsiplaya.darkanddarker.classes;

import me.pepsiplaya.darkanddarker.classes.Fighter;
import me.pepsiplaya.darkanddarker.game.GameManager;
import me.pepsiplaya.darkanddarker.game.GameSession;
import me.pepsiplaya.darkanddarker.players.PlayerData;
import me.pepsiplaya.darkanddarker.players.PlayerDataManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.Plugin;

import java.util.*;

public class ClassSelectionManager implements Listener {
    private Plugin plugin;
    private GameManager gameManager;
    private static PlayerDataManager playerDataManager;
    private static PlayerData playerData;
    private HashMap<UUID, Fighter> fighters = new HashMap<>();

    public ClassSelectionManager(Plugin plugin, GameManager gameManager, PlayerDataManager playerDataManager, PlayerData playerData) {
        this.plugin = plugin;
        this.gameManager = gameManager;
        this.playerDataManager = playerDataManager;
        this.playerData = new PlayerData(0, 0, 0, 0, null, null, null, null);
    }

    public void openClassSelectionGUI(Player player) {
        Inventory classSelectionGUI = Bukkit.createInventory(player, 9, "Select Your Class");

        // Add class icons to the GUI
        classSelectionGUI.addItem(createClassIcon(Material.STONE_SWORD, "Fighter", Arrays.asList(ChatColor.GRAY + "Level: " + playerDataManager.getClassLevel(player, "Fighter"))));
        classSelectionGUI.addItem(createClassIcon(Material.IRON_AXE, "Barbarian", Arrays.asList(ChatColor.GRAY + "Level: " + playerDataManager.getClassLevel(player, "Barbarian"))));
        classSelectionGUI.addItem(createClassIcon(Material.LEATHER_CHESTPLATE, "Rogue", Arrays.asList(ChatColor.GRAY + "Level: " + playerDataManager.getClassLevel(player, "Rogue"))));
        classSelectionGUI.addItem(createClassIcon(Material.BOW, "Ranger", Arrays.asList(ChatColor.GRAY + "Level: " + playerDataManager.getClassLevel(player, "Ranger"))));
        classSelectionGUI.addItem(createClassIcon(Material.POTION, "Cleric", Arrays.asList(ChatColor.GRAY + "Level: " + playerDataManager.getClassLevel(player, "Cleric"))));

        player.openInventory(classSelectionGUI);
    }

    private static Inventory createChestGUI(String title, int rows) {
        return Bukkit.createInventory(null, rows * 9, title);
    }

    public static void openAbilitiesPerksGUI(Player player, String className) {
        int rows = 3; // The number of rows you want for the Chest GUI (6 for 54 slots)
        Inventory abilitiesPerksGUI = createChestGUI("Select Abilities & Perks", rows);

        switch (className) {
            case "Fighter":
                addFighterAbilitiesPerks(abilitiesPerksGUI);
                break;
            //case "Barbarian":
            //    addBarbarianAbilitiesPerks(abilitiesPerksGUI);
            //    break;
        }

        // Load selected abilities and perks from the player data file
        List<String> selectedAbilities = playerDataManager.loadSelectedAbilities(player, className);
        List<String> selectedPerks = playerDataManager.loadSelectedPerks(player, className);

        // Update the enchantment effect on the items in the inventory
        for (int i = 0; i < abilitiesPerksGUI.getSize(); i++) {
            ItemStack item = abilitiesPerksGUI.getItem(i);
            if (item != null && item.hasItemMeta()) {
                String abilityOrPerkName = item.getItemMeta().getDisplayName();
                if (selectedAbilities.contains(abilityOrPerkName) || selectedPerks.contains(abilityOrPerkName)) {
                    updateItemEnchantment(abilitiesPerksGUI, abilityOrPerkName, true);
                }
            }
        }

        player.openInventory(abilitiesPerksGUI);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        handlePlayerDisconnect(event.getPlayer());
    }

    @EventHandler
    public void onPlayerKick(PlayerKickEvent event) {
        handlePlayerDisconnect(event.getPlayer());
    }

    private void handlePlayerDisconnect(Player player) {
        GameSession gameSession = gameManager.getGameSessionByPlayer(player);
        if (gameSession != null) {
            gameSession.removePlayer(player);

            // Check if the game is over or should be updated after player disconnection
            gameSession.checkForGameOver();
        }
    }

    private static void addFighterAbilitiesPerks(Inventory abilitiesPerksGUI) {
        List<String> emptyLore = new ArrayList<>();
        abilitiesPerksGUI.addItem(createClassIcon(Material.SUGAR, "Adrenaline Rush", emptyLore));
        abilitiesPerksGUI.addItem(createClassIcon(Material.IRON_BARS, "Breakthrough", emptyLore));
        abilitiesPerksGUI.addItem(createClassIcon(Material.FEATHER, "Second Wind", emptyLore));
        abilitiesPerksGUI.addItem(createClassIcon(Material.RABBIT_FOOT, "Sprint", emptyLore));
        abilitiesPerksGUI.addItem(createClassIcon(Material.MAGMA_CREAM, "Taunt", emptyLore));
        abilitiesPerksGUI.addItem(createClassIcon(Material.DIAMOND_SWORD, "Victory Strike", emptyLore));
        abilitiesPerksGUI.addItem(createClassIcon(Material.SHIELD, "Perfect Block", emptyLore));
        abilitiesPerksGUI.addItem(createClassIcon(Material.ANVIL, "Shield Slam", emptyLore));
        abilitiesPerksGUI.addItem(createClassIcon(Material.TNT, "Barricade", emptyLore));
        abilitiesPerksGUI.addItem(createClassIcon(Material.TNT, "Combo Attack", emptyLore));
        abilitiesPerksGUI.addItem(createClassIcon(Material.TNT, "Counterattack", emptyLore));
        abilitiesPerksGUI.addItem(createClassIcon(Material.TNT, "Defense Expert", emptyLore));
        abilitiesPerksGUI.addItem(createClassIcon(Material.TNT, "Dual Wield", emptyLore));
        abilitiesPerksGUI.addItem(createClassIcon(Material.TNT, "Projectile Resistance", emptyLore));
        abilitiesPerksGUI.addItem(createClassIcon(Material.TNT, "Shield Expert", emptyLore));
        abilitiesPerksGUI.addItem(createClassIcon(Material.TNT, "Swift", emptyLore));
        abilitiesPerksGUI.addItem(createClassIcon(Material.TNT, "Weapon Mastery", emptyLore));
        abilitiesPerksGUI.addItem(createClassIcon(Material.TNT, "Adrenaline Spike", emptyLore));
        abilitiesPerksGUI.addItem(createClassIcon(Material.TNT, "Slayer", emptyLore));
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getWhoClicked();
        String currentClassName = playerDataManager.getPlayerClass(player);

        if (event.getView().getTitle().equals("Select Your Class")) {
            event.setCancelled(true);

            ItemStack clickedItem = event.getCurrentItem();
            if (clickedItem != null && clickedItem.hasItemMeta()) {
                if (currentClassName != null) {
                    playerDataManager.saveCurrentClassData(player, currentClassName);
                }
                String className = clickedItem.getItemMeta().getDisplayName();
                playerDataManager.setPlayerClass(player, className);
                player.closeInventory();
                player.sendMessage(ChatColor.GREEN + "You have selected the " + className + " class.");

                if (isFirstTimeSelectingClass(player)) {
                    PlayerData newPlayerData = PlayerData.getPlayerData(player);
                    // Save initial stats
                    playerDataManager.saveInitialStats(player, newPlayerData);
                    playerDataManager.updatePlayerData(player, newPlayerData);
                    player.sendMessage(ChatColor.YELLOW + "First time selecting class!");
                    giveDefaultItems(player);
                    playerDataManager.setFirstTimeSelectingClass(player, className, false);
                    playerDataManager.savePlayerData(player);
                } else {
                    playerDataManager.loadPlayerData(player);
                }
            }
        } else if (event.getView().getTitle().equals("Select Abilities & Perks")) {
            event.setCancelled(true);

            ItemStack clickedItem = event.getCurrentItem();
            if (clickedItem != null && clickedItem.hasItemMeta()) {
                String abilityOrPerkName = clickedItem.getItemMeta().getDisplayName();
                String className = playerDataManager.getPlayerClass(player);
                ClickType clickType = event.getClick();

                switch (className) {
                    case "Fighter":
                        handleFighterAbilityPerkSelection(player, event.getClickedInventory(), abilityOrPerkName, clickType);
                        break;
                    // Add more cases for other classes
                }
            }
        }
    }

    private void handleFighterAbilityPerkSelection(Player player, Inventory abilitiesPerksGUI, String abilityOrPerkName, ClickType clickType) {
        List<String> selectedAbilities = playerDataManager.loadSelectedAbilities(player, "Fighter");
        List<String> selectedPerks = playerDataManager.loadSelectedPerks(player, "Fighter");

        switch (abilityOrPerkName) {
            case "Adrenaline Rush":
            case "Breakthrough":
            case "Second Wind":
            case "Sprint":
            case "Taunt":
            case "Victory Strike":
            case "Perfect Block":
            case "Shield Slam":
                if (selectedAbilities.contains(abilityOrPerkName)) {
                    deselectAbility(player, "Fighter", abilityOrPerkName, abilitiesPerksGUI);
                } else {
                    selectAbility(player, "Fighter", abilityOrPerkName, abilitiesPerksGUI);
                }
                break;
            case "Barricade":
            case "Counterattack":
            case "Combo Attack":
            case "Defense Expert":
            case "Dual Wield":
            case "Projectile Resistance":
            case "Shield Expert":
            case "Swift":
            case "Weapon Mastery":
            case "Adrenaline Spike":
            case "Slayer":
                if (selectedPerks.contains(abilityOrPerkName)) {
                    deselectPerk(player, "Fighter", abilityOrPerkName, abilitiesPerksGUI);
                } else {
                    selectPerk(player, "Fighter", abilityOrPerkName, abilitiesPerksGUI);
                    applyPerkToPlayer(player, abilityOrPerkName);
                }
                break;
            default:
                break;
        }
    }

    private static ItemStack createClassIcon(Material material, String className, List<String> lore) {
        ItemStack classIcon = new ItemStack(material);
        ItemMeta meta = classIcon.getItemMeta();
        meta.setDisplayName(className);
        meta.setLore(lore);
        classIcon.setItemMeta(meta);
        return classIcon;
    }

    private void selectAbility(Player player, String className, String abilityName, Inventory abilitiesPerksGUI) {
        List<String> selectedAbilities = playerDataManager.loadSelectedAbilities(player, className);

        if (selectedAbilities.size() < 2) {
            selectedAbilities.add(abilityName);
            playerDataManager.saveSelectedAbilities(player, className, selectedAbilities);
            updateItemEnchantment(abilitiesPerksGUI, abilityName, true);
            player.sendMessage(ChatColor.GREEN + "Selected ability: " + abilityName);
            ItemStack abilityItem = getAbilityItem(abilityName);
            if (abilityItem != null) {
                player.getInventory().addItem(abilityItem);
            }
        } else {
            player.sendMessage(ChatColor.RED + "You have already selected the maximum number of abilities. Remove one to add a new one.");
        }
    }

    private void deselectAbility(Player player, String className, String abilityName, Inventory abilitiesPerksGUI) {
        List<String> selectedAbilities = playerDataManager.loadSelectedAbilities(player, className);

        if (selectedAbilities.contains(abilityName)) {
            selectedAbilities.remove(abilityName);
            playerDataManager.saveSelectedAbilities(player, className, selectedAbilities);
            updateItemEnchantment(abilitiesPerksGUI, abilityName, false);
            player.sendMessage(ChatColor.RED + "Deselected ability: " + abilityName);
            ItemStack abilityItem = getAbilityItem(abilityName);
            if (abilityItem != null) {
                player.getInventory().removeItem(abilityItem);
            }
        }
    }
    private int getMaxPerks(int level) {
        if (level >= 15) {
            return 4;
        } else if (level >= 10) {
            return 3;
        } else if (level >= 5) {
            return 2;
        } else {
            return 1;
        }
    }
    private void selectPerk(Player player, String className, String perkName, Inventory abilitiesPerksGUI) {
        List<String> selectedPerks = playerDataManager.loadSelectedPerks(player, className);

        if (selectedPerks.contains(perkName)) {
            player.sendMessage(ChatColor.RED + "You have already selected this perk.");
            return;
        }

        int classLevel = playerDataManager.getClassLevel(player, className);
        if (selectedPerks.size() < getMaxPerks(classLevel)) {
            selectedPerks.add(perkName);
            playerDataManager.saveSelectedPerks(player, className, selectedPerks);
            updateItemEnchantment(abilitiesPerksGUI, perkName, true);
            player.sendMessage(ChatColor.GREEN + "Selected perk: " + perkName);
        } else {
            player.sendMessage(ChatColor.RED + "You have already selected the maximum number of perks for your level. Level up to unlock more perk slots.");
        }
    }

    private void deselectPerk(Player player, String className, String perkName, Inventory abilitiesPerksGUI) {
        List<String> selectedPerks = playerDataManager.loadSelectedPerks(player, className);

        if (selectedPerks.contains(perkName)) {
            selectedPerks.remove(perkName);
            playerDataManager.saveSelectedPerks(player, className, selectedPerks);
            updateItemEnchantment(abilitiesPerksGUI, perkName, false);
            player.sendMessage(ChatColor.RED + "Deselected perk: " + perkName);
        }
    }

    private void applyPerkToPlayer(Player player, String perkName) {
        String className = playerDataManager.getPlayerClass(player);
        if (className.equals("Fighter")) {
            Fighter fighter = fighters.get(player.getUniqueId());
            if (fighter == null) {
                fighter = new Fighter(player, playerDataManager.getClassLevel(player, "Fighter"), plugin);
                fighters.put(player.getUniqueId(), fighter);
            }
            fighter.applyPerkByName(perkName);
        }
        // Add more cases for other classes
    }

    private static ItemStack glowingItem(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        meta.addEnchant(Enchantment.LUCK, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(meta);
        return item;
    }

    private static void updateItemEnchantment(Inventory inventoryToUpdate, String name, boolean enchant) {
        for (int i = 0; i < inventoryToUpdate.getSize(); i++) {
            ItemStack item = inventoryToUpdate.getItem(i);
            if (item != null && item.hasItemMeta() && item.getItemMeta().getDisplayName().equals(name)) {
                if (enchant) {
                    inventoryToUpdate.setItem(i, glowingItem(item));
                } else {
                    ItemMeta meta = item.getItemMeta();
                    meta.removeEnchant(Enchantment.LUCK);
                    meta.removeItemFlags(ItemFlag.HIDE_ENCHANTS);
                    item.setItemMeta(meta);
                }
                break;
            }
        }
    }

    private static ItemStack getAbilityItem(String abilityName) {
        Material material;
        int customModelData;
        String name;
        List<String> lore;

        switch (abilityName) {
            case "Adrenaline Rush":
                material = Material.SUGAR;
                customModelData = 1001;
                name = "Adrenaline Rush";
                lore = Arrays.asList(
                        "Increases your action speed by 25% for 8 seconds.",
                        "After the duration ends, your action speed is reduced by -8%",
                        "and your movement speed is reduced by -4% for 4 seconds.",
                        "Cooldown: 28s"
                );
                break;
            case "Breakthrough":
                material = Material.IRON_BARS;
                customModelData = 1002;
                name = "Breakthrough";
                lore = Arrays.asList(
                        "Removes debuffs that slow all movement speeds.",
                        "Cooldown: 28s"
                );
                break;
            case "Second Wind":
                material = Material.FEATHER;
                customModelData = 1003;
                name = "Second Wind";
                lore = Arrays.asList(
                        "Recovers 50% of HP over 12 seconds.",
                        "Additionally, it eliminates the side effects of the Adrenaline Rush skill.",
                        "(Single-use, rechargeable with campfires at the rate of a Tier 6 Spell.)"
                );
                break;
            case "Sprint":
                material = Material.RABBIT_FOOT;
                customModelData = 1004;
                name = "Sprint";
                lore = Arrays.asList(
                        "Movement speed is increased by 50 for 6 seconds.",
                        "Cooldown: 28s"
                );
                break;
            case "Taunt":
                material = Material.MAGMA_CREAM;
                customModelData = 1005;
                name = "Taunt";
                lore = Arrays.asList(
                        "Increases the aggro value to all monsters within a 7.5m area by 50%",
                        "and increases your defense rating bonus by 15% for 8 seconds.",
                        "Cooldown: 18s"
                );
                break;
            case "Victory Strike":
                material = Material.DIAMOND_SWORD;
                customModelData = 1006;
                name = "Victory Strike";
                lore = Arrays.asList(
                        "The next attack deals an additional 20% weapon damage.",
                        "If the target dies from this attack you recover 5% of your maximum HP.",
                        "Cooldown: 24s"
                );
                break;
            case "Perfect Block":
                material = Material.SHIELD;
                customModelData = 1007;
                name = "Perfect Block";
                lore = Arrays.asList(
                        "The next defense completely blocks any attack.",
                        "(also removes the stagger animation of blocking)",
                        "Cooldown: 12s"
                );
                break;
            case "Shield Slam":
                material = Material.ANVIL;
                customModelData = 1008;
                name = "Shield Slam";
                lore = Arrays.asList(
                        "Using the skill inflicts 25 physical damage on the player",
                        "and reduces the movement speed of the hit target by 10% for 2 seconds.",
                        "Cooldown: 16s"
                );
                break;
            default:
                return null;
        }

        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setCustomModelData(customModelData);
        meta.setDisplayName(name);
        meta.setLore(lore);
        item.setItemMeta(meta);

        return item;
    }

    private boolean isFirstTimeSelectingClass(Player player) {
        String currentClass = playerDataManager.getPlayerClass(player);
        return playerDataManager.isFirstTimeSelectingClass(player, currentClass);
    }

    public static void giveDefaultItems(Player player) {
        player.getInventory().clear();
        String currentClass = playerDataManager.getPlayerClass(player);
        ItemStack woodenSword = new ItemStack(Material.WOODEN_SWORD);
        ItemStack stoneSword = new ItemStack(Material.STONE_SWORD);
        ItemStack ironSword = new ItemStack(Material.IRON_SWORD);
        ItemStack stoneAxe = new ItemStack(Material.STONE_AXE);
        ItemStack bow = new ItemStack(Material.BOW);
        ItemStack arrows = new ItemStack(Material.ARROW);
        if (currentClass != null && currentClass.equals("Fighter")) {
            player.getInventory().addItem(stoneSword);
            ItemStack helmet = new ItemStack(Material.CHAINMAIL_HELMET);
            ItemStack chestplate = new ItemStack(Material.CHAINMAIL_CHESTPLATE);
            ItemStack leggings = new ItemStack(Material.CHAINMAIL_LEGGINGS);
            ItemStack boots = new ItemStack(Material.CHAINMAIL_BOOTS);

            player.getInventory().setHelmet(helmet);
            player.getInventory().setChestplate(chestplate);
            player.getInventory().setLeggings(leggings);
            player.getInventory().setBoots(boots);
        } else if (currentClass != null && currentClass.equals("Barbarian")) {
            player.getInventory().addItem(stoneAxe);
            ItemStack helmet = new ItemStack(Material.IRON_HELMET);
            ItemStack chestplate = new ItemStack(Material.IRON_CHESTPLATE);
            ItemStack leggings = new ItemStack(Material.IRON_LEGGINGS);
            ItemStack boots = new ItemStack(Material.IRON_BOOTS);

            player.getInventory().setHelmet(helmet);
            player.getInventory().setChestplate(chestplate);
            player.getInventory().setLeggings(leggings);
            player.getInventory().setBoots(boots);
        } else if (currentClass != null && currentClass.equals("Rogue")) {
            ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
            ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
            ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS);
            ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);

            player.getInventory().setHelmet(helmet);
            player.getInventory().setChestplate(chestplate);
            player.getInventory().setLeggings(leggings);
            player.getInventory().setBoots(boots);
            player.getInventory().addItem(ironSword);
        } else if (currentClass != null && currentClass.equals("Ranger")) {
            player.getInventory().addItem(woodenSword);
            player.getInventory().addItem(bow);
            player.getInventory().addItem(arrows);
            ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
            ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
            ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS);
            ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);

            player.getInventory().setHelmet(helmet);
            player.getInventory().setChestplate(chestplate);
            player.getInventory().setLeggings(leggings);
            player.getInventory().setBoots(boots);
        } else if (currentClass != null && currentClass.equals("Cleric")) {
            player.getInventory().addItem(stoneAxe);
            player.getInventory().addItem(stoneSword);
            ItemStack helmet = new ItemStack(Material.CHAINMAIL_HELMET);
            ItemStack chestplate = new ItemStack(Material.CHAINMAIL_CHESTPLATE);
            ItemStack leggings = new ItemStack(Material.CHAINMAIL_LEGGINGS);
            ItemStack boots = new ItemStack(Material.CHAINMAIL_BOOTS);

            player.getInventory().setHelmet(helmet);
            player.getInventory().setChestplate(chestplate);
            player.getInventory().setLeggings(leggings);
            player.getInventory().setBoots(boots);
        }
        playerDataManager.setFirstTimeSelectingClass(player, currentClass, false);

        // Give the lobby items to the player using the LobbyManager instance
        setupLobbyInventory(player);
    }

    private static void setupLobbyInventory(Player player) {
        Inventory inv = player.getInventory();

        // Compass
        ItemStack compass = new ItemStack(Material.COMPASS);
        ItemMeta compassMeta = compass.getItemMeta();
        compassMeta.setDisplayName("Join Game");
        compass.setItemMeta(compassMeta);
        inv.setItem(6, compass);

        // Chest
        ItemStack chest = new ItemStack(Material.CHEST);
        ItemMeta chestMeta = chest.getItemMeta();
        chestMeta.setDisplayName("Choose Class");
        chest.setItemMeta(chestMeta);
        inv.setItem(7, chest);

        // Player head
        ItemStack playerHead = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) playerHead.getItemMeta();
        meta.setOwningPlayer(player);
        meta.setDisplayName("Choose Abilities/Perks");
        playerHead.setItemMeta(meta);
        inv.setItem(8, playerHead);
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        PlayerData playerData = playerDataManager.getPlayerData(player);

        if (playerData.hasDiedInGame()) {
            giveDefaultItems(player);
            player.teleport(gameManager.getLobbyLocation());

            // Remove the player from the game lobby
            GameSession gameSession = gameManager.getGameSessionByPlayer(player);
            if (gameSession != null) {
                gameSession.removePlayer(player);
            }

            // Reset the flag
            playerData.setDiedInGame(false);
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        GameSession gameSession = gameManager.getGameSessionForPlayer(player);

        if (gameSession != null) {
            gameSession.handlePlayerDeath(player);
        }
    }
}