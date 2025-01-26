package io.dogsbean.kitpvp;

import io.dogsbean.kitpvp.command.SpawnCommand;
import io.dogsbean.kitpvp.kit.command.KitCommand;
import io.dogsbean.kitpvp.kit.manager.KitManager;
import io.dogsbean.kitpvp.manager.CombatManager;
import io.dogsbean.kitpvp.manager.KillstreakManager;
import io.dogsbean.kitpvp.manager.RegionManager;
import io.dogsbean.kitpvp.player.PlayerListener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.Bukkit;

public class Main extends JavaPlugin {
    private static Main instance;
    private RegionManager regionManager;
    private KitManager kitManager;
    private CombatManager combatManager;
    private KillstreakManager killstreakManager;

    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();

        regionManager = new RegionManager(this);
        kitManager = new KitManager(this);
        combatManager = new CombatManager(this);
        killstreakManager = new KillstreakManager(this);

        getCommand("kit").setExecutor(new KitCommand(this));
        getCommand("spawn").setExecutor(new SpawnCommand(this));

        getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
        setupDefaultRegions();

        getLogger().info("KitPvP has been enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("KitPvP has been disabled!");
    }

    public static Main getInstance() {
        return instance;
    }

    public RegionManager getRegionManager() {
        return regionManager;
    }

    public KitManager getKitManager() {
        return kitManager;
    }

    public CombatManager getCombatManager() {
        return combatManager;
    }

    public KillstreakManager getKillstreakManager() {
        return killstreakManager;
    }

    private void setupDefaultRegions() {
        FileConfiguration config = getConfig();
        if (!config.contains("regions.spawn")) {
            World world = Bukkit.getWorlds().get(0);
            Location spawnLoc = world.getSpawnLocation();

            config.set("regions.spawn.world", world.getName());
            config.set("regions.spawn.x1", spawnLoc.getBlockX() - 20);
            config.set("regions.spawn.y1", spawnLoc.getBlockY() - 5);
            config.set("regions.spawn.z1", spawnLoc.getBlockZ() - 20);
            config.set("regions.spawn.x2", spawnLoc.getBlockX() + 20);
            config.set("regions.spawn.y2", spawnLoc.getBlockY() + 10);
            config.set("regions.spawn.z2", spawnLoc.getBlockZ() + 20);

            saveConfig();
        }
    }
}
