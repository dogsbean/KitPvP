package io.dogsbean.kitpvp.player;

import io.dogsbean.kitpvp.Main;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;

public class PlayerUtils {
    public static final long RESET_DELAY_TICKS = 2L;

    public static void resetInventoryDelayed(Player player) {
        Runnable task = () -> resetInventory(player);
        Bukkit.getScheduler().runTaskLater(Main.getInstance(), task, RESET_DELAY_TICKS);
    }

    public static void resetInventory(Player player) {
        player.setHealth(player.getMaxHealth());
        player.setFallDistance(0F);
        player.setFoodLevel(20);
        player.setSaturation(10F);
        player.setLevel(0);
        player.setExp(0F);

        player.getInventory().clear();
        player.getInventory().setArmorContents(null);

        player.setFireTicks(0);

        for (PotionEffect potionEffect : player.getActivePotionEffects()) {
            player.removePotionEffect(potionEffect.getType());
        }

        Bukkit.getScheduler().runTaskLater(Main.getInstance(), player::updateInventory, 1L);
    }
}
