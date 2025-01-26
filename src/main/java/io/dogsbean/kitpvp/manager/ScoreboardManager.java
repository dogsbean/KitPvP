package io.dogsbean.kitpvp.manager;

import io.dogsbean.kitpvp.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

public class ScoreboardManager {
    private final Main plugin;

    public ScoreboardManager(Main plugin) {
        this.plugin = plugin;
    }

    public void updateScoreboard(Player player) {
        Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective obj = board.registerNewObjective("kitpvp", "dummy");
        obj.setDisplayName("§6§lKit PvP");
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);

        boolean inCombat = plugin.getCombatManager().isInCombat(player);
        Score combatStatus = obj.getScore("§fCombat: " + (inCombat ? "§c✘" : "§a✔"));
        combatStatus.setScore(2);

        String selectedKit = plugin.getKitManager().getSelectedKit(player);
        if (selectedKit != null) {
            Score kitScore = obj.getScore("§fKit: §a" + selectedKit);
            kitScore.setScore(1);
        }

        Score emptyLine = obj.getScore("§f");
        emptyLine.setScore(0);

        player.setScoreboard(board);
    }
}
