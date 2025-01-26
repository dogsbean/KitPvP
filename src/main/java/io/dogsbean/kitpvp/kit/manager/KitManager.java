package io.dogsbean.kitpvp.kit.manager;

import io.dogsbean.kitpvp.Main;
import io.dogsbean.kitpvp.kit.Kit;
import io.dogsbean.kitpvp.player.PlayerUtils;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.*;

public class KitManager {
    private final Main plugin;
    private final Map<String, Kit> kits = new HashMap<>();
    private final Map<UUID, String> selectedKits = new HashMap<>();

    public KitManager(Main plugin) {
        this.plugin = plugin;
        setupDefaultKits();
    }

    private void setupDefaultKits() {
        ItemStack[] items = new ItemStack[36];
        items[0] = new ItemStack(Material.IRON_SWORD);

        ItemStack[] armor = new ItemStack[4];
        armor[1] = new ItemStack(Material.IRON_LEGGINGS);
        armor[2] = new ItemStack(Material.IRON_CHESTPLATE);

        List<PotionEffect> effects = new ArrayList<>();
        effects.add(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, 0));

        kits.put("berserker", new Kit("berserker", items, armor, effects));
    }

    public void applyKit(Player player, String kitName) {
        Kit kit = kits.get(kitName.toLowerCase());
        if (kit == null) return;

        PlayerUtils.resetInventory(player);

        player.getInventory().setContents(kit.getItems());
        player.getInventory().setArmorContents(kit.getArmor());

        for (PotionEffect effect : player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }

        for (PotionEffect effect : kit.getEffects()) {
            player.addPotionEffect(effect);
        }
    }

    public void selectKit(Player player, String kitName) {
        if (kits.containsKey(kitName.toLowerCase())) {
            selectedKits.put(player.getUniqueId(), kitName.toLowerCase());
        }
    }

    public String getSelectedKit(Player player) {
        return selectedKits.get(player.getUniqueId());
    }

    public Set<String> getKitNames() {
        return kits.keySet();
    }
}
