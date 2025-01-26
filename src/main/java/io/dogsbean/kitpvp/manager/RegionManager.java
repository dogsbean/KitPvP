package io.dogsbean.kitpvp.manager;

import io.dogsbean.kitpvp.Main;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

public class RegionManager {
    private final Main plugin;

    public RegionManager(Main plugin) {
        this.plugin = plugin;
    }

    public boolean isInSpawn(Location location) {
        FileConfiguration config = plugin.getConfig();
        String world = config.getString("regions.spawn.world");

        if (!location.getWorld().getName().equals(world)) {
            return false;
        }

        int x = location.getBlockX();
        int y = location.getBlockY();
        int z = location.getBlockZ();

        int x1 = config.getInt("regions.spawn.x1");
        int y1 = config.getInt("regions.spawn.y1");
        int z1 = config.getInt("regions.spawn.z1");
        int x2 = config.getInt("regions.spawn.x2");
        int y2 = config.getInt("regions.spawn.y2");
        int z2 = config.getInt("regions.spawn.z2");

        return x >= Math.min(x1, x2) && x <= Math.max(x1, x2)
                && y >= Math.min(y1, y2) && y <= Math.max(y1, y2)
                && z >= Math.min(z1, z2) && z <= Math.max(z1, z2);
    }
}
