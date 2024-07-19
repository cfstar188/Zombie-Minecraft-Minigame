package me.cfstar188.zombiegame.listeners;

import me.cfstar188.zombiegame.ZombieGame;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
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

        if (event.getView().getTitle().equals("Kits") && event.getCurrentItem() != null) {

            // ensures items cannot be placed into player's main inventory
            event.setCancelled(true);

            Player player = (Player) event.getWhoClicked();

            Material material = event.getCurrentItem().getType();

            switch(material) {

                case RED_DYE:
                    player.setHealth(20);
                    player.sendMessage(ChatColor.GREEN + "You healed the player");
                    break;

                case IRON_SWORD:
                    player.setHealth(0);
                    player.sendMessage(ChatColor.GREEN + "You killed the player");
                    break;

                case ZOMBIE_HEAD:
                    player.getWorld().spawnEntity(player.getLocation(), EntityType.ZOMBIE);
                    player.sendMessage(ChatColor.GREEN + "You spawned a zombie at the player's location");
                    break;

            }

            player.closeInventory();

        }

    }

}
