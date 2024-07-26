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
        setBackButtons(inventory);
        setConfirmButtons(inventory);

        player.openInventory(inventory);
    }

    private void setBackButtons(Inventory inventory) {
        ItemStack backButton = new ItemStack(ButtonGUI.getBackButton());
        ItemMeta backButtonMeta = backButton.getItemMeta();
        assert backButtonMeta != null;
        backButtonMeta.setDisplayName("§cCancel");
        backButton.setItemMeta(backButtonMeta);
        for (int slot = 0; slot < 18; slot++) {
            inventory.setItem(slot, backButton);
        }
    }

    private void setConfirmButtons(Inventory inventory) {
        ItemStack confirmButton = new ItemStack(ButtonGUI.getConfirmButton());
        ItemMeta confirmButtonMeta = confirmButton.getItemMeta();
        assert confirmButtonMeta != null;
        confirmButtonMeta.setDisplayName("§eThis will clear your current inventory");
        confirmButtonMeta.setLore(Collections.singletonList(
                "§aClick to confirm"
        ));
        confirmButton.setItemMeta(confirmButtonMeta);
        for (int slot = 27; slot < 45; slot++) {
            inventory.setItem(slot, confirmButton);
        }
    }

}
