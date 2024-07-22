package me.cfstar188.zombiegame.commands;

import me.cfstar188.zombiegame.ZombieGame;
import me.cfstar188.zombiegame.gui.MainKitGUI;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class KitCommand implements CommandExecutor {

    private final ZombieGame plugin;

    public KitCommand(ZombieGame plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {

        // only players should be able to access kits
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players can access kits");
            return true;
        }

        Player player = (Player) sender;
        new MainKitGUI(player);
        return true;
        
    }
}
