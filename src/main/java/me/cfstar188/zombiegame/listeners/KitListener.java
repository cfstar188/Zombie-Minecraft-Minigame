package me.cfstar188.zombiegame.listeners;

import me.cfstar188.zombiegame.ZombieGame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class KitListener implements Listener {

    private final ZombieGame plugin;

    public KitListener(ZombieGame plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {

        Player player = (Player) event.getWhoClicked();

        if (player.hasMetadata("opened") && event.getSlot() == 13) {
            player.getWorld().setStorm(true);
            player.closeInventory();
        }

    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();

        if (player.hasMetadata("opened")) {
            player.removeMetadata("opened", plugin);
        }

    }

}
