package me.cfstar188.zombiegame.gui;

import me.cfstar188.zombiegame.kits.Kit;
import me.cfstar188.zombiegame.listeners.KitListener;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.Map;

public class MainKitGUI {

    public MainKitGUI(Player player) {

        Inventory inventory = Bukkit.createInventory(null, KitListener.getKitGUISize(), "Kits");
        HashMap<Integer, Kit> slotToKit = KitListener.getSlotToKit();

        // loop through each of the kits, giving each a spot in the KitGUI
        for (Map.Entry<Integer, Kit> entry : slotToKit.entrySet()) {

            int slot = entry.getKey();
            Kit kit = entry.getValue();

            // change name of representative item to kit name
            ItemStack representativeItem = kit.getRepresentativeItem();
            ItemMeta representativeItemMeta = representativeItem.getItemMeta();
            assert representativeItemMeta != null;
            representativeItemMeta.setDisplayName(kit.getName());
            representativeItem.setItemMeta(representativeItemMeta);

            inventory.setItem(slot, representativeItem);
        }

        player.openInventory(inventory);

    }
}
