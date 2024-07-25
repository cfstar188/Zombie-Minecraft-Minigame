package me.cfstar188.zombiegame.errors;

import org.bukkit.ChatColor;

public class CustomError {

    public static String getCustomError(String customError) {
        String PREFIX = "ZOMBIE ERROR: ";
        return ChatColor.RED + PREFIX + customError;
    }

    public static String getInvalidMaterialError(String materialName) {
        return getCustomError(materialName + " is an invalid item");
    }

}
