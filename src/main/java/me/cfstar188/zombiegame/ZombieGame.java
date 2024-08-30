package me.cfstar188.zombiegame;

import me.cfstar188.zombiegame.commands.GiveCurrencyCommand;
import me.cfstar188.zombiegame.commands.KitCommand;
import me.cfstar188.zombiegame.commands.RemoveCurrencyCommand;
import me.cfstar188.zombiegame.configs.CustomItemConfig;
import me.cfstar188.zombiegame.configs.HealingConfig;
import me.cfstar188.zombiegame.configs.KitConfig;
import me.cfstar188.zombiegame.configs.ScoreboardConfig;
import me.cfstar188.zombiegame.databases.CurrencyDatabase;
import me.cfstar188.zombiegame.databases.KitCooldownDatabase;
import me.cfstar188.zombiegame.listeners.*;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;
import java.util.Objects;

public final class ZombieGame extends JavaPlugin {

    private KitCooldownDatabase kitCooldownDatabase;
    private CurrencyDatabase currencyDatabase;

    @Override
    public void onEnable() {

        Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("WeaponMechanics")).saveDefaultConfig();
        saveDefaultConfig();
        registerEvents();
        registerConfigs();
        registerCommands();
        registerDatabases();

    }

    @Override
    public void onDisable() {
        if (kitCooldownDatabase != null) {
            try {
                kitCooldownDatabase.closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (currencyDatabase != null) {
            try {
                currencyDatabase.closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void registerEvents() {
        PluginManager pluginManager = getServer().getPluginManager();
        pluginManager.registerEvents(new HealingListener(), this);
        pluginManager.registerEvents(new MainKitListener(), this);
        pluginManager.registerEvents(new DisplayKitListener(), this);
        pluginManager.registerEvents(new ConfirmKitListener(), this);
        pluginManager.registerEvents(new MobKillListener(), this);
        pluginManager.registerEvents(new PlayerJoinListener(), this);
        pluginManager.registerEvents(new EntityDamageListener(), this);
    }

    private void registerConfigs() {
        HealingConfig.getInstance(this);
        CustomItemConfig.getInstance(this);
        KitConfig.getInstance(this);
        ScoreboardConfig.getInstance(this);
    }

    private void registerCommands() {
        Objects.requireNonNull(getCommand("kits")).setExecutor(new KitCommand());
        Objects.requireNonNull(getCommand("givecurrency")).setExecutor(new GiveCurrencyCommand());
        Objects.requireNonNull(getCommand("removecurrency")).setExecutor(new RemoveCurrencyCommand());
    }

    private void registerDatabases() {
        try {
            // create the ZombieGame folder if not exists
            if (!getDataFolder().exists())
                getDataFolder().mkdirs();

            String overallDatabaseString = getDataFolder().getAbsolutePath() + "/ZombieGame.db";
            kitCooldownDatabase = new KitCooldownDatabase(overallDatabaseString);
            currencyDatabase = new CurrencyDatabase(overallDatabaseString);
        }
        catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to connect to ZombieGame database: " + e.getMessage());
            Bukkit.getPluginManager().disablePlugin(this);
        }
    }

}
