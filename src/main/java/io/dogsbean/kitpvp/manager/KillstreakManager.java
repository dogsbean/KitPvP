package io.dogsbean.kitpvp.manager;

import io.dogsbean.kitpvp.Main;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class KillstreakManager {
    private final Main plugin;
    private final Map<UUID, Integer> killstreaks = new HashMap<>();

    public KillstreakManager(Main plugin) {
        this.plugin = plugin;
    }

    public void addKill(Player player) {
        UUID playerId = player.getUniqueId();
        int currentStreak = killstreaks.getOrDefault(playerId, 0) + 1;
        killstreaks.put(playerId, currentStreak);

        if (currentStreak % 5 == 0) {
            giveKillstreakReward(player, currentStreak);
        }
    }

    public void resetKillstreak(Player player) {
        killstreaks.remove(player.getUniqueId());
    }

    private void giveKillstreakReward(Player player, int streak) {
        player.sendMessage("§6Killstreak: " + streak + "!");

        switch (streak) {
            case 5:
                player.getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE));
                break;
            case 10:
                player.getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE, 2));
                break;
            case 15:
                player.getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE, 3));
                break;
        }
    }
}
