package me.cfstar188.zombiegame.gui;

import me.cfstar188.zombiegame.kits.Kit;
import me.deecaad.weaponmechanics.WeaponMechanicsAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

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
        SetButtons.setSingleBackButton(inventory);
        SetButtons.setSingleConfirmButton(inventory);

        // set all items in the inventory
        setItems(inventory, kit);

        player.openInventory(inventory);
    }

    void setItems(Inventory inventory, Kit kit) {

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
