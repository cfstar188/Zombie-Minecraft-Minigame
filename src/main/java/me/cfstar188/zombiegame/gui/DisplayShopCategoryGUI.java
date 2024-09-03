package me.cfstar188.zombiegame.gui;

import me.cfstar188.zombiegame.kits.Kit;
import me.cfstar188.zombiegame.kits.ShopCategory;
import me.cfstar188.zombiegame.misc.FormatTime;
import me.deecaad.weaponmechanics.WeaponMechanicsAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class DisplayShopCategoryGUI {

    public DisplayShopCategoryGUI(Player player, ShopCategory category) {

        Inventory inventory = Bukkit.createInventory(null, 45, category.getName());

        // set the back and confirm buttons for the inventory
        SetButtons.setSingleBackButton(inventory);
        SetButtons.setSingleConfirmButton(inventory);

        // set all items in the inventory
        setItems(inventory, category);

        player.openInventory(inventory);
    }

    private void setItems(Inventory inventory, ShopCategory category) {

        ArrayList<ItemStack> items = category.getItems();
        HashMap<String, Integer> weaponNameToQuantity = category.getWeaponNameToQuantity();
        HashMap<String, ItemStack> armor = category.getArmor();

        int currSlot = 18;

        for (ItemStack item : items) {
            ItemMeta itemMeta = item.getItemMeta();
            assert itemMeta != null;
            String itemName = itemMeta.getDisplayName();
            changeLore(itemMeta, category, itemName);
            item.setItemMeta(itemMeta);
            inventory.setItem(currSlot, item);
            currSlot++;
        }

        for (Map.Entry<String, Integer> entry : weaponNameToQuantity.entrySet()) {
            String weaponName = entry.getKey();
            int quantity = entry.getValue();
            for (int i = 0; i < quantity; i++) {
                ItemStack weapon = WeaponMechanicsAPI.generateWeapon(weaponName);
                ItemMeta itemMeta = weapon.getItemMeta();
                assert itemMeta != null;
                changeLore(itemMeta, category, weaponName);
                weapon.setItemMeta(itemMeta);
                inventory.setItem(currSlot, weapon);
                currSlot++;
            }
        }

        for (ItemStack armorPiece : armor.values()) {
            ItemMeta itemMeta = armorPiece.getItemMeta();
            assert itemMeta != null;
            changeLore(itemMeta, category, itemMeta.getDisplayName());
            armorPiece.setItemMeta(itemMeta);
            inventory.setItem(currSlot, armorPiece);
            currSlot++;
        }

    }

    private void changeLore(ItemMeta itemMeta, ShopCategory category, String itemName) {
        List<String> loreArray = Collections.singletonList("§cCost: §4" + category.getCost(itemName));
        itemMeta.setLore(loreArray);
    }



}
