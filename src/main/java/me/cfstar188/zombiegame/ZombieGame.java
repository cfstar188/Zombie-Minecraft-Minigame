package me.cfstar188.zombiegame;

import me.cfstar188.zombiegame.commands.KitCommand;
import me.cfstar188.zombiegame.listeners.HealingListener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class ZombieGame extends JavaPlugin {

    @Override
    public void onEnable() {

        saveDefaultConfig();

        // register events
        getServer().getPluginManager().registerEvents(new HealingListener(this), this);

        // register commands
        getCommand("kits").setExecutor(new KitCommand(this));

    }

}
