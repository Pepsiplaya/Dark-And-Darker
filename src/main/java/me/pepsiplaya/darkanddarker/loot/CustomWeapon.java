package me.pepsiplaya.darkanddarker.loot;

import me.pepsiplaya.darkanddarker.actions.Lifesteal;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public class CustomWeapon {
    private static final List<AttributeUtil.AttributeRange> GREY = Arrays.asList(
            new AttributeUtil.AttributeRange(Attribute.GENERIC_ATTACK_DAMAGE, 1, 1),
            new AttributeUtil.AttributeRange(Attribute.GENERIC_ATTACK_SPEED, 0.001, 0.002)
    );

    private static final List<AttributeUtil.AttributeRange> GREEN = Arrays.asList(
            new AttributeUtil.AttributeRange(Attribute.GENERIC_ATTACK_DAMAGE, 1, 2),
            new AttributeUtil.AttributeRange(Attribute.GENERIC_ATTACK_SPEED, 0.002, 0.003)
    );

    private static final List<AttributeUtil.AttributeRange> BLUE = Arrays.asList(
            new AttributeUtil.AttributeRange(Attribute.GENERIC_ATTACK_DAMAGE, 1, 2),
            new AttributeUtil.AttributeRange(Attribute.GENERIC_ATTACK_SPEED, 0.003, 0.004)
    );

    private static final List<AttributeUtil.AttributeRange> RARE = Arrays.asList(
            new AttributeUtil.AttributeRange(Attribute.GENERIC_ATTACK_DAMAGE, 1, 3),
            new AttributeUtil.AttributeRange(Attribute.GENERIC_ATTACK_SPEED, 0.004, 0.005)
    );

    private static final List<AttributeUtil.AttributeRange> LEGENDARY = Arrays.asList(
            new AttributeUtil.AttributeRange(Attribute.GENERIC_ATTACK_DAMAGE, 1, 3),
            new AttributeUtil.AttributeRange(Attribute.GENERIC_ATTACK_SPEED, 0.005, 0.006)
    );

    public ItemStack getRandomGreyStoneSword() {
        ItemStack sword = new ItemStack(Material.STONE_SWORD);
        AttributeUtil.addRandomAttributes(sword, EquipmentSlot.HAND, 1, 1, GREY, false);
        return setDisplayName(sword, ChatColor.GRAY, "Cracked Stone Sword");
    }

    public ItemStack getRandomGreenStoneSword() {
        ItemStack sword = new ItemStack(Material.STONE_SWORD);
        AttributeUtil.addRandomAttributes(sword, EquipmentSlot.HAND, 1, 1, GREEN, false);
        return setDisplayName(sword, ChatColor.DARK_GREEN, "Common Stone Sword");
    }

    public ItemStack getRandomBlueStoneSword() {
        ItemStack sword = new ItemStack(Material.STONE_SWORD);
        AttributeUtil.addRandomAttributes(sword, EquipmentSlot.HAND, 1, 2, BLUE, false);
        return setDisplayName(sword, ChatColor.DARK_BLUE, "Uncommon Stone Sword");
    }

    public ItemStack getRandomRareStoneSword() {
        ItemStack sword = new ItemStack(Material.STONE_SWORD);
        AttributeUtil.addRandomAttributes(sword, EquipmentSlot.HAND, 1, 2, RARE, false);
        return setDisplayName(sword, ChatColor.DARK_PURPLE, "Rare Stone Sword");
    }

    public ItemStack getRandomLegendaryStoneSword() {
        ItemStack sword = new ItemStack(Material.STONE_SWORD);
        AttributeUtil.addRandomAttributes(sword, EquipmentSlot.HAND, 2, 2, LEGENDARY, true);
        return setDisplayName(sword, ChatColor.RED, "Legendary Stone Sword");
    }

    public ItemStack getRandomGreyIronSword() {
        ItemStack sword = new ItemStack(Material.IRON_SWORD);
        AttributeUtil.addRandomAttributes(sword, EquipmentSlot.HAND, 1, 1, GREY, false);
        return setDisplayName(sword, ChatColor.GRAY, "Cracked Iron Sword");
    }

    public ItemStack getRandomGreenIronSword() {
        ItemStack sword = new ItemStack(Material.IRON_SWORD);
        AttributeUtil.addRandomAttributes(sword, EquipmentSlot.HAND, 1, 1, GREEN, false);
        return setDisplayName(sword, ChatColor.DARK_GREEN, "Common Iron Sword");
    }

    public ItemStack getRandomBlueIronSword() {
        ItemStack sword = new ItemStack(Material.IRON_SWORD);
        AttributeUtil.addRandomAttributes(sword, EquipmentSlot.HAND, 1, 2, BLUE, false);
        return setDisplayName(sword, ChatColor.DARK_BLUE, "Uncommon Iron Sword");
    }

    public ItemStack getRandomRareIronSword() {
        ItemStack sword = new ItemStack(Material.IRON_SWORD);
        AttributeUtil.addRandomAttributes(sword, EquipmentSlot.HAND, 1, 2, RARE, false);
        return setDisplayName(sword, ChatColor.DARK_PURPLE, "Rare Iron Sword");
    }

    public ItemStack getRandomLegendaryIronSword() {
        ItemStack sword = new ItemStack(Material.IRON_SWORD);
        AttributeUtil.addRandomAttributes(sword, EquipmentSlot.HAND, 2, 2, LEGENDARY, true);
        return setDisplayName(sword, ChatColor.RED, "Legendary Iron Sword");
    }

    public ItemStack getRandomGreyStoneAxe() {
        ItemStack axe = new ItemStack(Material.STONE_AXE);
        AttributeUtil.addRandomAttributes(axe, EquipmentSlot.HAND, 1, 1, GREY, false);
        return setDisplayName(axe, ChatColor.GRAY, "Cracked Stone Axe");
    }

    public ItemStack getRandomGreenStoneAxe() {
        ItemStack axe = new ItemStack(Material.STONE_AXE);
        AttributeUtil.addRandomAttributes(axe, EquipmentSlot.HAND, 1, 1, GREEN, false);
        return setDisplayName(axe, ChatColor.DARK_GREEN, "Common Stone Axe");
    }

    public ItemStack getRandomBlueStoneAxe() {
        ItemStack axe = new ItemStack(Material.STONE_AXE);
        AttributeUtil.addRandomAttributes(axe, EquipmentSlot.HAND, 1, 2, BLUE, false);
        return setDisplayName(axe, ChatColor.DARK_BLUE, "Uncommon Stone Axe");
    }

    public ItemStack getRandomRareStoneAxe() {
        ItemStack axe = new ItemStack(Material.STONE_AXE);
        AttributeUtil.addRandomAttributes(axe, EquipmentSlot.HAND, 1, 2, RARE, false);
        return setDisplayName(axe, ChatColor.DARK_PURPLE, "Rare Stone Axe");
    }

    public ItemStack getRandomLegendaryStoneAxe() {
        ItemStack axe = new ItemStack(Material.STONE_AXE);
        AttributeUtil.addRandomAttributes(axe, EquipmentSlot.HAND, 2, 2, LEGENDARY, true);
        return setDisplayName(axe, ChatColor.RED, "Legendary Stone Axe");
    }

    private ItemStack setDisplayName(ItemStack item, ChatColor color, String name) {
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(color + name);
            item.setItemMeta(meta);
        }
        return item;
    }
}
