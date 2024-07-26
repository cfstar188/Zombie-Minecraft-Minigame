package me.cfstar188.zombiegame.kits;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;

/*
Class for a single kit
*/
public class Kit {

    private final String name;
    private final int slot;
    private final ItemStack representativeItem;
    private final ArrayList<ItemStack> items;
    private final HashMap<String, ItemStack> armor;

    public Kit(String name, int slot, ItemStack representativeItem, ArrayList<ItemStack> items, HashMap<String, ItemStack> armor) {
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

    public HashMap<String, ItemStack> getArmor() {
        return armor;
    }

    public void giveKit(Player player) {
        giveItems(player);
        giveArmor(player);
    }

    private void giveItems(Player player) {
        ItemStack[] itemsArray = new ItemStack[items.size()];
        itemsArray = items.toArray(itemsArray);
        player.getInventory().setContents(itemsArray);
    }

    private void giveArmor(Player player) {
        player.getInventory().setHelmet(armor.get("helmet"));
        player.getInventory().setChestplate(armor.get("chestplate"));
        player.getInventory().setLeggings(armor.get("leggings"));
        player.getInventory().setBoots(armor.get("boots"));
    }

//    private Inventory getInventory() {
//        return this.inventory;
//    }

}
