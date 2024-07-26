package me.cfstar188.zombiegame.configs;

import me.cfstar188.zombiegame.ZombieGame;
import me.cfstar188.zombiegame.builders.HealingItemBuilder;
import me.cfstar188.zombiegame.items.HealingItem;
import me.cfstar188.zombiegame.errors.CustomError;
import org.bukkit.Material;

import java.util.*;

/*
Saves the data about the healing from config.yml
*/
public class HealingConfig {

    private static HealingConfig instance; // for singleton design pattern
    private static final HashMap<Material, HealingItem> materialToStats = new HashMap<>();
    private static final HashSet<UUID> playerBars = new HashSet<>(); // does the player currently have a healing bar?
    private static ZombieGame plugin;

    private HealingConfig(ZombieGame plugin) {
        HealingConfig.plugin = Objects.requireNonNull(plugin, CustomError.getCustomError("Plugin cannot be null"));
        establishHashmap(Objects.requireNonNull(plugin.getConfig().getList("healing"), CustomError.getCustomError("Healing config cannot be null")));
    }

    // there should only ever be one instance of HealingConfig
    public static synchronized void getInstance(ZombieGame plugin) {
        if (instance == null) {
            instance = new HealingConfig(plugin);
        }
    }

    // establish materialToStats hashmap based on config.yml
    private static void establishHashmap(List<?> healings) {
        for (Object healing : healings) {

            // extract data from healing
            String materialName = (String) ((LinkedHashMap<?, ?>) healing).get("material");
            Material material = Material.valueOf(materialName.toUpperCase());
            double heartsGained = getDouble(((LinkedHashMap<?, ?>) healing).get("hearts-gained"));
            double waitingTime = getDouble(((LinkedHashMap<?, ?>) healing).get("waiting-time"));

            // put into hashmap
            materialToStats.put(material, new HealingItemBuilder()
                    .setHealthRestored(heartsGained * 2)
                    .setWaitingTime(waitingTime * 1000)
                    .build());

        }
    }

    // helper method for establishHashmap
    private static double getDouble(Object obj) {
        return ((Number) obj).doubleValue();
    }

    /*
    Getter methods
    */
    public static HashMap<Material, HealingItem> getMaterialToStats() {
        return materialToStats;
    }

    public static HashSet<UUID> getPlayerBars() {
        return playerBars;
    }

    public static ZombieGame getPlugin() {
        return plugin;
    }

}
