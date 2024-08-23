package me.cfstar188.zombiegame.configs;

import me.cfstar188.zombiegame.ZombieGame;
import me.cfstar188.zombiegame.builders.HealingItemBuilder;
import me.cfstar188.zombiegame.errors.CustomError;
import me.cfstar188.zombiegame.kits.Kit;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;

public class CurrencyConfig {

    private static CurrencyConfig instance; // for singleton design pattern
    private static final HashMap<EntityType, Integer> entityTypeToCurrencyReceived = new HashMap<>();
    private static ZombieGame plugin;

    private CurrencyConfig(ZombieGame plugin) {
        CurrencyConfig.plugin = Objects.requireNonNull(plugin, CustomError.getCustomError("Plugin cannot be null"));
        establishHashmap(Objects.requireNonNull(plugin.getConfig().getList("currency"), CustomError.getCustomError("Currency config cannot be null")));
    }

    // there should only ever be one instance of KitConfig
    public static synchronized void getInstance(ZombieGame plugin) {
        if (instance == null) {
            instance = new CurrencyConfig(plugin);
        }
    }

    // establish materialToStats hashmap based on config.yml
    private static void establishHashmap(List<?> currencyConfig) {
        for (Object item : currencyConfig) {

            // extract data from currency config
            String mobName = (String) ((LinkedHashMap<?, ?>) item).get("mob");
            int currencyReceived = (int) ((LinkedHashMap<?, ?>) item).get("currency-received");

            // put into hashmap
            entityTypeToCurrencyReceived.put(EntityType.valueOf(mobName.toUpperCase()), currencyReceived);
            System.out.println("MOB NAME TO CURRENCY RECEIVED");
            System.out.println(mobName + " " + currencyReceived);

        }
    }

    public static HashMap<EntityType, Integer> getEntityTypeToCurrencyReceived() {
        return entityTypeToCurrencyReceived;
    }
}
