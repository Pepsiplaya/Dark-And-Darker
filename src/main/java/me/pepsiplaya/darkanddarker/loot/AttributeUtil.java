package me.pepsiplaya.darkanddarker.loot;

import me.pepsiplaya.darkanddarker.actions.Lifesteal;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class AttributeUtil {
    private static JavaPlugin plugin;

    public AttributeUtil(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public static class AttributeRange {
        Attribute attribute;
        double minValue;
        double maxValue;

        public AttributeRange(Attribute attribute, double minValue, double maxValue) {
            this.attribute = attribute;
            this.minValue = minValue;
            this.maxValue = maxValue;
        }
    }

    public static void addRandomAttributes(ItemStack item, EquipmentSlot slot, int minAttributes, int maxAttributes, List<AttributeRange> attributeRanges, boolean includeLifesteal) {
        ItemMeta meta = item.getItemMeta();

        int numberOfAttributes = ThreadLocalRandom.current().nextInt(minAttributes, maxAttributes + 1);
        List<AttributeRange> shuffledAttributes = new ArrayList<>(attributeRanges);
        java.util.Collections.shuffle(shuffledAttributes);

        for (int i = 0; i < numberOfAttributes && i < shuffledAttributes.size(); i++) {
            AttributeRange range = shuffledAttributes.get(i);
            double randomValue = ThreadLocalRandom.current().nextDouble(range.minValue, range.maxValue);
            meta.addAttributeModifier(range.attribute, new AttributeModifier(UUID.randomUUID(), range.attribute.name(), randomValue, AttributeModifier.Operation.ADD_NUMBER, slot));
        }

        if (includeLifesteal) {
            double lifestealChance = ThreadLocalRandom.current().nextDouble(0.05, 0.2);
            meta.getPersistentDataContainer().set(new NamespacedKey(plugin, Lifesteal.LIFESTEAL_KEY), PersistentDataType.DOUBLE, lifestealChance);
        }

        item.setItemMeta(meta);
    }
}