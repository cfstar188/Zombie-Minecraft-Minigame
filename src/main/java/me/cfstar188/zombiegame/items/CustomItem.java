package me.cfstar188.zombiegame.items;

import org.bukkit.inventory.ItemStack;

public class CustomItem extends ItemStack {

    private final String lore;
    private final double damage;
    private final ItemStack material;

    public CustomItem(String lore, double damage, ItemStack material) {
        this.lore = lore;
        this.damage = damage;
        this.material = material;
    }

    public String getLore() {
        return this.lore;
    }

    public double getDamage() {
        return this.damage;
    }

    public ItemStack getMaterial() {
        return this.material;
    }

}
