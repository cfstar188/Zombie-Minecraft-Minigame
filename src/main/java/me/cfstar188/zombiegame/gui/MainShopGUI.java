package me.cfstar188.zombiegame.gui;

import me.cfstar188.zombiegame.configs.KitConfig;
import me.cfstar188.zombiegame.configs.ShopConfig;
import me.cfstar188.zombiegame.kits.Kit;
import me.cfstar188.zombiegame.kits.ShopCategory;
import me.cfstar188.zombiegame.misc.FormatTime;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainShopGUI {

    public MainShopGUI(Player player) {

        Inventory inventory = Bukkit.createInventory(null, ShopConfig.getShopGUISize(), "Shop");
        HashMap<String, ShopCategory> nameToCategory = ShopConfig.getNameToCategory();

        // loop through each of the kits, giving each a spot in the KitGUI
        for (Map.Entry<String, ShopCategory> entry : nameToCategory.entrySet()) {

            ShopCategory category = entry.getValue();

            ItemStack representativeItem = category.getRepresentativeItem();

            // change name of representative item to kit name
            changeName(representativeItem, category);

            // change lore of representative item to list the contents of the kit
            // changeLore(representativeItem);

            inventory.setItem(category.getSlot(), representativeItem);
        }

        player.openInventory(inventory);

    }

    private void changeName(ItemStack representativeItem, ShopCategory category) {

        ItemMeta representativeItemMeta = representativeItem.getItemMeta();
        assert representativeItemMeta != null;
        representativeItemMeta.setDisplayName("§7§l" + category.getName());
        representativeItem.setItemMeta(representativeItemMeta);

    }

//    private void changeLore(ItemMeta representativeItemMeta, ShopCategory category) {
//        int cost = category.getCost();
//        List<String> loreArray = Collections.singletonList("§cPrice: §4" + cost);
//        representativeItemMeta.setLore(loreArray);
//    }
}
