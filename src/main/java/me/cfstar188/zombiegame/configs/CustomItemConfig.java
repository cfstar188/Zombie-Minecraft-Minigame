package me.cfstar188.zombiegame.configs;

import me.cfstar188.zombiegame.ZombieGame;
import me.cfstar188.zombiegame.builders.CustomItemBuilder;
import me.cfstar188.zombiegame.errors.CustomError;
import me.cfstar188.zombiegame.items.CustomItem;
import me.cfstar188.zombiegame.misc.FormatNum;
import org.bukkit.Material;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;

/*
Saves the data about custom items from config.yml
*/
public class CustomItemConfig {

    private ZombieGame plugin;
    public static CustomItemConfig instance;
    private static HashMap<String, CustomItem> nameToCustomItem = new HashMap<>();

    private CustomItemConfig(ZombieGame plugin) {
        this.plugin = Objects.requireNonNull(plugin, CustomError.getCustomError("Plugin cannot be null"));
        establishHashmap(Objects.requireNonNull(plugin.getConfig().getList("custom-items"), CustomError.getCustomError("CustomItem config cannot be null")));
    }

    // there should only ever be one instance of HealingConfig
    public static synchronized void getInstance(ZombieGame plugin) {
        if (instance == null) {
            instance = new CustomItemConfig(plugin);
        }
    }

    // establish the nameToCustomItem hashmap
    private static void establishHashmap(List<?> customItems) {
        for (Object customItem : customItems) {

            // extract data from healing
            String name = ((String) ((LinkedHashMap<?, ?>) customItem).get("name")).toUpperCase();
            String lore = (String) ((LinkedHashMap<?, ?>) customItem).get("lore");
            double damage = FormatNum.getDouble(((LinkedHashMap<?, ?>) customItem).get("damage"));
            String materialString = ((String) ((LinkedHashMap<?, ?>) customItem).get("material")).toUpperCase();
            Material material = Material.valueOf(materialString);

            // put into hashmap
            nameToCustomItem.put(name, new CustomItemBuilder()
                    .setLore(lore)
                    .setDamage(damage * 2)
                    .setMaterial(material)
                    .build());

        }
    }

    public static boolean contains(String customItem) {
        return nameToCustomItem.containsKey(customItem);
    }

    public static CustomItem getCustomItem(String customItem) {
        return nameToCustomItem.get(customItem);
    }

}
