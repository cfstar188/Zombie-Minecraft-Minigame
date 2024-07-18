package me.cfstar188.zombiegame;

import me.cfstar188.zombiegame.commands.KitCommand;
import me.cfstar188.zombiegame.listeners.HealingListener;
import me.cfstar188.zombiegame.listeners.KitListener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class ZombieGame extends JavaPlugin {

    @Override
    public void onEnable() {

        saveDefaultConfig();

        // register events
        getServer().getPluginManager().registerEvents(new HealingListener(this), this);
        getServer().getPluginManager().registerEvents(new KitListener(this), this);

        // register commands
        Objects.requireNonNull(getCommand("kits")).setExecutor(new KitCommand(this));

    }

}
