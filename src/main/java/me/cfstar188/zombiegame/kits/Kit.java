package me.cfstar188.zombiegame.kits;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class Kit {

    private final String name;
    private final int slot;
    private final ItemStack representativeItem;
    private final ArrayList<ItemStack> items;
    private final ArrayList<ItemStack> armor;

    public Kit(String name, int slot, ItemStack representativeItem, ArrayList<ItemStack> items, ArrayList<ItemStack> armor) {
        this.name = name;
        this.slot = slot;
        this.representativeItem = representativeItem;
        this.items = items;
        this.armor = armor;
    }

    public String getName() {
        return name;
    }

    public int getSlot() {
        return slot;
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

    public void giveKit(Player player) {

        // convert ArrayLists to normal arrays
        ItemStack[] itemsArray = new ItemStack[items.size()];
        itemsArray = items.toArray(itemsArray);
        ItemStack[] armorArray = new ItemStack[armor.size()];
        armorArray = armor.toArray(armorArray);

        // give player items
        player.getInventory().setContents(itemsArray);
        player.getInventory().setArmorContents(armorArray);


    }

}
