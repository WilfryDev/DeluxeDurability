package jn.willfrydev.deluxedurability.listeners;

import jn.willfrydev.deluxedurability.DeluxeDurability;
import jn.willfrydev.deluxedurability.managers.ItemManager;
import jn.willfrydev.deluxedurability.objects.CustomItem;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class DurabilityListener implements Listener {

    private final DeluxeDurability plugin;
    private final ItemManager itemManager;

    public DurabilityListener(DeluxeDurability plugin) {
        this.plugin = plugin;
        this.itemManager = plugin.getItemManager();
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onItemDamage(PlayerItemDamageEvent event) {
        Player player = event.getPlayer();
        if (player.getGameMode() == GameMode.CREATIVE) return;

        ItemStack item = event.getItem();
        ItemMeta meta = item.getItemMeta();

        if (meta == null || !meta.getPersistentDataContainer().has(itemManager.ITEM_ID_KEY, PersistentDataType.STRING)) {
            return;
        }

        event.setCancelled(true);

        String itemId = meta.getPersistentDataContainer().get(itemManager.ITEM_ID_KEY, PersistentDataType.STRING);
        CustomItem customItem = itemManager.getCustomItem(itemId);
        if (customItem == null) return;

        Integer currentDurability = meta.getPersistentDataContainer().get(itemManager.CURRENT_DURABILITY_KEY, PersistentDataType.INTEGER);
        if (currentDurability == null) return;

        int newDurability = currentDurability - event.getDamage();

        if (newDurability <= 0) {
            if (customItem.willBreak()) {

                event.getItem().setAmount(0);
                player.playSound(player.getLocation(), "minecraft:entity.item.break", 1.0f, 1.0f);
            } else {

                meta.getPersistentDataContainer().set(itemManager.CURRENT_DURABILITY_KEY, PersistentDataType.INTEGER, 0);
                item.setItemMeta(meta);
                itemManager.updateItemLoreAndDamage(item);
            }
            return;
        }

        meta.getPersistentDataContainer().set(itemManager.CURRENT_DURABILITY_KEY, PersistentDataType.INTEGER, newDurability);
        item.setItemMeta(meta);
        itemManager.updateItemLoreAndDamage(item);
    }

    @EventHandler
    public void onAnvilPrepare(PrepareAnvilEvent event) {
        ItemStack firstItem = event.getInventory().getItem(0);
        if (firstItem == null || firstItem.getType() == Material.AIR) return;

        ItemMeta meta = firstItem.getItemMeta();
        if (meta == null || !meta.getPersistentDataContainer().has(itemManager.ITEM_ID_KEY, PersistentDataType.STRING)) {
            return;
        }

        String itemId = meta.getPersistentDataContainer().get(itemManager.ITEM_ID_KEY, PersistentDataType.STRING);
        CustomItem customItem = itemManager.getCustomItem(itemId);

        if (customItem != null && customItem.isRepairingDisabled()) {
            event.setResult(null);
        }
    }
}