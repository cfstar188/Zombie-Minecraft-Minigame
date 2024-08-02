package me.cfstar188.zombiegame.builders;

import me.cfstar188.zombiegame.kits.Kit;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;

/*
Pertains to the builder design pattern
*/
public class KitBuilder {

    private String name;
    private int slot;
    private ItemStack representativeItem;
    private ArrayList<ItemStack> items = new ArrayList<>();
    private HashMap<String, ItemStack> armor = new HashMap<>();
    private double cooldown;

    public KitBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public KitBuilder setSlot(int slot) {
        this.slot = slot;
        return this;
    }

    public KitBuilder setRepresentativeItem(ItemStack representativeItem) {
        this.representativeItem = representativeItem;
        return this;
    }

    public KitBuilder setItems(ArrayList<ItemStack> items) {
        this.items = items;
        return this;
    }

    public KitBuilder setArmor(HashMap<String, ItemStack> armor) {
        this.armor = armor;
        return this;
    }

    public KitBuilder setCooldown(double cooldown) {
        this.cooldown = cooldown;
        return this;
    }

    public Kit build() {
        return new Kit(name, slot, representativeItem, items, armor, cooldown);
    }
}
