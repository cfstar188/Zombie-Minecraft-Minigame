package me.cfstar188.zombiegame.items;

import org.bukkit.Material;

import java.util.ArrayList;
import java.util.Arrays;

public class CustomItem {

    private final String lore;
    private final double damage;
    private final Material material;

    public CustomItem(String lore, double damage, Material material) {
        this.lore = lore;
        this.damage = damage;
        this.material = material;
    }

    public ArrayList<String> getLore() {
        return new ArrayList<>(Arrays.asList(lore.split("\n")));
    }

    public double getDamage() {
        return this.damage;
    }

    public Material getMaterial() {
        return this.material;
    }

}
