package me.cfstar188.zombiegame.listeners;

import me.cfstar188.zombiegame.ZombieGame;
import me.cfstar188.zombiegame.configs.MainKitConfig;
import me.cfstar188.zombiegame.gui.DisplayKitGUI;
import me.cfstar188.zombiegame.kits.Kit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class MainKitListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event) {

        if (event.getView().getTitle().equals("Kits") && event.getCurrentItem() != null) {

            // ensures items cannot be placed into player's main inventory
            event.setCancelled(true);

            Player player = (Player) event.getWhoClicked();

            // what kit did the player select
            String kitName = Objects.requireNonNull(event.getCurrentItem().getItemMeta()).getDisplayName();
            Kit selectedKit = MainKitConfig.getNameToKit().get(kitName);

            new DisplayKitGUI(player, selectedKit);

        }

    }

}
