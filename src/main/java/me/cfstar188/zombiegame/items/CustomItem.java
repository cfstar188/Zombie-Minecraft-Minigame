package me.cfstar188.zombiegame.items;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class CustomItem extends ItemStack {

    private final String lore;
    private final double damage;
    private final Material material;

    public CustomItem(String lore, double damage, Material material) {
        this.lore = lore;
        this.damage = damage;
        this.material = material;
    }

    public List<String> getLore() {
        return Arrays.asList(lore.split("\n"));
    }

    public double getDamage() {
        return this.damage;
    }

    public Material getMaterial() {
        return this.material;
    }

}
