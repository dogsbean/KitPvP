package io.dogsbean.kitpvp.kit;

import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import java.util.List;

public class Kit {
    private String name;
    private ItemStack[] items;
    private ItemStack[] armor;
    private List<PotionEffect> effects;

    public Kit(String name, ItemStack[] items, ItemStack[] armor, List<PotionEffect> effects) {
        this.name = name;
        this.items = items;
        this.armor = armor;
        this.effects = effects;
    }

    public String getName() {
        return name;
    }

    public ItemStack[] getItems() {
        return items;
    }

    public ItemStack[] getArmor() {
        return armor;
    }

    public List<PotionEffect> getEffects() {
        return effects;
    }
}
