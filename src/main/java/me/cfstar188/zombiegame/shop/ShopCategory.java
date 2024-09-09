package me.cfstar188.zombiegame.kits;

import me.cfstar188.zombiegame.databases.CurrencyDatabase;
import me.deecaad.weaponmechanics.WeaponMechanicsAPI;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/*
Simple child class of Kit representing a shop category
*/
public class ShopCategory extends Kit {

    private final HashMap<String, Integer> nameToPrice;
    private final HashMap<String, ItemStack> nameToItemStack;

    public ShopCategory(String name, int slot, ItemStack representativeItem, ArrayList<ItemStack> items,
                        HashMap<String, ItemStack> armor, HashMap<String, Integer> weaponNameToQuantity,
                        double cooldown, HashMap<String, Integer> nameToPrice, HashMap<String, ItemStack> nameToItemStack) {
        super(name, slot, representativeItem, items, armor, weaponNameToQuantity, cooldown);
        this.nameToPrice = nameToPrice;
        this.nameToItemStack = nameToItemStack;
//        this.weaponDisplayNameToActualName =
    }

    public int getCost(String name) {
        System.out.println(nameToPrice);
        if (nameToPrice.containsKey(name))
            return nameToPrice.get(name);
        return -1;
    }

    public void buyItem(Player player, String itemName) throws SQLException {
        Inventory inventory = player.getInventory();
        if (nameToItemStack.containsKey(itemName)) {
            inventory.addItem(nameToItemStack.get(itemName));
        }
        else { // otherwise, itemName represents a WeaponMechanics weapon
            System.out.println(itemName);
            int quantity = super.getWeaponNameToQuantity().get(itemName);
            for (int i = 0; i < quantity; i++) {
                inventory.addItem(WeaponMechanicsAPI.generateWeapon(itemName));
            }
        }
        CurrencyDatabase.giveCurrency(player, -getCost(itemName));
    }

}

