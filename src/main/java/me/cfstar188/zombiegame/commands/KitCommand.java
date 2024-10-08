package me.cfstar188.zombiegame.commands;

import me.cfstar188.zombiegame.errors.CustomError;
import me.cfstar188.zombiegame.gui.MainKitGUI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class KitCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        // only players should be able to use /kits
        if (!(sender instanceof Player)) {
            sender.sendMessage(CustomError.getCustomError("Only players can access kits"));
            return true;
        }

        // open Kit GUI for the player that sent the command
        Player player = (Player) sender;
        new MainKitGUI(player);
        return true;
        
    }
}
