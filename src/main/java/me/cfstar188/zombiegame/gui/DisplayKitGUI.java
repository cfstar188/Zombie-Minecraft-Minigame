package me.cfstar188.zombiegame.gui;

import me.cfstar188.zombiegame.kits.Kit;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;

public class DisplayKitGUI {

    public DisplayKitGUI(Player player, Kit kit) {

        Inventory inventory = Bukkit.createInventory(null, 45, kit.getName());

        // set the back button for the inventory
        ItemStack backButton = new ItemStack(ButtonGUI.getBackButton());
        ItemMeta backButtonMeta = backButton.getItemMeta();
        assert backButtonMeta != null;
        backButtonMeta.setDisplayName("§cBack to menu");
        backButton.setItemMeta(backButtonMeta);
        inventory.setItem(0, backButton);

        // set the confirm button for the inventory
        ItemStack confirmButton = new ItemStack(ButtonGUI.getConfirmButton());
        ItemMeta confirmButtonMeta = backButton.getItemMeta();
        assert confirmButtonMeta != null;
        confirmButtonMeta.setDisplayName("§aConfirm kit");
        confirmButton.setItemMeta(confirmButtonMeta);
        inventory.setItem(44, confirmButton);

        // set all items in the inventory
        setItems(inventory, kit);

        player.openInventory(inventory);
    }

    private void setItems(Inventory inventory, Kit kit) {

        ArrayList<ItemStack> items = kit.getItems();
        HashMap<String, ItemStack> armor = kit.getArmor();

        int currSlot = 18;

        for (ItemStack item : items) {
            inventory.setItem(currSlot, item);
            currSlot++;
        }

        for (ItemStack armorPiece : armor.values()) {
            inventory.setItem(currSlot, armorPiece);
            currSlot++;
        }

    }
}
