package me.cfstar188.zombiegame.listeners;

import me.cfstar188.zombiegame.configs.ScoreboardConfig;
import me.cfstar188.zombiegame.databases.CurrencyDatabase;
import me.cfstar188.zombiegame.gui.ScoreboardGUI;
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

        HashMap<EntityType, Integer> entityTypeToCurrencyReceived = ScoreboardConfig.getEntityTypeToCurrencyReceived();
        EntityType entityType = event.getEntityType();
        Player player = event.getEntity().getKiller();
        
        if (entityTypeToCurrencyReceived.containsKey(entityType) && player != null) {
            String uuid = player.getUniqueId().toString();

            // updating currency database
            int currencyToGive = entityTypeToCurrencyReceived.get(entityType);
            CurrencyDatabase.giveCurrency(uuid, currencyToGive);

            // updating currency scoreboard
            ScoreboardGUI.displayScoreboard(player);
        }

    }

}
