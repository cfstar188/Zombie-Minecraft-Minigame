package me.cfstar188.zombiegame.gui;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;

public class SetButtons {

    public static void setMultipleBackButtons(Inventory inventory) {
        ItemStack backButton = new ItemStack(ButtonGUI.getBackButton());
        ItemMeta backButtonMeta = backButton.getItemMeta();
        assert backButtonMeta != null;
        backButtonMeta.setDisplayName("§cCancel");
        backButton.setItemMeta(backButtonMeta);
        for (int slot = 0; slot < 18; slot++) {
            inventory.setItem(slot, backButton);
        }
    }

    public static void setMultipleConfirmButtons(Inventory inventory) {
        ItemStack confirmButton = new ItemStack(ButtonGUI.getConfirmButton());
        ItemMeta confirmButtonMeta = confirmButton.getItemMeta();
        assert confirmButtonMeta != null;
        confirmButtonMeta.setDisplayName("§eThis will clear your current inventory");
        confirmButtonMeta.setLore(Collections.singletonList(
                "§aClick to confirm"
        ));
        confirmButton.setItemMeta(confirmButtonMeta);
        for (int slot = 27; slot < 45; slot++) {
            inventory.setItem(slot, confirmButton);
        }
    }

    public static void setSingleBackButton(Inventory inventory) {
        ItemStack backButton = new ItemStack(ButtonGUI.getBackButton());
        ItemMeta backButtonMeta = backButton.getItemMeta();
        assert backButtonMeta != null;
        backButtonMeta.setDisplayName("§cBack to menu");
        backButton.setItemMeta(backButtonMeta);
        inventory.setItem(0, backButton);
    }

    public static void setSingleConfirmButton(Inventory inventory) {
        ItemStack confirmButton = new ItemStack(ButtonGUI.getConfirmButton());
        ItemMeta confirmButtonMeta = confirmButton.getItemMeta();
        assert confirmButtonMeta != null;
        confirmButtonMeta.setDisplayName("§aConfirm kit");
        confirmButton.setItemMeta(confirmButtonMeta);
        inventory.setItem(44, confirmButton);
    }

}
