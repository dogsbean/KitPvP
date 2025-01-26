package io.dogsbean.kitpvp.manager;

import io.dogsbean.kitpvp.Main;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CombatManager {
    private final Main plugin;
    private final Map<UUID, Long> combatTags = new HashMap<>();
    private static final int COMBAT_TAG_DURATION = 30;

    public CombatManager(Main plugin) {
        this.plugin = plugin;
    }

    public void tagPlayer(Player player) {
        combatTags.put(player.getUniqueId(), System.currentTimeMillis());
        plugin.getScoreboardManager().updateScoreboard(player);

        new BukkitRunnable() {
            @Override
            public void run() {
                if (isInCombat(player)) {
                    combatTags.remove(player.getUniqueId());
                    player.sendMessage("§aYou are no longer in combat!");
                    plugin.getScoreboardManager().updateScoreboard(player);
                }
            }
        }.runTaskLater(plugin, COMBAT_TAG_DURATION * 20L);
    }

    public boolean isInCombat(Player player) {
        Long tagTime = combatTags.get(player.getUniqueId());
        if (tagTime == null) return false;

        return System.currentTimeMillis() - tagTime < COMBAT_TAG_DURATION * 1000;
    }

    public void handleCombatLogout(Player player) {
        if (isInCombat(player)) {
            player.setHealth(0.0);
            player.getInventory().clear();
            combatTags.remove(player.getUniqueId());
        }
    }
}
