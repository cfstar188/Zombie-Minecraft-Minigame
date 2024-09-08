package me.cfstar188.zombiegame.errors;

import org.bukkit.ChatColor;

/*
Class associated with errors for the plugin
*/
public class CustomError {

    public static String getCustomError(String customError) {
        return ChatColor.RED + "ZOMBIE ERROR: " + customError;
    }

    public static String getInvalidMaterialError(String materialName) {
        return getCustomError(materialName + " is an invalid item");
    }

    public static String getAddToCustomItemsError(String materialName)
    {
        return getCustomError(materialName + " needs to be added to the custom-items config");
    }

    public static String getAddToCustomArmorError(String materialName)
    {
        return getCustomError(materialName + " needs to be added to the custom-armor config");
    }
}
