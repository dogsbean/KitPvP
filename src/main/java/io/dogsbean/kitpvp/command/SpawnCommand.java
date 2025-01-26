package io.dogsbean.kitpvp.command;

import io.dogsbean.kitpvp.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.Location;

public class SpawnCommand implements CommandExecutor {
    private final Main plugin;

    public SpawnCommand(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cThis command can only be used by players!");
            return true;
        }

        Player player = (Player) sender;

        if (plugin.getCombatManager().isInCombat(player)) {
            player.sendMessage("§cYou cannot teleport to spawn while in combat!");
            return true;
        }

        Location spawnLocation = player.getWorld().getSpawnLocation();
        player.teleport(spawnLocation);
        player.sendMessage("§aTeleported to spawn!");

        return true;
    }
}
