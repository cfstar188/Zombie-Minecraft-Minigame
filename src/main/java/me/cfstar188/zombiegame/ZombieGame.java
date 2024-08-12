package me.cfstar188.zombiegame;

import me.cfstar188.zombiegame.commands.KitCommand;
import me.cfstar188.zombiegame.configs.HealingConfig;
import me.cfstar188.zombiegame.configs.KitConfig;
import me.cfstar188.zombiegame.databases.KitCooldownDatabase;
import me.cfstar188.zombiegame.listeners.ConfirmKitListener;
import me.cfstar188.zombiegame.listeners.DisplayKitListener;
import me.cfstar188.zombiegame.listeners.HealingListener;
import me.cfstar188.zombiegame.listeners.MainKitListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;
import java.util.Objects;

public final class ZombieGame extends JavaPlugin {

    private KitCooldownDatabase kitCooldownDatabase;

    @Override
    public void onEnable() {

        Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("WeaponMechanics")).saveDefaultConfig();
        saveDefaultConfig();
        registerEvents();
        registerConfigs();
        registerCommands();
        registerKitCooldownDatabase();

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
    }

    private void registerEvents() {
        PluginManager pluginManager = getServer().getPluginManager();
        pluginManager.registerEvents(new HealingListener(), this);
        pluginManager.registerEvents(new MainKitListener(), this);
        pluginManager.registerEvents(new DisplayKitListener(), this);
        pluginManager.registerEvents(new ConfirmKitListener(), this);
    }

    private void registerConfigs() {
        HealingConfig.getInstance(this);
        KitConfig.getInstance(this);
    }

    private void registerCommands() {
        Objects.requireNonNull(getCommand("kits")).setExecutor(new KitCommand());
    }

    private void registerKitCooldownDatabase() {
        try {
            // create the ZombieGame folder if not exists
            if (!getDataFolder().exists())
                getDataFolder().mkdirs();

            kitCooldownDatabase = new KitCooldownDatabase(getDataFolder().getAbsolutePath() + "/ZombieGame.db");
        }
        catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to connect to ZombieGame database: " + e.getMessage());
            Bukkit.getPluginManager().disablePlugin(this);
        }
    }

}
