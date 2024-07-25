package me.cfstar188.zombiegame.listeners;

import me.cfstar188.zombiegame.configs.MainKitConfig;
import me.cfstar188.zombiegame.gui.MainKitGUI;
import me.cfstar188.zombiegame.kits.Kit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class ConfirmKitListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event) {

        String inventoryName = parseInventoryName(event.getView().getTitle());

        if (MainKitConfig.getKitNames().contains(inventoryName) && event.getCurrentItem() != null) {

            // ensures items cannot be placed into player's main inventory
            event.setCancelled(true);

            Player player = (Player) event.getWhoClicked();
            Material material = event.getCurrentItem().getType();

            switch (material) {
                case REDSTONE_BLOCK:
                    new MainKitGUI(player);
                    break;
                case EMERALD_BLOCK:
                    player.closeInventory();
                    player.getInventory().clear();
                    Kit kit = MainKitConfig.getNameToKit().get(inventoryName);
                    kit.giveKit(player);
            }

        }

    }

    // remove the "Confirm " text at the beginning of the inventory name
    private String parseInventoryName(String name) {
        if (name.length() > 8)
            return name.substring(8);
        return "";
    }
}
