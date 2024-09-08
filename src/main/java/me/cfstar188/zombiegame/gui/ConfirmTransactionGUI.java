package me.cfstar188.zombiegame.gui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

/*
The confirmation GUI for a kit
*/
public class ConfirmTransactionGUI {

    public ConfirmTransactionGUI(Player player, String name) {

        Inventory inventory = Bukkit.createInventory(null, 45, "Confirm " + name);

        // setting back and confirm buttons
        SetButtons.setMultipleBackButtons(inventory);
        SetButtons.setMultipleConfirmButtons(inventory);

        player.openInventory(inventory);
    }

}
