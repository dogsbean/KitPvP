package io.dogsbean.kitpvp.player;

import io.dogsbean.kitpvp.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {
    private final Main plugin;

    public PlayerListener(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        plugin.getScoreboardManager().updateScoreboard(event.getPlayer());
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        boolean wasInSpawn = plugin.getRegionManager().isInSpawn(event.getFrom());
        boolean isInSpawn = plugin.getRegionManager().isInSpawn(event.getTo());

        if (wasInSpawn && !isInSpawn) {
            String selectedKit = plugin.getKitManager().getSelectedKit(player);
            if (selectedKit != null) {
                plugin.getKitManager().applyKit(player, selectedKit);
                plugin.getScoreboardManager().updateScoreboard(player);
                player.sendMessage("§cYou left spawn.");
            }
        }

        if (!wasInSpawn && isInSpawn && plugin.getCombatManager().isInCombat(player)) {
            event.setCancelled(true);
            player.sendMessage("§cYou cannot enter spawn while in combat!");
        }
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player) || !(event.getDamager() instanceof Player)) {
            return;
        }

        Player victim = (Player) event.getEntity();
        Player attacker = (Player) event.getDamager();

        if (plugin.getRegionManager().isInSpawn(victim.getLocation()) ||
                plugin.getRegionManager().isInSpawn(attacker.getLocation())) {
            event.setCancelled(true);
            return;
        }

        plugin.getCombatManager().tagPlayer(victim);
        plugin.getCombatManager().tagPlayer(attacker);

        plugin.getScoreboardManager().updateScoreboard(victim);
        plugin.getScoreboardManager().updateScoreboard(attacker);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        plugin.getCombatManager().handleCombatLogout(event.getPlayer());
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player victim = event.getEntity();
        Player killer = victim.getKiller();

        if (killer != null) {
            plugin.getKillstreakManager().addKill(killer);
        }
        plugin.getKillstreakManager().resetKillstreak(victim);

        plugin.getScoreboardManager().updateScoreboard(victim);
        if (killer != null) {
            plugin.getScoreboardManager().updateScoreboard(killer);
        }
    }
}