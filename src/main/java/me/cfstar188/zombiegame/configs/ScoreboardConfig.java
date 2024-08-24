package me.cfstar188.zombiegame.configs;

import me.cfstar188.zombiegame.ZombieGame;
import me.cfstar188.zombiegame.errors.CustomError;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;

public class ScoreboardConfig {

    private static ScoreboardConfig instance; // for singleton design pattern
    private static ZombieGame plugin;
    private static FileConfiguration config;
    private static String scoreboardTitle;
    private static boolean displayPlayerCurrency = true;
    private static String currencyName = "Money";
    private static String currencyPrefix = "$";
    private static final HashMap<EntityType, Integer> entityTypeToCurrencyReceived = new HashMap<>();


    private ScoreboardConfig(ZombieGame plugin) {
        ScoreboardConfig.plugin = Objects.requireNonNull(plugin, CustomError.getCustomError("Plugin cannot be null"));
        config = plugin.getConfig();
        scoreboardTitle = config.getString("scoreboard.scoreboard-title");
        establishCurrencyHashmap(Objects.requireNonNull(config.getList("currency-mobs"), CustomError.getCustomError("Currency config cannot be null")));
        establishCurrencyDisplay();
    }

    // there should only ever be one instance of KitConfig
    public static synchronized void getInstance(ZombieGame plugin) {
        if (instance == null) {
            instance = new ScoreboardConfig(plugin);
        }
    }

    // establish entityTypeToCurrencyReceived hashmap based on config.yml
    private static void establishCurrencyHashmap(List<?> currencyMobsConfig) {
        for (Object item : currencyMobsConfig) {

            // extract data from currency config
            String mobName = (String) ((LinkedHashMap<?, ?>) item).get("mob");
            int currencyReceived = (int) ((LinkedHashMap<?, ?>) item).get("currency-received");

            // put into hashmap
            entityTypeToCurrencyReceived.put(EntityType.valueOf(mobName.toUpperCase()), currencyReceived);

        }
    }

    private static void establishCurrencyDisplay() {
        ConfigurationSection scoreboardSection = config.getConfigurationSection("scoreboard");
        assert scoreboardSection != null;
        displayPlayerCurrency = scoreboardSection.getBoolean("display-player-currency");
        currencyName = scoreboardSection.getString("currency-name");
        currencyPrefix = scoreboardSection.getString("currency-prefix");
    }

    public static String getScoreboardTitle() {
        return scoreboardTitle;
    }

    public static String getCurrencyName() {
        return currencyName;
    }

    public static String getCurrencyPrefix() {
        return currencyPrefix;
    }

    public static HashMap<EntityType, Integer> getEntityTypeToCurrencyReceived() {
        return entityTypeToCurrencyReceived;
    }

    public static boolean getDisplayPlayerCurrency() {
        return displayPlayerCurrency;
    }

}
