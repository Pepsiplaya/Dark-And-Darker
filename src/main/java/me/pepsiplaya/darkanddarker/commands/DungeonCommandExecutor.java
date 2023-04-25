package me.pepsiplaya.darkanddarker.commands;

import me.pepsiplaya.darkanddarker.loot.CustomWeapon;
import me.pepsiplaya.darkanddarker.loot.LootChest;
import me.pepsiplaya.darkanddarker.game.GameManager;
import me.pepsiplaya.darkanddarker.game.GameSession;
import me.pepsiplaya.darkanddarker.loot.CustomArmor;
import me.pepsiplaya.darkanddarker.mobs.CustomMobFactory;
import me.pepsiplaya.darkanddarker.mobs.MobDataManager;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class DungeonCommandExecutor implements CommandExecutor, TabCompleter {
    private GameManager gameManager;
    private CustomArmor customArmor;
    private CustomWeapon customWeapon;
    private final CustomMobFactory customMobFactory;
    private final MobDataManager mobDataManager;

    public DungeonCommandExecutor(GameManager gameManager, JavaPlugin plugin) {
        this.gameManager = gameManager;
        this.customArmor = new CustomArmor(plugin);
        this.customWeapon = new CustomWeapon();
        this.customMobFactory = new CustomMobFactory(plugin);
        this.mobDataManager = new MobDataManager(plugin.getDataFolder());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command!");
            return true;
        }

        Player player = (Player) sender;

        if (command.getName().equalsIgnoreCase("dnd")) {
            if (args.length == 0) {
                // TODO: Show help message
            } else {
                String subCommand = args[0];
                GameSession gameSession = gameManager.getGameSessionByPlayer(player);

                switch (subCommand) {
                    case "customgear":
                        if (!player.isOp()) {
                            sender.sendMessage("You do not have permission to use this command.");
                            return true;
                        }

                        if (args.length != 2) {
                            sender.sendMessage("Usage: /dnd customarmor <leather|iron|diamond|netherite>");
                            return true;
                        }

                        String armorType = args[1].toLowerCase();

                        switch (armorType) {
                            case "leathergrey":
                                player.getInventory().addItem(customArmor.getRandomGreyLeatherHelmet());
                                player.getInventory().addItem(customArmor.getRandomGreyLeatherChestplate());
                                player.getInventory().addItem(customArmor.getRandomGreyLeatherLeggings());
                                player.getInventory().addItem(customArmor.getRandomGreyLeatherBoots());
                                break;

                            case "leathergreen":
                                player.getInventory().addItem(customArmor.getRandomGreenLeatherHelmet());
                                player.getInventory().addItem(customArmor.getRandomGreenLeatherChestplate());
                                player.getInventory().addItem(customArmor.getRandomGreenLeatherLeggings());
                                player.getInventory().addItem(customArmor.getRandomGreenLeatherBoots());
                                break;

                            case "leatherblue":
                                player.getInventory().addItem(customArmor.getRandomBlueLeatherHelmet());
                                player.getInventory().addItem(customArmor.getRandomBlueLeatherChestplate());
                                player.getInventory().addItem(customArmor.getRandomBlueLeatherLeggings());
                                player.getInventory().addItem(customArmor.getRandomBlueLeatherBoots());
                                break;

                            case "leatherrare":
                                player.getInventory().addItem(customArmor.getRandomRareLeatherHelmet());
                                player.getInventory().addItem(customArmor.getRandomRareLeatherChestplate());
                                player.getInventory().addItem(customArmor.getRandomRareLeatherLeggings());
                                player.getInventory().addItem(customArmor.getRandomRareLeatherBoots());
                                break;

                            case "leatherlegendary":
                                player.getInventory().addItem(customArmor.getRandomLegendaryLeatherHelmet());
                                player.getInventory().addItem(customArmor.getRandomLegendaryLeatherChestplate());
                                player.getInventory().addItem(customArmor.getRandomLegendaryLeatherLeggings());
                                player.getInventory().addItem(customArmor.getRandomLegendaryLeatherBoots());
                                break;

                            case "chainmailgrey":
                                player.getInventory().addItem(customArmor.getRandomGreyChainmailHelmet());
                                player.getInventory().addItem(customArmor.getRandomGreyChainmailChestplate());
                                player.getInventory().addItem(customArmor.getRandomGreyChainmailLeggings());
                                player.getInventory().addItem(customArmor.getRandomGreyChainmailBoots());
                                break;

                            case "chainmailgreen":
                                player.getInventory().addItem(customArmor.getRandomGreenChainmailHelmet());
                                player.getInventory().addItem(customArmor.getRandomGreenChainmailChestplate());
                                player.getInventory().addItem(customArmor.getRandomGreenChainmailLeggings());
                                player.getInventory().addItem(customArmor.getRandomGreenChainmailBoots());
                                break;

                            case "chainmailblue":
                                player.getInventory().addItem(customArmor.getRandomBlueChainmailHelmet());
                                player.getInventory().addItem(customArmor.getRandomBlueChainmailChestplate());
                                player.getInventory().addItem(customArmor.getRandomBlueChainmailLeggings());
                                player.getInventory().addItem(customArmor.getRandomBlueChainmailBoots());
                                break;

                            case "chainmailrare":
                                player.getInventory().addItem(customArmor.getRandomRareChainmailHelmet());
                                player.getInventory().addItem(customArmor.getRandomRareChainmailChestplate());
                                player.getInventory().addItem(customArmor.getRandomRareChainmailLeggings());
                                player.getInventory().addItem(customArmor.getRandomRareChainmailBoots());
                                break;

                            case "chainmaillegendary":
                                player.getInventory().addItem(customArmor.getRandomLegendaryChainmailHelmet());
                                player.getInventory().addItem(customArmor.getRandomLegendaryChainmailChestplate());
                                player.getInventory().addItem(customArmor.getRandomLegendaryChainmailLeggings());
                                player.getInventory().addItem(customArmor.getRandomLegendaryChainmailBoots());
                                break;

                            case "irongrey":
                                player.getInventory().addItem(customArmor.getRandomGreyIronHelmet());
                                player.getInventory().addItem(customArmor.getRandomGreyIronChestplate());
                                player.getInventory().addItem(customArmor.getRandomGreyIronLeggings());
                                player.getInventory().addItem(customArmor.getRandomGreyIronBoots());
                                break;

                            case "irongreen":
                                player.getInventory().addItem(customArmor.getRandomGreenIronHelmet());
                                player.getInventory().addItem(customArmor.getRandomGreenIronChestplate());
                                player.getInventory().addItem(customArmor.getRandomGreenIronLeggings());
                                player.getInventory().addItem(customArmor.getRandomGreenIronBoots());
                                break;

                            case "ironblue":
                                player.getInventory().addItem(customArmor.getRandomBlueIronHelmet());
                                player.getInventory().addItem(customArmor.getRandomBlueIronChestplate());
                                player.getInventory().addItem(customArmor.getRandomBlueIronLeggings());
                                player.getInventory().addItem(customArmor.getRandomBlueIronBoots());
                                break;

                            case "ironrare":
                                player.getInventory().addItem(customArmor.getRandomRareIronHelmet());
                                player.getInventory().addItem(customArmor.getRandomRareIronChestplate());
                                player.getInventory().addItem(customArmor.getRandomRareIronLeggings());
                                player.getInventory().addItem(customArmor.getRandomRareIronBoots());
                                break;

                            case "ironlegendary":
                                player.getInventory().addItem(customArmor.getRandomLegendaryIronHelmet());
                                player.getInventory().addItem(customArmor.getRandomLegendaryIronChestplate());
                                player.getInventory().addItem(customArmor.getRandomLegendaryIronLeggings());
                                player.getInventory().addItem(customArmor.getRandomLegendaryIronBoots());
                                break;

                            case "weapongrey":
                                player.getInventory().addItem(customWeapon.getRandomGreyStoneSword());
                                player.getInventory().addItem(customWeapon.getRandomGreyStoneAxe());
                                player.getInventory().addItem(customWeapon.getRandomGreyIronSword());
                                break;

                            case "weapongreen":
                                player.getInventory().addItem(customWeapon.getRandomGreenStoneSword());
                                player.getInventory().addItem(customWeapon.getRandomGreenStoneAxe());
                                player.getInventory().addItem(customWeapon.getRandomGreenIronSword());
                                break;

                            case "weaponblue":
                                player.getInventory().addItem(customWeapon.getRandomBlueStoneSword());
                                player.getInventory().addItem(customWeapon.getRandomBlueStoneAxe());
                                player.getInventory().addItem(customWeapon.getRandomBlueIronSword());
                                break;

                            case "weaponrare":
                                player.getInventory().addItem(customWeapon.getRandomRareStoneSword());
                                player.getInventory().addItem(customWeapon.getRandomRareStoneAxe());
                                player.getInventory().addItem(customWeapon.getRandomRareIronSword());
                                break;

                            case "weaponlegendary":
                                player.getInventory().addItem(customWeapon.getRandomLegendaryStoneSword());
                                player.getInventory().addItem(customWeapon.getRandomLegendaryStoneAxe());
                                player.getInventory().addItem(customWeapon.getRandomLegendaryIronSword());
                                break;

                            case "shield":
                                player.getInventory().addItem(customArmor.getRandomGreyShield());
                                player.getInventory().addItem(customArmor.getRandomGreenShield());
                                player.getInventory().addItem(customArmor.getRandomBlueShield());
                                player.getInventory().addItem(customArmor.getRandomRareShield());
                                player.getInventory().addItem(customArmor.getRandomLegendaryShield());
                                break;

                            default:
                                sender.sendMessage("Invalid armor type. Use leather, iron, diamond, or netherite.");
                                return true;
                        }

                        sender.sendMessage("You have received a custom " + armorType + " armor set.");
                        break;
                    case "join":
                        gameManager.addPlayerToGame(player);
                        break;
                    case "setlootchest":
                        if (!sender.isOp()) {
                            sender.sendMessage("Only operators can use this command.");
                            return true;
                        }

                        if (player.getTargetBlockExact(5).getType() != Material.CHEST) {
                            sender.sendMessage("You must be looking at a chest to set it as a loot chest.");
                            return true;
                        }

                        LootChest lootChest = new LootChest(player.getTargetBlockExact(5).getLocation());
                        lootChest.setLootTable(Arrays.asList(
                                customArmor.getRandomGreyLeatherHelmet(),
                                customArmor.getRandomGreyLeatherChestplate(),
                                customArmor.getRandomGreyLeatherLeggings(),
                                customArmor.getRandomGreyLeatherBoots(),

                                customArmor.getRandomGreenLeatherHelmet(),
                                customArmor.getRandomGreenLeatherChestplate(),
                                customArmor.getRandomGreenLeatherLeggings(),
                                customArmor.getRandomGreenLeatherBoots(),

                                customArmor.getRandomBlueLeatherHelmet(),
                                customArmor.getRandomBlueLeatherChestplate(),
                                customArmor.getRandomBlueLeatherLeggings(),
                                customArmor.getRandomBlueLeatherBoots(),

                                customArmor.getRandomRareLeatherHelmet(),
                                customArmor.getRandomRareLeatherChestplate(),
                                customArmor.getRandomRareLeatherLeggings(),
                                customArmor.getRandomRareLeatherBoots(),

                                customArmor.getRandomLegendaryLeatherHelmet(),
                                customArmor.getRandomLegendaryLeatherChestplate(),
                                customArmor.getRandomLegendaryLeatherLeggings(),
                                customArmor.getRandomLegendaryLeatherBoots(),

                                customArmor.getRandomGreyChainmailHelmet(),
                                customArmor.getRandomGreyChainmailChestplate(),
                                customArmor.getRandomGreyChainmailLeggings(),
                                customArmor.getRandomGreyChainmailBoots(),

                                customArmor.getRandomGreenChainmailHelmet(),
                                customArmor.getRandomGreenChainmailChestplate(),
                                customArmor.getRandomGreenChainmailLeggings(),
                                customArmor.getRandomGreenChainmailBoots(),

                                customArmor.getRandomBlueChainmailHelmet(),
                                customArmor.getRandomBlueChainmailChestplate(),
                                customArmor.getRandomBlueChainmailLeggings(),
                                customArmor.getRandomBlueChainmailBoots(),

                                customArmor.getRandomRareChainmailHelmet(),
                                customArmor.getRandomRareChainmailChestplate(),
                                customArmor.getRandomRareChainmailLeggings(),
                                customArmor.getRandomRareChainmailBoots(),

                                customArmor.getRandomLegendaryChainmailHelmet(),
                                customArmor.getRandomLegendaryChainmailChestplate(),
                                customArmor.getRandomLegendaryChainmailLeggings(),
                                customArmor.getRandomLegendaryChainmailBoots(),

                                customArmor.getRandomGreyIronHelmet(),
                                customArmor.getRandomGreyIronChestplate(),
                                customArmor.getRandomGreyIronLeggings(),
                                customArmor.getRandomGreyIronBoots(),

                                customArmor.getRandomGreenIronHelmet(),
                                customArmor.getRandomGreenIronChestplate(),
                                customArmor.getRandomGreenIronLeggings(),
                                customArmor.getRandomGreenIronBoots(),

                                customArmor.getRandomBlueIronHelmet(),
                                customArmor.getRandomBlueIronChestplate(),
                                customArmor.getRandomBlueIronLeggings(),
                                customArmor.getRandomBlueIronBoots(),

                                customArmor.getRandomRareIronHelmet(),
                                customArmor.getRandomRareIronChestplate(),
                                customArmor.getRandomRareIronLeggings(),
                                customArmor.getRandomRareIronBoots(),

                                customArmor.getRandomLegendaryIronHelmet(),
                                customArmor.getRandomLegendaryIronChestplate(),
                                customArmor.getRandomLegendaryIronLeggings(),
                                customArmor.getRandomLegendaryIronBoots(),

                                customArmor.getRandomGreyShield(),
                                customArmor.getRandomGreenShield(),
                                customArmor.getRandomBlueShield(),
                                customArmor.getRandomRareShield(),
                                customArmor.getRandomLegendaryShield()
                        ));

                        gameSession.saveLootChest(lootChest);
                        sender.sendMessage("Loot chest set successfully.");
                        break;
                    case "start":
                        if (gameSession != null) {
                            gameSession.startGame();
                            player.sendMessage("Game has been manually started.");
                        } else {
                            player.sendMessage("You are not in a game session.");
                        }
                        break;
                    case "stop":
                        if (gameSession != null) {
                            gameSession.endGame();
                            player.sendMessage("Game has been manually stopped.");
                        } else {
                            player.sendMessage("You are not in a game session.");
                        }
                        break;
                    case "setspawn":
                        // TODO: Call dungeonGenerator.setPlayerSpawn(player.getLocation());
                        break;
                    case "setpot":
                        // TODO: Call dungeonGenerator.setPot(player.getLocation());
                        break;
                    case "setbarrel":
                        // TODO: Call dungeonGenerator.setBarrel(player.getLocation());
                        break;
                    case "setmobspawn":
                        if (args.length < 2) {
                            player.sendMessage("Usage: /dnd setmobspawn [mob_name]");
                            return true;
                        }

                        String mobName = args[1];

                        if (mobName.equalsIgnoreCase("mummy")) {
                            customMobFactory.spawnMummy(player.getLocation());
                            player.sendMessage("Mummy spawned!");
                            Location spawnLocation = player.getLocation();
                            mobDataManager.saveMobSpawnLocation(mobName, spawnLocation);
                            return true;
                        } else {
                            player.sendMessage("Unknown mob name: " + mobName);
                            return true;
                        }
                    default:
                        player.sendMessage("Unknown command. Type /dnd for help.");
                        break;
                }
            }
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (command.getName().equalsIgnoreCase("dnd")) {
            if (args.length == 1) {
                List<String> subCommands = Arrays.asList("customgear", "setmobspawn", "setlootchest", "join", "stop", "start", "setspawn", "setpot", "setbarrel");
                return subCommands.stream()
                        .filter(subCommand -> subCommand.startsWith(args[0].toLowerCase()))
                        .collect(Collectors.toList());
            } else if (args.length == 2) {
                if (args[0].equalsIgnoreCase("customgear")) {
                    List<String> armorTypes = Arrays.asList(
                            "leathergrey", "leathergreen", "leatherblue", "leatherrare", "leatherlegendary",
                            "chainmailgrey", "chainmailgreen", "chainmailblue", "chainmailrare", "chainmaillegendary",
                            "irongrey", "irongreen", "ironblue", "ironrare", "ironlegendary",
                            "weapongrey", "weapongreen", "weaponblue", "weaponrare", "weaponlegendary",
                            "shield"
                    );
                    return armorTypes.stream()
                            .filter(armorType -> armorType.startsWith(args[1].toLowerCase()))
                            .collect(Collectors.toList());
                } else if (args[0].equalsIgnoreCase("setmobspawn")) {
                    List<String> mobNames = Collections.singletonList("mummy");
                    return mobNames.stream()
                            .filter(mobName -> mobName.startsWith(args[1].toLowerCase()))
                            .collect(Collectors.toList());
                }
            }
        }
        return Collections.emptyList();
    }
}