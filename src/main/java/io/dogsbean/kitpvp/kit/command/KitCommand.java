package io.dogsbean.kitpvp.kit.command;

import io.dogsbean.kitpvp.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class KitCommand implements CommandExecutor {
    private final Main plugin;

    public KitCommand(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cThis command can only be used by players!");
            return true;
        }

        Player player = (Player) sender;

        if (args.length == 0) {
            sendHelp(player);
            return true;
        }

        String subCommand = args[0].toLowerCase();

        switch (subCommand) {
            case "list":
                listKits(player);
                break;
            case "select":
                if (args.length < 2) {
                    player.sendMessage("§cUsage: /kit select <kitname>");
                    return true;
                }
                selectKit(player, args[1]);
                break;
            default:
                sendHelp(player);
                break;
        }

        return true;
    }

    private void sendHelp(Player player) {
        player.sendMessage("§6=== FFA Kit Commands ===");
        player.sendMessage("§f/kit list §7- List all available kits");
        player.sendMessage("§f/kit select <kitname> §7- Select a kit");
    }

    private void listKits(Player player) {
        player.sendMessage("§6=== Available Kits ===");
        for (String kitName : plugin.getKitManager().getKitNames()) {
            player.sendMessage("§7- §f" + kitName);
        }
    }

    private void selectKit(Player player, String kitName) {
        if (!plugin.getRegionManager().isInSpawn(player.getLocation())) {
            player.sendMessage("§cYou can only select kits in spawn!");
            return;
        }

        plugin.getKitManager().selectKit(player, kitName);
        player.sendMessage("§aSelected kit: " + kitName);
    }
}
