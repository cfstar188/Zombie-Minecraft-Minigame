package me.cfstar188.zombiegame.commands;

import me.cfstar188.zombiegame.configs.CustomArmorConfig;
import me.cfstar188.zombiegame.configs.CustomItemConfig;
import me.cfstar188.zombiegame.items.CustomArmor;
import me.cfstar188.zombiegame.items.CustomItem;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class GiveCustomCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if (strings.length < 2 || strings.length > 3) {
            sender.sendMessage(ChatColor.RED + "This command takes 2-3 arguments");
            return false;
        }

        Player player = Bukkit.getPlayer(strings[0]);
        if (player == null) {
            sender.sendMessage(ChatColor.RED + "Player is not on the server.");
            return true;
        }

        int quantity;
        if (strings.length == 2) {
            quantity = 1;
        }
        else {
            quantity = Integer.parseInt(strings[2]);
        }

        String itemName = strings[1].replace('_', ' ');
        ItemStack itemStack;
        if (CustomItemConfig.contains(itemName)) {
            itemStack = CustomItemConfig.createCustomItemStack(itemName, quantity);
        }
        else if (CustomArmorConfig.contains(itemName)) {
            itemStack = CustomArmorConfig.createCustomItemStack(itemName, quantity);
        }
        else {
            sender.sendMessage(ChatColor.RED + String.format("%s is not a custom item.", itemName));
            return true;
        }

        player.getInventory().addItem(itemStack);
        return true;

    }
}
