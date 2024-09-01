package me.cfstar188.zombiegame.items;

import org.bukkit.Material;

import java.util.ArrayList;
import java.util.Arrays;

public class CustomArmor{

    private final String lore;
    private final double armorLevel;
    private final Material material;

    public CustomArmor(String lore, double armorLevel, Material material) {
        this.lore = lore;
        this.armorLevel = armorLevel;
        this.material = material;
    }

    public ArrayList<String> getLore() {
        return new ArrayList<>(Arrays.asList(lore.split("\n")));
    }

    public double getArmorLevel() {
        return this.armorLevel;
    }

    public Material getMaterial() {
        return this.material;
    }

}
