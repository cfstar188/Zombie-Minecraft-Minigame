package me.cfstar188.zombiegame.gui;

import me.cfstar188.zombiegame.configs.ScoreboardConfig;
import me.cfstar188.zombiegame.databases.CurrencyDatabase;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.sql.SQLException;

public class ScoreboardGUI {

    public static void displayScoreboard(Player player) throws SQLException {

        ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();
        assert scoreboardManager != null;
        Scoreboard scoreboard = scoreboardManager.getNewScoreboard();

        Objective objective = scoreboard.registerNewObjective("scoreboard", "dummy", ScoreboardConfig.getScoreboardTitle());
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        String currencyName = ScoreboardConfig.getCurrencyName();
        String currencyPrefix = ScoreboardConfig.getCurrencyPrefix();

        Score score = objective.getScore(currencyName + ": " + ChatColor.GREEN + currencyPrefix + CurrencyDatabase.getCurrency(player));
        score.setScore(1);

        player.setScoreboard(scoreboard);

    }

}
