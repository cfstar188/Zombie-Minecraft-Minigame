package me.cfstar188.zombiegame;

import me.cfstar188.zombiegame.listeners.HealingListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class ZombieGame extends JavaPlugin {

    @Override
    public void onEnable() {

        saveDefaultConfig();

        // register events
        getServer().getPluginManager().registerEvents(new HealingListener(this), this);

    }

}
