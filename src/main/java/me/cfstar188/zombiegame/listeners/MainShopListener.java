package me.cfstar188.zombiegame.listeners;

import me.cfstar188.zombiegame.configs.KitConfig;
import me.cfstar188.zombiegame.configs.ShopConfig;
import me.cfstar188.zombiegame.gui.DisplayKitGUI;
import me.cfstar188.zombiegame.gui.DisplayShopCategoryGUI;
import me.cfstar188.zombiegame.kits.Kit;
import me.cfstar188.zombiegame.kits.ShopCategory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Objects;

public class MainShopListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event) {

        if (event.getView().getTitle().equals("Shop") && event.getCurrentItem() != null) {

            // ensures items cannot be placed into player's main inventory
            event.setCancelled(true);

            Player player = (Player) event.getWhoClicked();

            // what kit did the player select
            String categoryName = Objects.requireNonNull(event.getCurrentItem().getItemMeta()).getDisplayName().substring(4);
            ShopCategory selectedCategory = ShopConfig.getNameToCategory().get(categoryName);

            new DisplayShopCategoryGUI(player, selectedCategory);

        }
    }
}
