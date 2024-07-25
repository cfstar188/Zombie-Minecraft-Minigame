package me.cfstar188.zombiegame.configs;

import me.cfstar188.zombiegame.ZombieGame;
import me.cfstar188.zombiegame.items.HealingItem;
import org.bukkit.Material;

import java.util.*;

public class HealingConfig {

    private static final HashMap<Material, HealingItem> materialToStats = new HashMap<>();
    private static final HashSet<UUID> playerBars = new HashSet<>(); // does the player currently have a healing bar?
    private static ZombieGame plugin = null;

    public HealingConfig(ZombieGame plugin) {
        HealingConfig.plugin = plugin;
        establishHashmap(Objects.requireNonNull(plugin.getConfig().getList("healing")));
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
            materialToStats.put(material, new HealingItem(heartsGained * 2, waitingTime * 1000));

        }
    }

    // helper method for establishHashmap
    private static double getDouble(Object obj) {
        return ((Number) obj).doubleValue();
    }

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
