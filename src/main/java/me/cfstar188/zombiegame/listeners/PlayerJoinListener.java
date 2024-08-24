package me.cfstar188.zombiegame.listeners;

import me.cfstar188.zombiegame.configs.ScoreboardConfig;
import me.cfstar188.zombiegame.gui.ScoreboardGUI;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.sql.SQLException;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) throws SQLException {
        System.out.println(ScoreboardConfig.getDisplayPlayerCurrency());
        if (ScoreboardConfig.getDisplayPlayerCurrency()) {
            ScoreboardGUI.displayScoreboard(event.getPlayer());
        }
    }
}
