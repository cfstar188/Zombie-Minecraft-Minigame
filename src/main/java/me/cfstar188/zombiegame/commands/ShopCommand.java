package me.cfstar188.zombiegame.commands;

import me.cfstar188.zombiegame.errors.CustomError;
import me.cfstar188.zombiegame.gui.MainKitGUI;
import me.cfstar188.zombiegame.gui.MainShopGUI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ShopCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        // only players should be able to use /kits
        if (!(sender instanceof Player)) {
            sender.sendMessage(CustomError.getCustomError("Only players can access the shop"));
            return true;
        }

        // open Kit GUI for the player that sent the command
        Player player = (Player) sender;
        new MainShopGUI(player);
        return true;

    }
}
