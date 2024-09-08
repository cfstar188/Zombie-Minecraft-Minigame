package me.cfstar188.zombiegame.listeners;

import me.cfstar188.zombiegame.configs.ShopConfig;
import me.cfstar188.zombiegame.databases.CurrencyDatabase;
import me.cfstar188.zombiegame.gui.ConfirmTransactionGUI;
import me.cfstar188.zombiegame.gui.MainKitGUI;
import me.cfstar188.zombiegame.gui.MainShopGUI;
import me.cfstar188.zombiegame.kits.ShopCategory;
import me.deecaad.weaponmechanics.WeaponMechanicsAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.sql.SQLException;
import java.util.HashMap;

public class ShopItemListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event) throws SQLException {

        String inventoryName = event.getView().getTitle();
        ItemStack currentItem = event.getCurrentItem();
        HashMap<String, ShopCategory> nameToCategory = ShopConfig.getNameToCategory();

        if (nameToCategory.containsKey(inventoryName) && currentItem != null) {

            // ensures items cannot be placed into player's main inventory
            event.setCancelled(true);

            Player player = (Player) event.getWhoClicked();
            int slot = event.getSlot();

            if (slot == 0) {
                new MainShopGUI(player);
            }
            else {
                ItemMeta meta = currentItem.getItemMeta();
                assert meta != null;
                String displayName = meta.getDisplayName();
                ShopCategory category = nameToCategory.get(inventoryName);
                int cost = category.getCost(displayName);

                if (cost == -1)
                    cost = category.getCost(WeaponMechanicsAPI.getWeaponTitle(currentItem));

                System.out.println("COST " + cost);
                System.out.println("DISPLAY NAME " + displayName);

                // player does not have enough currency for this item
                if (CurrencyDatabase.getCurrency(player) - cost < 0) {
                    player.closeInventory();
                    player.sendMessage(String.format("§cYou cannot afford %s!", displayName));
                }
                else {
                    new ConfirmTransactionGUI(player, String.format("from %s (%s)", inventoryName, displayName));
                }

            }
        }
    }
}
