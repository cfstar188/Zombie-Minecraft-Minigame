package me.cfstar188.zombiegame.gui;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.Collections;

public class KitGUI {

    public KitGUI(Player player) {

        Inventory inventory = Bukkit.createInventory(null, 27, "Kits");

        ItemStack playerHead = new ItemStack(Material.PLAYER_HEAD, 1);
        ItemMeta playerHeadMeta = playerHead.getItemMeta();
        assert playerHeadMeta != null;
        playerHeadMeta.setDisplayName(player.getDisplayName());
        playerHead.setItemMeta(playerHeadMeta);

        inventory.setItem(22, playerHead);

        ItemStack heal = new ItemStack(Material.RED_DYE, 1);
        ItemMeta healMeta = heal.getItemMeta();
        assert healMeta != null;
        healMeta.setDisplayName(ChatColor.RED + "Heal");
        healMeta.setLore(Collections.singletonList(ChatColor.WHITE + "Heal " + player.getDisplayName()));
        heal.setItemMeta(healMeta);
        inventory.setItem(11, heal);

        ItemStack kill = new ItemStack(Material.IRON_SWORD, 1);
        ItemMeta killMeta = kill.getItemMeta();
        killMeta.setDisplayName(ChatColor.BLUE + "Kill");
        killMeta.setLore(Collections.singletonList(ChatColor.WHITE + "Kill " + player.getDisplayName()));
        kill.setItemMeta(killMeta);
        inventory.setItem(13, kill);

        ItemStack zombie = new ItemStack(Material.ZOMBIE_HEAD, 1);
        ItemMeta zombieMeta = zombie.getItemMeta();
        zombieMeta.setDisplayName(ChatColor.WHITE + "Zombie");
        healMeta.setLore(Collections.singletonList(ChatColor.WHITE + "Spawn a zombie for " + player.getDisplayName()));
        zombie.setItemMeta(zombieMeta);
        inventory.setItem(15, zombie);

        player.openInventory(inventory);

    }
}
