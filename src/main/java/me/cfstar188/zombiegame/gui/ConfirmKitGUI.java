package me.cfstar188.zombiegame.gui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;

/*
The confirmation GUI for a kit
*/
public class ConfirmKitGUI {

    public ConfirmKitGUI(Player player, String kitName) {

        Inventory inventory = Bukkit.createInventory(null, 45, "Confirm " + kitName);

        // setting back and confirm buttons
        SetButtons.setMultipleBackButtons(inventory);
        SetButtons.setMultipleConfirmButtons(inventory);

        player.openInventory(inventory);
    }

}
