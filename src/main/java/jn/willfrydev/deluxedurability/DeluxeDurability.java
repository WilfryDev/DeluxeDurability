package jn.willfrydev.deluxedurability;

import jn.willfrydev.deluxedurability.commands.DeluxeDurabilityCommand;
import jn.willfrydev.deluxedurability.listeners.DurabilityListener;
import jn.willfrydev.deluxedurability.listeners.EquipmentListener;
import jn.willfrydev.deluxedurability.managers.ItemManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class DeluxeDurability extends JavaPlugin {

    private ItemManager itemManager;

    @Override
    public void onEnable() {

        saveDefaultConfig();
        this.itemManager = new ItemManager(this);
        itemManager.loadItems();
        getCommand("deluxedurability").setExecutor(new DeluxeDurabilityCommand(this));
        getServer().getPluginManager().registerEvents(new DurabilityListener(this), this);
        getServer().getPluginManager().registerEvents(new EquipmentListener(this), this); // <-- REGISTRAR NUEVO LISTENER

        getLogger().info("DeluxeDurability has been enabled successfully! | Vertix Network");
    }

    @Override
    public void onDisable() {
        getLogger().info("DeluxeDurability has been disabled. | Vertix Network");
    }

    public ItemManager getItemManager() {
        return itemManager;
    }
}