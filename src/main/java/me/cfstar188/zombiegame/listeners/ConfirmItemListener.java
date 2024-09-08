package me.cfstar188.zombiegame.listeners;

import me.cfstar188.zombiegame.configs.KitConfig;
import me.cfstar188.zombiegame.configs.ShopConfig;
import me.cfstar188.zombiegame.databases.KitCooldownDatabase;
import me.cfstar188.zombiegame.gui.DisplayKitGUI;
import me.cfstar188.zombiegame.gui.DisplayShopCategoryGUI;
import me.cfstar188.zombiegame.kits.Kit;
import me.cfstar188.zombiegame.kits.ShopCategory;
import me.cfstar188.zombiegame.misc.FormatTime;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.sql.SQLException;
import java.util.HashMap;

/*
Listener for the GUI that confirms whether a player will receive a certain kit
*/
public class ConfirmItemListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event) throws SQLException {

        String inventoryName = event.getView().getTitle();
        HashMap<String, ShopCategory> nameToCategory = ShopConfig.getNameToCategory();
        String categoryName = extractCategoryName(inventoryName);

        if (nameToCategory.containsKey(categoryName) && event.getCurrentItem() != null) {

            // ensures items cannot be placed into player's main inventory
            event.setCancelled(true);

            Player player = (Player) event.getWhoClicked();
            ShopCategory category = nameToCategory.get(categoryName);

            Material material = event.getCurrentItem().getType();

            switch (material) {
                case REDSTONE_BLOCK:
                    new DisplayShopCategoryGUI(player, category);
                    break;
                case EMERALD_BLOCK:
                    String itemName = extractItemName(inventoryName);

                    // remove player money, give player item
                    category.buyItem(player, itemName);

                    player.closeInventory();
                    player.sendMessage(String.format("§aYou purchased %s!", itemName));
            }

        }

    }

    // extract the category from the inventory name
    private String extractCategoryName(String name) {
        if (name.length() <= 13) {
            // If input length is less than or equal to 13, return an empty string
            return "";
        }

        // Remove the first 13 characters
        String trimmed = name.substring(13);

        // Find the position of " (" substring
        int index = trimmed.indexOf(" (");
        if (index != -1) {
            // Return the substring up to the found index
            return trimmed.substring(0, index);
        }

        // If " (" is not found, return the trimmed string as is
        return trimmed;
    }

    // extract the item from the inventory name
    private String extractItemName(String name) {
        // Ensure the string ends with "(*" format
        if (name == null || !name.endsWith(")") || !name.contains("(")) {
            throw new IllegalArgumentException("Input string must end with '(*' format");
        }

        // Find the position of the last "("
        int startIndex = name.lastIndexOf('(');
        if (startIndex == -1 || startIndex == name.length() - 1) {
            throw new IllegalArgumentException("Invalid format. No content found after '('");
        }

        // Extract the substring between "(" and ")"
        return name.substring(startIndex + 1, name.length() - 1);
    }
}
