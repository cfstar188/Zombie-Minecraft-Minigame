package me.cfstar188.zombiegame.kits;

import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class Kit {

    private final String kitName;
    private final ItemStack representativeItem;
    private final ArrayList<ItemStack> items;
    private final ArrayList<ItemStack> armor;

    public Kit(String kitName, ItemStack representativeItem, ArrayList<ItemStack> items, ArrayList<ItemStack> armor) {
        this.kitName = kitName;
        this.representativeItem = representativeItem;
        this.items = items;
        this.armor = armor;
    }

    public String getName() {
        return kitName;
    }

    public ItemStack getRepresentativeItem() {
        return representativeItem;
    }

    public ArrayList<ItemStack> getItems() {
        return items;
    }

    public ArrayList<ItemStack> getArmor() {
        return armor;
    }

}
