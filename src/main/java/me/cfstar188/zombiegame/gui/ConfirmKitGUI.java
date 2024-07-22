package me.cfstar188.zombiegame.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ConfirmKitGUI {

    public ConfirmKitGUI(Player player, String kitName) {

        Inventory inventory = Bukkit.createInventory(null, 45, "Confirm " + kitName); // temporary

        // set the back buttons for the inventory
        ItemStack backButton = new ItemStack(Material.REDSTONE_BLOCK);
        ItemMeta backButtonMeta = backButton.getItemMeta();
        assert backButtonMeta != null;
        backButtonMeta.setDisplayName("Click to cancel");
        backButton.setItemMeta(backButtonMeta);
        for (int slot = 0; slot < 18; slot++) {
            inventory.setItem(slot, backButton);
        }

        // set the confirm buttons for the inventory
        ItemStack confirmButton = new ItemStack(Material.EMERALD_BLOCK);
        ItemMeta confirmButtonMeta = backButton.getItemMeta();
        assert confirmButtonMeta != null;
        confirmButtonMeta.setDisplayName("Click to confirm");
        confirmButton.setItemMeta(confirmButtonMeta);
        for (int slot = 27; slot < 45; slot++) {
            inventory.setItem(slot, confirmButton);
        }

        player.openInventory(inventory);
    }
}
