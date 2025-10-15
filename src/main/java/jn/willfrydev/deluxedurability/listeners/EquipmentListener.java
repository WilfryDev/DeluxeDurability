package jn.willfrydev.deluxedurability.listeners;

import jn.willfrydev.deluxedurability.DeluxeDurability;
import jn.willfrydev.deluxedurability.managers.ItemManager;
import jn.willfrydev.deluxedurability.objects.CustomItem;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class EquipmentListener implements Listener {

    private final ItemManager itemManager;

    public EquipmentListener(DeluxeDurability plugin) {
        this.itemManager = plugin.getItemManager();
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {

        if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        Player player = event.getPlayer();
        ItemStack itemInHand = player.getInventory().getItemInMainHand();

        if (itemInHand == null || itemInHand.getType() == Material.AIR) return;

        ItemMeta meta = itemInHand.getItemMeta();
        if (meta == null || !meta.getPersistentDataContainer().has(itemManager.ITEM_ID_KEY, PersistentDataType.STRING)) {
            return;
        }

        String itemId = meta.getPersistentDataContainer().get(itemManager.ITEM_ID_KEY, PersistentDataType.STRING);
        CustomItem customItem = itemManager.getCustomItem(itemId);
        if (customItem == null || customItem.getEquipSlot() == null) return;
        event.setCancelled(true);

        PlayerInventory inventory = player.getInventory();
        String slot = customItem.getEquipSlot().toUpperCase();
        ItemStack currentArmor;

        switch (slot) {
            case "HELMET":
                currentArmor = inventory.getHelmet();
                inventory.setHelmet(itemInHand);
                break;
            case "CHESTPLATE":
                currentArmor = inventory.getChestplate();
                inventory.setChestplate(itemInHand);
                break;
            case "LEGGINGS":
                currentArmor = inventory.getLeggings();
                inventory.setLeggings(itemInHand);
                break;
            case "BOOTS":
                currentArmor = inventory.getBoots();
                inventory.setBoots(itemInHand);
                break;
            default:
                return;
        }

        inventory.setItemInMainHand(currentArmor);
        player.playSound(player.getLocation(), Sound.ITEM_ARMOR_EQUIP_GENERIC, 1.0f, 1.0f);
    }
}