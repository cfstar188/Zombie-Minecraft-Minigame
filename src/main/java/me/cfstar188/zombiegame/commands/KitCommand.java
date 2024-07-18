package me.cfstar188.zombiegame.commands;

import me.cfstar188.zombiegame.ZombieGame;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;

public class KitCommand implements CommandExecutor {

    private final ZombieGame plugin;

    public KitCommand(ZombieGame plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can access kits");
            return true;
        }

        Player player = (Player) sender;

        Inventory inventory = Bukkit.createInventory(player, 9 * 3, "Kits");

        ItemStack getDiamondButton = new ItemStack(Material.DIAMOND);
        ItemMeta diamondMeta = getDiamondButton.getItemMeta();
        diamondMeta.setDisplayName(ChatColor.AQUA + "Get Diamond");
        getDiamondButton.setItemMeta(diamondMeta);

        inventory.setItem(11, getDiamondButton);

        player.openInventory(inventory);
        player.setMetadata("opened", new FixedMetadataValue(plugin, inventory));

        return true;
        
    }
}
