package me.cfstar188.zombiegame.listeners;

import me.cfstar188.zombiegame.configs.MainKitConfig;
import me.cfstar188.zombiegame.gui.ConfirmKitGUI;
import me.cfstar188.zombiegame.gui.MainKitGUI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class DisplayKitListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event) {

        String inventoryName = event.getView().getTitle();

        if (MainKitConfig.getKitNames().contains(inventoryName) && event.getCurrentItem() != null) {

            // ensures items cannot be placed into player's main inventory
            event.setCancelled(true);

            Player player = (Player) event.getWhoClicked();
            int slot = event.getSlot();

            switch (slot) {
                case 0:
                    new MainKitGUI(player);
                    break;
                case 44:
                    new ConfirmKitGUI(player, inventoryName);
            }
        }
    }

}
