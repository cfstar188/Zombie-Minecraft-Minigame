package me.cfstar188.zombiegame.gui;

import me.cfstar188.zombiegame.kits.Kit;
import me.deecaad.weaponmechanics.WeaponMechanicsAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/*
The GUI to display the contents of a kit
*/
public class DisplayKitGUI {

    public DisplayKitGUI(Player player, Kit kit) {

        Inventory inventory = Bukkit.createInventory(null, 45, kit.getName());

        // set the back and confirm buttons for the inventory
        setBackButton(inventory);
        setConfirmButton(inventory);

        // set all items in the inventory
        setItems(inventory, kit);

        player.openInventory(inventory);
    }

    private void setBackButton(Inventory inventory) {
        ItemStack backButton = new ItemStack(ButtonGUI.getBackButton());
        ItemMeta backButtonMeta = backButton.getItemMeta();
        assert backButtonMeta != null;
        backButtonMeta.setDisplayName("§cBack to menu");
        backButton.setItemMeta(backButtonMeta);
        inventory.setItem(0, backButton);
    }

    private void setConfirmButton(Inventory inventory) {
        ItemStack confirmButton = new ItemStack(ButtonGUI.getConfirmButton());
        ItemMeta confirmButtonMeta = confirmButton.getItemMeta();
        assert confirmButtonMeta != null;
        confirmButtonMeta.setDisplayName("§aConfirm kit");
        confirmButton.setItemMeta(confirmButtonMeta);
        inventory.setItem(44, confirmButton);
    }

    private void setItems(Inventory inventory, Kit kit) {

        ArrayList<ItemStack> items = kit.getItems();
        HashMap<String, Integer> weaponNameToQuantity = kit.getWeaponNameToQuantity();
        HashMap<String, ItemStack> armor = kit.getArmor();

        int currSlot = 18;

        for (ItemStack item : items) {
            inventory.setItem(currSlot, item);
            currSlot++;
        }

        for (Map.Entry<String, Integer> entry : weaponNameToQuantity.entrySet()) {
            String weaponName = entry.getKey();
            int quantity = entry.getValue();
            for (int i = 0; i < quantity; i++) {
                inventory.setItem(currSlot, WeaponMechanicsAPI.generateWeapon(weaponName));
                currSlot++;
            }
        }

        for (ItemStack armorPiece : armor.values()) {
            inventory.setItem(currSlot, armorPiece);
            currSlot++;
        }

    }
}
