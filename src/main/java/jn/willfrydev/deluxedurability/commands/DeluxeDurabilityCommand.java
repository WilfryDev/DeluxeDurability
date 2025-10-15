package jn.willfrydev.deluxedurability.commands;

import jn.willfrydev.deluxedurability.DeluxeDurability;
import jn.willfrydev.deluxedurability.managers.ItemManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class DeluxeDurabilityCommand implements CommandExecutor {

    private final DeluxeDurability plugin;

    public DeluxeDurabilityCommand(DeluxeDurability plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String prefix = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.prefix", ""));

        if (args.length == 0 || args[0].equalsIgnoreCase("help")) {
            plugin.getConfig().getStringList("messages.help").forEach(line ->
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', line))
            );
            return true;
        }

        String subCommand = args[0].toLowerCase();

        switch (subCommand) {
            case "reload":
                if (!sender.hasPermission("deluxedurability.reload")) {
                    sender.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.no-permission")));
                    return true;
                }
                plugin.reloadConfig();
                plugin.getItemManager().loadItems();
                sender.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.reload")));
                break;

            case "give":
                if (!sender.hasPermission("deluxedurability.give")) {
                    sender.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.no-permission")));
                    return true;
                }
                if (!(sender instanceof Player)) {
                    sender.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.player-only")));
                    return true;
                }
                if (args.length < 2) {
                    sender.sendMessage(prefix + "&cUsage: /dd give <item_id>");
                    return true;
                }

                Player player = (Player) sender;
                String itemId = args[1];
                ItemStack item = plugin.getItemManager().createItemStack(itemId);

                if (item == null) {
                    String notFoundMsg = plugin.getConfig().getString("messages.item-not-found", "&cItem '{id}' not found.").replace("{id}", itemId);
                    player.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', notFoundMsg));
                    return true;
                }

                player.getInventory().addItem(item);
                String givenMsg = plugin.getConfig().getString("messages.item-given", "&aReceived item '{id}'.").replace("{id}", itemId);
                player.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', givenMsg));
                break;

            default:
                sender.sendMessage(prefix + "&cUnknown subcommand. Use /dd help for a list of commands.");
                break;
        }
        return true;
    }
}
