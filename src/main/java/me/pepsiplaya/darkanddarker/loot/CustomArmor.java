package me.pepsiplaya.darkanddarker.loot;

import me.pepsiplaya.darkanddarker.actions.Lifesteal;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class CustomArmor {
    private static JavaPlugin plugin;

    public CustomArmor(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    private static final List<AttributeUtil.AttributeRange> GREY = Arrays.asList(
            new AttributeUtil.AttributeRange(Attribute.GENERIC_MAX_HEALTH, 1, 1),
            new AttributeUtil.AttributeRange(Attribute.GENERIC_ARMOR, 1, 1),
            new AttributeUtil.AttributeRange(Attribute.GENERIC_ATTACK_DAMAGE, 0.05, 0.10),
            new AttributeUtil.AttributeRange(Attribute.GENERIC_ATTACK_SPEED, 0.001, 0.0015),
            new AttributeUtil.AttributeRange(Attribute.GENERIC_MOVEMENT_SPEED, 0.001, 0.0015)
    );

    private static final List<AttributeUtil.AttributeRange> GREEN = Arrays.asList(
            new AttributeUtil.AttributeRange(Attribute.GENERIC_MAX_HEALTH, 1, 2),
            new AttributeUtil.AttributeRange(Attribute.GENERIC_ARMOR, 1, 2),
            new AttributeUtil.AttributeRange(Attribute.GENERIC_ATTACK_DAMAGE, 0.05, 0.2),
            new AttributeUtil.AttributeRange(Attribute.GENERIC_ATTACK_SPEED, 0.001, 0.0025),
            new AttributeUtil.AttributeRange(Attribute.GENERIC_MOVEMENT_SPEED, 0.001, 0.0025)
    );

    private static final List<AttributeUtil.AttributeRange> BLUE = Arrays.asList(
            new AttributeUtil.AttributeRange(Attribute.GENERIC_MAX_HEALTH, 1, 3),
            new AttributeUtil.AttributeRange(Attribute.GENERIC_ARMOR, 1, 2),
            new AttributeUtil.AttributeRange(Attribute.GENERIC_ATTACK_DAMAGE, 0.05, 0.3),
            new AttributeUtil.AttributeRange(Attribute.GENERIC_ATTACK_SPEED, 0.001, 0.0035),
            new AttributeUtil.AttributeRange(Attribute.GENERIC_MOVEMENT_SPEED, 0.001, 0.0035)
    );

    private static final List<AttributeUtil.AttributeRange> RARE = Arrays.asList(
            new AttributeUtil.AttributeRange(Attribute.GENERIC_MAX_HEALTH, 1, 4),
            new AttributeUtil.AttributeRange(Attribute.GENERIC_ARMOR, 1, 2),
            new AttributeUtil.AttributeRange(Attribute.GENERIC_ATTACK_DAMAGE, 0.05, 0.4),
            new AttributeUtil.AttributeRange(Attribute.GENERIC_ATTACK_SPEED, 0.001, 0.0045),
            new AttributeUtil.AttributeRange(Attribute.GENERIC_MOVEMENT_SPEED, 0.001, 0.0045)
    );

    private static final List<AttributeUtil.AttributeRange> LEGENDARY = Arrays.asList(
            new AttributeUtil.AttributeRange(Attribute.GENERIC_MAX_HEALTH, 1, 4),
            new AttributeUtil.AttributeRange(Attribute.GENERIC_ARMOR, 1, 2),
            new AttributeUtil.AttributeRange(Attribute.GENERIC_ATTACK_DAMAGE, 0.05, 0.5),
            new AttributeUtil.AttributeRange(Attribute.GENERIC_ATTACK_SPEED, 0.001, 0.0055),
            new AttributeUtil.AttributeRange(Attribute.GENERIC_MOVEMENT_SPEED, 0.001, 0.0055)
    );

    public ItemStack getRandomGreyLeatherHelmet() {
        ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
        AttributeUtil.addRandomAttributes(helmet, EquipmentSlot.HEAD, 1, 1, GREY, false);
        return setDisplayName(helmet, ChatColor.GRAY, "Cracked Leather Helmet");
    }

    public ItemStack getRandomGreyLeatherChestplate() {
        ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
        AttributeUtil.addRandomAttributes(chestplate, EquipmentSlot.CHEST, 1, 1, GREY, false);
        return setDisplayName(chestplate, ChatColor.GRAY, "Cracked Leather Chestplate");
    }

    public ItemStack getRandomGreyLeatherLeggings() {
        ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS);
        AttributeUtil.addRandomAttributes(leggings, EquipmentSlot.LEGS, 1, 1, GREY, false);
        return setDisplayName(leggings, ChatColor.GRAY, "Cracked Leather Leggings");
    }

    public ItemStack getRandomGreyLeatherBoots() {
        ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
        AttributeUtil.addRandomAttributes(boots, EquipmentSlot.FEET, 1, 1, GREY, false);
        return setDisplayName(boots, ChatColor.GRAY, "Cracked Leather Boots");
    }

    public ItemStack getRandomGreenLeatherHelmet() {
        ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
        AttributeUtil.addRandomAttributes(helmet, EquipmentSlot.HEAD, 1, 2, GREEN, false);
        return setDisplayName(helmet, ChatColor.DARK_GREEN, "Common Leather Helmet");
    }

    public ItemStack getRandomGreenLeatherChestplate() {
        ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
        AttributeUtil.addRandomAttributes(chestplate, EquipmentSlot.CHEST, 1, 2, GREEN, false);
        return setDisplayName(chestplate, ChatColor.DARK_GREEN, "Common Leather Chestplate");
    }

    public ItemStack getRandomGreenLeatherLeggings() {
        ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS);
        AttributeUtil.addRandomAttributes(leggings, EquipmentSlot.LEGS, 1, 2, GREEN, false);
        return setDisplayName(leggings, ChatColor.DARK_GREEN, "Common Leather Leggings");
    }

    public ItemStack getRandomGreenLeatherBoots() {
        ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
        AttributeUtil.addRandomAttributes(boots, EquipmentSlot.FEET, 1, 2, GREEN, false);
        return setDisplayName(boots, ChatColor.DARK_GREEN, "Common Leather Boots");
    }

    public ItemStack getRandomBlueLeatherHelmet() {
        ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
        AttributeUtil.addRandomAttributes(helmet, EquipmentSlot.HEAD, 1, 3, BLUE, false);
        return setDisplayName(helmet, ChatColor.DARK_BLUE, "Uncommon Leather Helmet");
    }

    public ItemStack getRandomBlueLeatherChestplate() {
        ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
        AttributeUtil.addRandomAttributes(chestplate, EquipmentSlot.CHEST, 1, 3, BLUE, false);
        return setDisplayName(chestplate, ChatColor.DARK_BLUE, "Uncommon Leather Chestplate");
    }

    public ItemStack getRandomBlueLeatherLeggings() {
        ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS);
        AttributeUtil.addRandomAttributes(leggings, EquipmentSlot.LEGS, 1, 3, BLUE, false);
        return setDisplayName(leggings, ChatColor.DARK_BLUE, "Uncommon Leather Leggings");
    }

    public ItemStack getRandomBlueLeatherBoots() {
        ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
        AttributeUtil.addRandomAttributes(boots, EquipmentSlot.FEET, 1, 3, BLUE, false);
        return setDisplayName(boots, ChatColor.DARK_BLUE, "Uncommon Leather Boots");
    }

    public ItemStack getRandomRareLeatherHelmet() {
        ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
        AttributeUtil.addRandomAttributes(helmet, EquipmentSlot.HEAD, 2, 3, RARE, false);
        return setDisplayName(helmet, ChatColor.DARK_PURPLE, "Rare Leather Helmet");
    }

    public ItemStack getRandomRareLeatherChestplate() {
        ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
        AttributeUtil.addRandomAttributes(chestplate, EquipmentSlot.CHEST, 2, 3, RARE, false);
        return setDisplayName(chestplate, ChatColor.DARK_PURPLE, "Rare Leather Chestplate");
    }

    public ItemStack getRandomRareLeatherLeggings() {
        ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS);
        AttributeUtil.addRandomAttributes(leggings, EquipmentSlot.LEGS, 2, 3, RARE, false);
        return setDisplayName(leggings, ChatColor.DARK_PURPLE, "Rare Leather Leggings");
    }

    public ItemStack getRandomRareLeatherBoots() {
        ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
        AttributeUtil.addRandomAttributes(boots, EquipmentSlot.FEET, 2, 3, RARE, false);
        return setDisplayName(boots, ChatColor.DARK_PURPLE, "Rare Leather Boots");
    }

    public ItemStack getRandomLegendaryLeatherHelmet() {
        ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
        AttributeUtil.addRandomAttributes(helmet, EquipmentSlot.HEAD, 2, 4, LEGENDARY, false);
        return setDisplayName(helmet, ChatColor.RED, "Legendary Leather Helmet");
    }

    public ItemStack getRandomLegendaryLeatherChestplate() {
        ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
        AttributeUtil.addRandomAttributes(chestplate, EquipmentSlot.CHEST, 2, 4, LEGENDARY, false);
        return setDisplayName(chestplate, ChatColor.RED, "Legendary Leather Chestplate");
    }

    public ItemStack getRandomLegendaryLeatherLeggings() {
        ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS);
        AttributeUtil.addRandomAttributes(leggings, EquipmentSlot.LEGS, 2, 4, LEGENDARY, false);
        return setDisplayName(leggings, ChatColor.RED, "Legendary Leather Leggings");
    }

    public ItemStack getRandomLegendaryLeatherBoots() {
        ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
        AttributeUtil.addRandomAttributes(boots, EquipmentSlot.FEET, 2, 4, LEGENDARY, false);
        return setDisplayName(boots, ChatColor.RED, "Legendary Leather Boots");
    }

    public ItemStack getRandomGreyChainmailHelmet() {
        ItemStack helmet = new ItemStack(Material.CHAINMAIL_HELMET);
        AttributeUtil.addRandomAttributes(helmet, EquipmentSlot.HEAD, 1, 1, GREY, false);
        return setDisplayName(helmet, ChatColor.GRAY, "Cracked Chainmail Helmet");
    }

    public ItemStack getRandomGreyChainmailChestplate() {
        ItemStack chestplate = new ItemStack(Material.CHAINMAIL_CHESTPLATE);
        AttributeUtil.addRandomAttributes(chestplate, EquipmentSlot.CHEST, 1, 1, GREY, false);
        return setDisplayName(chestplate, ChatColor.GRAY, "Cracked Chainmail Chestplate");
    }

    public ItemStack getRandomGreyChainmailLeggings() {
        ItemStack leggings = new ItemStack(Material.CHAINMAIL_LEGGINGS);
        AttributeUtil.addRandomAttributes(leggings, EquipmentSlot.LEGS, 1, 1, GREY, false);
        return setDisplayName(leggings, ChatColor.GRAY, "Cracked Chainmail Leggings");
    }

    public ItemStack getRandomGreyChainmailBoots() {
        ItemStack boots = new ItemStack(Material.CHAINMAIL_BOOTS);
        AttributeUtil.addRandomAttributes(boots, EquipmentSlot.FEET, 1, 1, GREY, false);
        return setDisplayName(boots, ChatColor.GRAY, "Cracked Chainmail Boots");
    }

    public ItemStack getRandomGreenChainmailHelmet() {
        ItemStack helmet = new ItemStack(Material.CHAINMAIL_HELMET);
        AttributeUtil.addRandomAttributes(helmet, EquipmentSlot.HEAD, 1, 2, GREEN, false);
        return setDisplayName(helmet, ChatColor.DARK_GREEN, "Common Chainmail Helmet");
    }

    public ItemStack getRandomGreenChainmailChestplate() {
        ItemStack chestplate = new ItemStack(Material.CHAINMAIL_CHESTPLATE);
        AttributeUtil.addRandomAttributes(chestplate, EquipmentSlot.CHEST, 1, 2, GREEN, false);
        return setDisplayName(chestplate, ChatColor.DARK_GREEN, "Common Chainmail Chestplate");
    }

    public ItemStack getRandomGreenChainmailLeggings() {
        ItemStack leggings = new ItemStack(Material.CHAINMAIL_LEGGINGS);
        AttributeUtil.addRandomAttributes(leggings, EquipmentSlot.LEGS, 1, 2, GREEN, false);
        return setDisplayName(leggings, ChatColor.DARK_GREEN, "Common Chainmail Leggings");
    }

    public ItemStack getRandomGreenChainmailBoots() {
        ItemStack boots = new ItemStack(Material.CHAINMAIL_BOOTS);
        AttributeUtil.addRandomAttributes(boots, EquipmentSlot.FEET, 1, 2, GREEN, false);
        return setDisplayName(boots, ChatColor.DARK_GREEN, "Common Chainmail Boots");
    }

    public ItemStack getRandomBlueChainmailHelmet() {
        ItemStack helmet = new ItemStack(Material.CHAINMAIL_HELMET);
        AttributeUtil.addRandomAttributes(helmet, EquipmentSlot.HEAD, 1, 3, BLUE, false);
        return setDisplayName(helmet, ChatColor.DARK_BLUE, "Uncommon Chainmail Helmet");
    }

    public ItemStack getRandomBlueChainmailChestplate() {
        ItemStack chestplate = new ItemStack(Material.CHAINMAIL_CHESTPLATE);
        AttributeUtil.addRandomAttributes(chestplate, EquipmentSlot.CHEST, 1, 3, BLUE, false);
        return setDisplayName(chestplate, ChatColor.DARK_BLUE, "Uncommon Chainmail Chestplate");
    }

    public ItemStack getRandomBlueChainmailLeggings() {
        ItemStack leggings = new ItemStack(Material.CHAINMAIL_LEGGINGS);
        AttributeUtil.addRandomAttributes(leggings, EquipmentSlot.LEGS, 1, 3, BLUE, false);
        return setDisplayName(leggings, ChatColor.DARK_BLUE, "Uncommon Chainmail Leggings");
    }

    public ItemStack getRandomBlueChainmailBoots() {
        ItemStack boots = new ItemStack(Material.CHAINMAIL_BOOTS);
        AttributeUtil.addRandomAttributes(boots, EquipmentSlot.FEET, 1, 3, BLUE, false);
        return setDisplayName(boots, ChatColor.DARK_BLUE, "Uncommon Chainmail Boots");
    }

    public ItemStack getRandomRareChainmailHelmet() {
        ItemStack helmet = new ItemStack(Material.CHAINMAIL_HELMET);
        AttributeUtil.addRandomAttributes(helmet, EquipmentSlot.HEAD, 2, 3, RARE, false);
        return setDisplayName(helmet, ChatColor.DARK_PURPLE, "Rare Chainmail Helmet");
    }

    public ItemStack getRandomRareChainmailChestplate() {
        ItemStack chestplate = new ItemStack(Material.CHAINMAIL_CHESTPLATE);
        AttributeUtil.addRandomAttributes(chestplate, EquipmentSlot.CHEST, 2, 3, RARE, false);
        return setDisplayName(chestplate, ChatColor.DARK_PURPLE, "Rare Chainmail Chestplate");
    }

    public ItemStack getRandomRareChainmailLeggings() {
        ItemStack leggings = new ItemStack(Material.CHAINMAIL_LEGGINGS);
        AttributeUtil.addRandomAttributes(leggings, EquipmentSlot.LEGS, 2, 3, RARE, false);
        return setDisplayName(leggings, ChatColor.DARK_PURPLE, "Rare Chainmail Leggings");
    }

    public ItemStack getRandomRareChainmailBoots() {
        ItemStack boots = new ItemStack(Material.CHAINMAIL_BOOTS);
        AttributeUtil.addRandomAttributes(boots, EquipmentSlot.FEET, 2, 3, RARE, false);
        return setDisplayName(boots, ChatColor.DARK_PURPLE, "Rare Chainmail Boots");
    }

    public ItemStack getRandomLegendaryChainmailHelmet() {
        ItemStack helmet = new ItemStack(Material.CHAINMAIL_HELMET);
        AttributeUtil.addRandomAttributes(helmet, EquipmentSlot.HEAD, 2, 4, LEGENDARY, false);
        return setDisplayName(helmet, ChatColor.RED, "Legendary Chainmail Helmet");
    }

    public ItemStack getRandomLegendaryChainmailChestplate() {
        ItemStack chestplate = new ItemStack(Material.CHAINMAIL_CHESTPLATE);
        AttributeUtil.addRandomAttributes(chestplate, EquipmentSlot.CHEST, 2, 4, LEGENDARY, false);
        return setDisplayName(chestplate, ChatColor.RED, "Legendary Chainmail Chestplate");
    }

    public ItemStack getRandomLegendaryChainmailLeggings() {
        ItemStack leggings = new ItemStack(Material.CHAINMAIL_LEGGINGS);
        AttributeUtil.addRandomAttributes(leggings, EquipmentSlot.LEGS, 2, 4, LEGENDARY, false);
        return setDisplayName(leggings, ChatColor.RED, "Legendary Chainmail Leggings");
    }

    public ItemStack getRandomLegendaryChainmailBoots() {
        ItemStack boots = new ItemStack(Material.CHAINMAIL_BOOTS);
        AttributeUtil.addRandomAttributes(boots, EquipmentSlot.FEET, 2, 4, LEGENDARY, false);
        return setDisplayName(boots, ChatColor.RED, "Legendary Chainmail Boots");
    }

    public ItemStack getRandomGreyIronHelmet() {
        ItemStack helmet = new ItemStack(Material.IRON_HELMET);
        AttributeUtil.addRandomAttributes(helmet, EquipmentSlot.HEAD, 1, 1, GREY, false);
        return setDisplayName(helmet, ChatColor.GRAY, "Cracked Iron Helmet");
    }

    public ItemStack getRandomGreyIronChestplate() {
        ItemStack chestplate = new ItemStack(Material.IRON_CHESTPLATE);
        AttributeUtil.addRandomAttributes(chestplate, EquipmentSlot.CHEST, 1, 1, GREY, false);
        return setDisplayName(chestplate, ChatColor.GRAY, "Cracked Iron Chestplate");
    }

    public ItemStack getRandomGreyIronLeggings() {
        ItemStack leggings = new ItemStack(Material.IRON_LEGGINGS);
        AttributeUtil.addRandomAttributes(leggings, EquipmentSlot.LEGS, 1, 1, GREY, false);
        return setDisplayName(leggings, ChatColor.GRAY, "Cracked Iron Leggings");
    }

    public ItemStack getRandomGreyIronBoots() {
        ItemStack boots = new ItemStack(Material.IRON_BOOTS);
        AttributeUtil.addRandomAttributes(boots, EquipmentSlot.FEET, 1, 1, GREY, false);
        return setDisplayName(boots, ChatColor.GRAY, "Cracked Iron Boots");
    }

    public ItemStack getRandomGreenIronHelmet() {
        ItemStack helmet = new ItemStack(Material.IRON_HELMET);
        AttributeUtil.addRandomAttributes(helmet, EquipmentSlot.HEAD, 1, 2, GREEN, false);
        return setDisplayName(helmet, ChatColor.DARK_GREEN, "Common Iron Helmet");
    }

    public ItemStack getRandomGreenIronChestplate() {
        ItemStack chestplate = new ItemStack(Material.IRON_CHESTPLATE);
        AttributeUtil.addRandomAttributes(chestplate, EquipmentSlot.CHEST, 1, 2, GREEN, false);
        return setDisplayName(chestplate, ChatColor.DARK_GREEN, "Common Iron Chestplate");
    }

    public ItemStack getRandomGreenIronLeggings() {
        ItemStack leggings = new ItemStack(Material.IRON_LEGGINGS);
        AttributeUtil.addRandomAttributes(leggings, EquipmentSlot.LEGS, 1, 2, GREEN, false);
        return setDisplayName(leggings, ChatColor.DARK_GREEN, "Common Iron Leggings");
    }

    public ItemStack getRandomGreenIronBoots() {
        ItemStack boots = new ItemStack(Material.IRON_BOOTS);
        AttributeUtil.addRandomAttributes(boots, EquipmentSlot.FEET, 1, 2, GREEN, false);
        return setDisplayName(boots, ChatColor.DARK_GREEN, "Common Iron Boots");
    }

    public ItemStack getRandomBlueIronHelmet() {
        ItemStack helmet = new ItemStack(Material.IRON_HELMET);
        AttributeUtil.addRandomAttributes(helmet, EquipmentSlot.HEAD, 1, 3, BLUE, false);
        return setDisplayName(helmet, ChatColor.DARK_BLUE, "Uncommon Iron Helmet");
    }

    public ItemStack getRandomBlueIronChestplate() {
        ItemStack chestplate = new ItemStack(Material.IRON_CHESTPLATE);
        AttributeUtil.addRandomAttributes(chestplate, EquipmentSlot.CHEST, 1, 3, BLUE, false);
        return setDisplayName(chestplate, ChatColor.DARK_BLUE, "Uncommon Iron Chestplate");
    }

    public ItemStack getRandomBlueIronLeggings() {
        ItemStack leggings = new ItemStack(Material.IRON_LEGGINGS);
        AttributeUtil.addRandomAttributes(leggings, EquipmentSlot.LEGS, 1, 3, BLUE, false);
        return setDisplayName(leggings, ChatColor.DARK_BLUE, "Uncommon Iron Leggings");
    }

    public ItemStack getRandomBlueIronBoots() {
        ItemStack boots = new ItemStack(Material.IRON_BOOTS);
        AttributeUtil.addRandomAttributes(boots, EquipmentSlot.FEET, 1, 3, BLUE, false);
        return setDisplayName(boots, ChatColor.DARK_BLUE, "Uncommon Iron Boots");
    }

    public ItemStack getRandomRareIronHelmet() {
        ItemStack helmet = new ItemStack(Material.IRON_HELMET);
        AttributeUtil.addRandomAttributes(helmet, EquipmentSlot.HEAD, 2, 3, RARE, false);
        return setDisplayName(helmet, ChatColor.DARK_PURPLE, "Rare Iron Helmet");
    }

    public ItemStack getRandomRareIronChestplate() {
        ItemStack chestplate = new ItemStack(Material.IRON_CHESTPLATE);
        AttributeUtil.addRandomAttributes(chestplate, EquipmentSlot.CHEST, 2, 3, RARE, false);
        return setDisplayName(chestplate, ChatColor.DARK_PURPLE, "Rare Iron Chestplate");
    }

    public ItemStack getRandomRareIronLeggings() {
        ItemStack leggings = new ItemStack(Material.IRON_LEGGINGS);
        AttributeUtil.addRandomAttributes(leggings, EquipmentSlot.LEGS, 2, 3, RARE, false);
        return setDisplayName(leggings, ChatColor.DARK_PURPLE, "Rare Iron Leggings");
    }

    public ItemStack getRandomRareIronBoots() {
        ItemStack boots = new ItemStack(Material.IRON_BOOTS);
        AttributeUtil.addRandomAttributes(boots, EquipmentSlot.FEET, 2, 3, RARE, false);
        return setDisplayName(boots, ChatColor.DARK_PURPLE, "Rare Iron Boots");
    }

    public ItemStack getRandomLegendaryIronHelmet() {
        ItemStack helmet = new ItemStack(Material.IRON_HELMET);
        AttributeUtil.addRandomAttributes(helmet, EquipmentSlot.HEAD, 2, 4, LEGENDARY, false);
        return setDisplayName(helmet, ChatColor.RED, "Legendary Iron Helmet");
    }

    public ItemStack getRandomLegendaryIronChestplate() {
        ItemStack chestplate = new ItemStack(Material.IRON_CHESTPLATE);
        AttributeUtil.addRandomAttributes(chestplate, EquipmentSlot.CHEST, 2, 4, LEGENDARY, false);
        return setDisplayName(chestplate, ChatColor.RED, "Legendary Iron Chestplate");
    }

    public ItemStack getRandomLegendaryIronLeggings() {
        ItemStack leggings = new ItemStack(Material.IRON_LEGGINGS);
        AttributeUtil.addRandomAttributes(leggings, EquipmentSlot.LEGS, 2, 4, LEGENDARY, false);
        return setDisplayName(leggings, ChatColor.RED, "Legendary Iron Leggings");
    }

    public ItemStack getRandomLegendaryIronBoots() {
        ItemStack boots = new ItemStack(Material.IRON_BOOTS);
        AttributeUtil.addRandomAttributes(boots, EquipmentSlot.FEET, 2, 4, LEGENDARY, false);
        return setDisplayName(boots, ChatColor.RED, "Legendary Iron Boots");
    }

    public ItemStack getRandomGreyShield() {
        ItemStack shield = new ItemStack(Material.SHIELD);
        AttributeUtil.addRandomAttributes(shield, EquipmentSlot.OFF_HAND, 1, 1, GREY, false);
        return setDisplayName(shield, ChatColor.GRAY, "Cracked Shield");
    }

    public ItemStack getRandomGreenShield() {
        ItemStack shield = new ItemStack(Material.SHIELD);
        AttributeUtil.addRandomAttributes(shield, EquipmentSlot.OFF_HAND, 1, 1, GREEN, false);
        return setDisplayName(shield, ChatColor.DARK_GREEN, "Common Shield");
    }

    public ItemStack getRandomBlueShield() {
        ItemStack shield = new ItemStack(Material.SHIELD);
        AttributeUtil.addRandomAttributes(shield, EquipmentSlot.OFF_HAND, 1, 2, BLUE, false);
        return setDisplayName(shield, ChatColor.DARK_BLUE, "Uncommon Shield");
    }

    public ItemStack getRandomRareShield() {
        ItemStack shield = new ItemStack(Material.SHIELD);
        AttributeUtil.addRandomAttributes(shield, EquipmentSlot.OFF_HAND, 1, 2, RARE, false);
        return setDisplayName(shield, ChatColor.DARK_PURPLE, "Rare Shield");
    }

    public ItemStack getRandomLegendaryShield() {
        ItemStack shield = new ItemStack(Material.SHIELD);
        AttributeUtil.addRandomAttributes(shield, EquipmentSlot.OFF_HAND, 2, 2, LEGENDARY, false);
        return setDisplayName(shield, ChatColor.RED, "Legendary Shield");
    }

    private ItemStack setDisplayName(ItemStack item, ChatColor color, String name) {
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(color + name);
            item.setItemMeta(meta);
        }
        return item;
    }

    public static void addRandomAttributes(ItemStack item, EquipmentSlot slot, int minAttributes, int maxAttributes, List<AttributeUtil.AttributeRange> attributeRanges, boolean includeLifesteal) {
        ItemMeta meta = item.getItemMeta();

        // Add other attributes...

        if (includeLifesteal) {
            double lifestealChance = ThreadLocalRandom.current().nextDouble(0.1, 0.3);
            meta.getPersistentDataContainer().set(new NamespacedKey(plugin, Lifesteal.LIFESTEAL_KEY), PersistentDataType.DOUBLE, lifestealChance);
        }

        item.setItemMeta(meta);
    }
}