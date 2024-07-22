package me.cfstar188.zombiegame.gui;

import me.cfstar188.zombiegame.kits.Kit;
import me.cfstar188.zombiegame.listeners.MainKitListener;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.Map;

public class MainKitGUI {

    public MainKitGUI(Player player) {

        Inventory inventory = Bukkit.createInventory(null, MainKitListener.getKitGUISize(), "Kits");
        HashMap<String, Kit> nameToKit = MainKitListener.getNameToKit();

        // loop through each of the kits, giving each a spot in the KitGUI
        for (Map.Entry<String, Kit> entry : nameToKit.entrySet()) {

            Kit kit = entry.getValue();

            ItemStack representativeItem = kit.getRepresentativeItem();

            // change name of representative item to kit name
            changeName(representativeItem, kit);

            // change lore of representative item to list the contents of the kit
            // changeLore(representativeItem);

            inventory.setItem(kit.getSlot(), representativeItem);
        }

        player.openInventory(inventory);

    }

    private void changeName(ItemStack representativeItem, Kit kit) {

        ItemMeta representativeItemMeta = representativeItem.getItemMeta();
        assert representativeItemMeta != null;
        representativeItemMeta.setDisplayName(kit.getName());
        representativeItem.setItemMeta(representativeItemMeta);

    }

//    private void changeLore(ItemStack representativeItem, Kit kit) {
//
//    }

}