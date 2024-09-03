package me.cfstar188.zombiegame.kits;

import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;

/*
Simple child class of Kit representing a shop category
*/
public class ShopCategory extends Kit {

    private HashMap<String, Integer> nameToPrice;

    public ShopCategory(String name, int slot, ItemStack representativeItem, ArrayList<ItemStack> items,
                        HashMap<String, ItemStack> armor, HashMap<String, Integer> weaponNameToQuantity,
                        double cooldown, HashMap<String, Integer> nameToPrice) {
        super(name, slot, representativeItem, items, armor, weaponNameToQuantity, cooldown);
        this.nameToPrice = nameToPrice;
    }

    public int getCost(String name) {
        System.out.println(nameToPrice);
        if (nameToPrice.containsKey(name))
            return nameToPrice.get(name);
        return 0;
    }

}

