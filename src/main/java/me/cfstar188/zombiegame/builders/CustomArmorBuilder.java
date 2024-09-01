package me.cfstar188.zombiegame.builders;

import me.cfstar188.zombiegame.items.CustomArmor;
import org.bukkit.Material;

public class CustomArmorBuilder {

    private String lore;
    private double armorLevel;
    private Material material;

    public CustomArmorBuilder setLore(String lore) {
        this.lore = lore;
        return this;
    }

    public CustomArmorBuilder setArmorLevel(double armorLevel) {
        this.armorLevel = armorLevel;
        return this;
    }

    // waitingTime should be in milliseconds
    public CustomArmorBuilder setMaterial(Material material) {
        this.material = material;
        return this;
    }

    public CustomArmor build() {
        return new CustomArmor(lore, armorLevel, material);
    }


}
