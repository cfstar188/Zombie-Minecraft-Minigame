package me.cfstar188.zombiegame.builders;

import me.cfstar188.zombiegame.items.CustomArmor;
import me.cfstar188.zombiegame.items.CustomItem;
import org.bukkit.Material;

public class CustomItemBuilder {

    private String lore;
    private double damage;
    private Material material;

    public CustomItemBuilder setLore(String lore) {
        this.lore = lore;
        return this;
    }

    // waitingTime should be in milliseconds
    public CustomItemBuilder setDamage(double damage) {
        this.damage = damage;
        return this;
    }

    // waitingTime should be in milliseconds
    public CustomItemBuilder setMaterial(Material material) {
        this.material = material;
        return this;
    }

    public CustomItem build() {
        return new CustomItem(lore, damage, material);
    }

}
