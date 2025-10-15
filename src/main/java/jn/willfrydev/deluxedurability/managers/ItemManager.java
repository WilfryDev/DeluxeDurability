package jn.willfrydev.deluxedurability.managers;

import jn.willfrydev.deluxedurability.DeluxeDurability;
import jn.willfrydev.deluxedurability.objects.CustomItem;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ItemManager {

    private final DeluxeDurability plugin;
    private final Map<String, CustomItem> customItems = new HashMap<>();

    public final NamespacedKey ITEM_ID_KEY;
    public final NamespacedKey CURRENT_DURABILITY_KEY;

    public ItemManager(DeluxeDurability plugin) {
        this.plugin = plugin;
        this.ITEM_ID_KEY = new NamespacedKey(plugin, "dd-item-id");
        this.CURRENT_DURABILITY_KEY = new NamespacedKey(plugin, "dd-current-durability");
    }

    public void loadItems() {
        customItems.clear();
        ConfigurationSection itemsSection = plugin.getConfig().getConfigurationSection("items");
        if (itemsSection == null) {
            plugin.getLogger().warning("No items found in config.yml!");
            return;
        }

        for (String key : itemsSection.getKeys(false)) {
            ConfigurationSection itemConfig = itemsSection.getConfigurationSection(key);
            if (itemConfig != null) {
                CustomItem customItem = new CustomItem(key, itemConfig);
                customItems.put(key.toUpperCase(), customItem);
            }
        }
        plugin.getLogger().info("Loaded " + customItems.size() + " custom items.");
    }

    public CustomItem getCustomItem(String id) {
        return customItems.get(id.toUpperCase());
    }

    public ItemStack createItemStack(String id) {
        CustomItem customItem = getCustomItem(id);
        if (customItem == null) {
            return null;
        }

        ItemStack item = new ItemStack(customItem.getMaterial());
        ItemMeta meta = item.getItemMeta();

        if (meta != null) {
            meta.setDisplayName(customItem.getDisplayName());

            if (customItem.getCustomModelData() > 0) {
                meta.setCustomModelData(customItem.getCustomModelData());
            }

            meta.getPersistentDataContainer().set(ITEM_ID_KEY, PersistentDataType.STRING, customItem.getId());
            meta.getPersistentDataContainer().set(CURRENT_DURABILITY_KEY, PersistentDataType.INTEGER, customItem.getMaxDurability());
            List<String> lore = customItem.getLore().stream()
                    .map(line -> line.replace("{current}", String.valueOf(customItem.getMaxDurability()))
                            .replace("{max}", String.valueOf(customItem.getMaxDurability())))
                    .collect(Collectors.toList());
            meta.setLore(lore);

            meta.setUnbreakable(true);
            meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ATTRIBUTES);
            if (meta instanceof LeatherArmorMeta && customItem.getDyeColor() != null) {
                ((LeatherArmorMeta) meta).setColor(customItem.getDyeColor());
            }

            item.setItemMeta(meta);
        }

        return item;
    }

    public ItemStack updateItemLoreAndDamage(ItemStack item) {
        if (item == null || item.getType() == Material.AIR) return item;

        ItemMeta meta = item.getItemMeta();
        if (meta == null || !meta.getPersistentDataContainer().has(ITEM_ID_KEY, PersistentDataType.STRING)) {
            return item;
        }

        String itemId = meta.getPersistentDataContainer().get(ITEM_ID_KEY, PersistentDataType.STRING);
        CustomItem customItem = getCustomItem(itemId);

        if (customItem == null) return item;

        Integer currentDurability = meta.getPersistentDataContainer().get(CURRENT_DURABILITY_KEY, PersistentDataType.INTEGER);
        if (currentDurability == null) {
            currentDurability = customItem.getMaxDurability();
            meta.getPersistentDataContainer().set(CURRENT_DURABILITY_KEY, PersistentDataType.INTEGER, currentDurability);
        }

        List<String> updatedLore = new ArrayList<>();
        for (String line : customItem.getLore()) {
            updatedLore.add(ChatColor.translateAlternateColorCodes('&', line
                    .replace("{current}", String.valueOf(currentDurability))
                    .replace("{max}", String.valueOf(customItem.getMaxDurability()))));
        }
        meta.setLore(updatedLore);
        if (meta instanceof Damageable) {
            Damageable damageable = (Damageable) meta;
            int maxVanillaDurability = item.getType().getMaxDurability();

            double durabilityRatio = (double) currentDurability / customItem.getMaxDurability();
            int damage = (int) (maxVanillaDurability * (1 - durabilityRatio));
            if (damage >= maxVanillaDurability) {
                damage = maxVanillaDurability -1;
            }
            if(damage < 0){
                damage = 0;
            }

            damageable.setDamage(damage);
        }

        item.setItemMeta(meta);
        return item;
    }
}
