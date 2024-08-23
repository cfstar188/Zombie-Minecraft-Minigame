package me.cfstar188.zombiegame.listeners;

import me.cfstar188.zombiegame.configs.CurrencyConfig;
import me.cfstar188.zombiegame.databases.CurrencyDatabase;
import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import java.sql.SQLException;
import java.util.HashMap;

public class MobKillListener implements Listener {

    @EventHandler
    public void onMobKill(EntityDeathEvent event) throws SQLException {

        HashMap<EntityType, Integer> entityTypeToCurrencyReceived = CurrencyConfig.getEntityTypeToCurrencyReceived();
        EntityType entityType = event.getEntityType();
        Player player = event.getEntity().getKiller();
        
        if (entityTypeToCurrencyReceived.containsKey(entityType) && player != null) {
            String uuid = player.getUniqueId().toString();
            int currency = entityTypeToCurrencyReceived.get(entityType);
            CurrencyDatabase.giveCurrency(uuid, currency);
        }

    }

}
