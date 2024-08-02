package me.cfstar188.zombiegame.listeners;

import me.cfstar188.zombiegame.builders.KitBuilder;
import me.cfstar188.zombiegame.configs.KitConfig;
import me.cfstar188.zombiegame.databases.KitCooldownDatabase;
import me.cfstar188.zombiegame.gui.DisplayKitGUI;
import me.cfstar188.zombiegame.kits.Kit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.sql.SQLException;

/*
Listener for the GUI that confirms whether a player will receive a certain kit
*/
public class ConfirmKitListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event) throws SQLException {

        String inventoryName = parseInventoryName(event.getView().getTitle());

        if (KitConfig.getNameToKit().containsKey(inventoryName) && event.getCurrentItem() != null) {

            // ensures items cannot be placed into player's main inventory
            event.setCancelled(true);

            Player player = (Player) event.getWhoClicked();
            Kit kit = KitConfig.getNameToKit().get(inventoryName);

            Material material = event.getCurrentItem().getType();

            switch (material) {
                case REDSTONE_BLOCK:
                    new DisplayKitGUI(player, kit);
                    break;
                case EMERALD_BLOCK:

                    String uuid = player.getUniqueId().toString();
                    String kitName = kit.getName();

                    String playerMessage;
                    double hoursPassed = KitCooldownDatabase.hoursPassed(uuid, kitName);
                    double cooldown = kit.getCooldown();

                    if (hoursPassed >= cooldown) {
                        player.getInventory().clear();
                        kit.giveKit(player);
                        KitCooldownDatabase.insertTuple(uuid, kitName);
                        playerMessage = String.format("§aYou selected %s!", kitName);
                    }
                    else {
                        playerMessage = String.format("§cYou need to wait %f hours before selecting %s!", cooldown, kitName);
                    }

                    player.closeInventory();
                    player.sendMessage(playerMessage);
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
