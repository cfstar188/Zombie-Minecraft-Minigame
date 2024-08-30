package me.cfstar188.zombiegame.listeners;

import me.cfstar188.zombiegame.configs.CustomItemConfig;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.Objects;

public class EntityDamageListener implements Listener {

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {

        if (event.getDamager() instanceof Player) {
            Player player = (Player) event.getDamager();
            String weaponName = Objects.requireNonNull(player.getInventory().getItemInMainHand().getItemMeta()).getDisplayName();

            if (CustomItemConfig.contains(weaponName)) {
                event.setDamage(CustomItemConfig.getCustomItem(weaponName).getDamage());
            }

        }
    }
}
