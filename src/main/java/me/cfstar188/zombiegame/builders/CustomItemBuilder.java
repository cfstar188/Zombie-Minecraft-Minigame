package me.cfstar188.zombiegame.builders;

import me.cfstar188.zombiegame.items.CustomItem;
import org.bukkit.inventory.ItemStack;

public class CustomItemBuilder {

    private String lore;
    private double damage;
    private ItemStack material;

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
    public CustomItemBuilder setMaterial(ItemStack material) {
        this.material = material;
        return this;
    }

    public CustomItem build() {
        return new CustomItem(lore, damage, material);
    }

}
