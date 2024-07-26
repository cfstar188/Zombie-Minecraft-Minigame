package me.cfstar188.zombiegame;

import me.cfstar188.zombiegame.commands.KitCommand;
import me.cfstar188.zombiegame.configs.HealingConfig;
import me.cfstar188.zombiegame.configs.KitConfig;
import me.cfstar188.zombiegame.listeners.ConfirmKitListener;
import me.cfstar188.zombiegame.listeners.DisplayKitListener;
import me.cfstar188.zombiegame.listeners.HealingListener;
import me.cfstar188.zombiegame.listeners.MainKitListener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class ZombieGame extends JavaPlugin {

    @Override
    public void onEnable() {

        saveDefaultConfig();
        registerEvents();
        registerConfigs();
        registerCommands();

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


}
