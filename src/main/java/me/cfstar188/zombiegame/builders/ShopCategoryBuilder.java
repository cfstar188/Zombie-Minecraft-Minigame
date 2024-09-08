package me.cfstar188.zombiegame.builders;

import me.cfstar188.zombiegame.kits.Kit;
import me.cfstar188.zombiegame.kits.ShopCategory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;

public class ShopCategoryBuilder {

    private String name;
    private int slot;
    private ItemStack representativeItem;
    private ArrayList<ItemStack> items = new ArrayList<>();
    private HashMap<String, ItemStack> armor = new HashMap<>();
    private HashMap<String, Integer> weaponNameToQuantity = new HashMap<>();
    private HashMap<String, Integer> nameToPrice;
    private HashMap<String, ItemStack> nameToItemStack;

    public ShopCategoryBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public ShopCategoryBuilder setSlot(int slot) {
        this.slot = slot;
        return this;
    }

    public ShopCategoryBuilder setRepresentativeItem(ItemStack representativeItem) {
        this.representativeItem = representativeItem;
        return this;
    }

    public ShopCategoryBuilder setItems(ArrayList<ItemStack> items) {
        this.items = items;
        return this;
    }

    public ShopCategoryBuilder setArmor(HashMap<String, ItemStack> armor) {
        this.armor = armor;
        return this;
    }

    public ShopCategoryBuilder setWeaponNameToQuantity(HashMap<String, Integer> weaponNameToQuantity) {
        this.weaponNameToQuantity = weaponNameToQuantity;
        return this;
    }

    public ShopCategoryBuilder setEntityNameToCost(HashMap<String, Integer> nameToPrice) {
        this.nameToPrice = nameToPrice;
        return this;
    }

    public ShopCategoryBuilder setEntityNameToItemStack(HashMap<String, ItemStack> nameToItemStack) {
        this.nameToItemStack = nameToItemStack;
        return this;
    }

    public ShopCategory build() {
        return new ShopCategory(name, slot, representativeItem, items, armor, weaponNameToQuantity, 0, nameToPrice, nameToItemStack);
    }

}
