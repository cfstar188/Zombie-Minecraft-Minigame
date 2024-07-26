package me.cfstar188.zombiegame.listeners;

import me.cfstar188.zombiegame.configs.KitConfig;
import me.cfstar188.zombiegame.gui.DisplayKitGUI;
import me.cfstar188.zombiegame.kits.Kit;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.*;

/*
Listener for the GUI that shows the player all available kits
*/
public class MainKitListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event) {

        if (event.getView().getTitle().equals("Kits") && event.getCurrentItem() != null) {

            // ensures items cannot be placed into player's main inventory
            event.setCancelled(true);

            Player player = (Player) event.getWhoClicked();

            // what kit did the player select
            String kitName = Objects.requireNonNull(event.getCurrentItem().getItemMeta()).getDisplayName();
            Kit selectedKit = KitConfig.getNameToKit().get(kitName);

            new DisplayKitGUI(player, selectedKit);

        }

    }

}
