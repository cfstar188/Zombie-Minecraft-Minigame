package me.cfstar188.zombiegame.commands;

import me.cfstar188.zombiegame.databases.CurrencyDatabase;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;

public class RemoveCurrencyCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, String[] strings) {

        if (strings.length != 2) {
            sender.sendMessage(ChatColor.RED + "This command takes two arguments.");
            return true;
        }

        Player player = Bukkit.getPlayer(strings[0]);
        if (player == null) {
            sender.sendMessage(ChatColor.RED + "Player is not on the server.");
            return true;
        }
        int currency = 0;
        try {
            currency = Integer.parseInt(strings[1]);
        } catch (NumberFormatException e) {
            sender.sendMessage(ChatColor.RED + "The amount must be an integer");
        }


        try {
            CurrencyDatabase.giveCurrency(player, -currency);
        } catch (SQLException e) {
            sender.sendMessage(ChatColor.RED + "There is a problem with the currency database.");
        }
        return true;

    }
}