package me.cfstar188.zombiegame;

import me.cfstar188.zombiegame.commands.KitCommand;
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

        // register events
        PluginManager pluginManager = getServer().getPluginManager();
        pluginManager.registerEvents(new HealingListener(this), this);
        pluginManager.registerEvents(new MainKitListener(this), this);
        pluginManager.registerEvents(new DisplayKitListener(), this);

        // register commands
        Objects.requireNonNull(getCommand("kits")).setExecutor(new KitCommand(this));

    }

}
