package jn.willfrydev.deluxedurability.objects;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;

import java.util.List;
import java.util.stream.Collectors;

public class CustomItem {
    private final String id;
    private final String displayName;
    private final Material material;
    private final int customModelData;
    private final int maxDurability;
    private final boolean willBreak;
    private final boolean disableRepairing;
    private final List<String> lore;
    private final Color dyeColor;
    private final String equipSlot;

    public CustomItem(String id, ConfigurationSection config) {
        this.id = id;
        this.displayName = ChatColor.translateAlternateColorCodes('&', config.getString("display-name", ""));
        this.material = Material.valueOf(config.getString("material", "STONE").toUpperCase());
        this.customModelData = config.getInt("custom-model-data", 0);
        this.maxDurability = config.getInt("max-durability", 100);
        this.willBreak = config.getBoolean("will-break", true);
        this.disableRepairing = config.getBoolean("disable-repairing", false);
        this.lore = config.getStringList("lore").stream()
                .map(line -> ChatColor.translateAlternateColorCodes('&', line))
                .collect(Collectors.toList());
        String colorString = config.getString("dye-color");
        if (colorString != null) {
            String[] rgb = colorString.replace(" ", "").split(",");
            this.dyeColor = Color.fromRGB(Integer.parseInt(rgb[0]), Integer.parseInt(rgb[1]), Integer.parseInt(rgb[2]));
        } else {
            this.dyeColor = null;
        }

        this.equipSlot = config.getString("equip-slot", null);
    }

    public String getId() { return id; }
    public String getDisplayName() { return displayName; }
    public Material getMaterial() { return material; }
    public int getCustomModelData() { return customModelData; }
    public int getMaxDurability() { return maxDurability; }
    public boolean willBreak() { return willBreak; }
    public boolean isRepairingDisabled() { return disableRepairing; }
    public List<String> getLore() { return lore; }
    public Color getDyeColor() { return dyeColor; }
    public String getEquipSlot() { return equipSlot; }
}