package me.cfstar188.zombiegame.gui;

import me.cfstar188.zombiegame.kits.Kit;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class DisplayKitGUI {

    public DisplayKitGUI(Player player, Kit kit) {

        Inventory inventory = Bukkit.createInventory(null, 45, kit.getName());

        // set the back button for the inventory
        ItemStack backButton = new ItemStack(Material.REDSTONE_BLOCK);
        ItemMeta backButtonMeta = backButton.getItemMeta();
        assert backButtonMeta != null;
        backButtonMeta.setDisplayName("Back");
        backButton.setItemMeta(backButtonMeta);
        inventory.setItem(0, backButton);

        // set the confirm button for the inventory
        ItemStack confirmButton = new ItemStack(Material.EMERALD_BLOCK);
        ItemMeta confirmButtonMeta = backButton.getItemMeta();
        assert confirmButtonMeta != null;
        confirmButtonMeta.setDisplayName("Confirm");
        confirmButton.setItemMeta(confirmButtonMeta);
        inventory.setItem(44, confirmButton);

        // set all items in the inventory
        setItems(inventory, kit);

        player.openInventory(inventory);
    }

    private void setItems(Inventory inventory, Kit kit) {

        ArrayList<ItemStack> items = kit.getItems();
        ArrayList<ItemStack> armor = kit.getArmor();

        int currSlot = 18;

        for (ItemStack item : items) {
            inventory.setItem(currSlot, item);
            currSlot++;
        }

        for (ItemStack armorPiece : armor) {
            inventory.setItem(currSlot, armorPiece);
            currSlot++;
        }

    }
}
