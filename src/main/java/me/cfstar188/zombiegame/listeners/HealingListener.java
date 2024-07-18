package me.cfstar188.zombiegame.listeners;

import me.cfstar188.zombiegame.ZombieGame;
import me.cfstar188.zombiegame.items.HealingItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.HashSet;

public class HealingListener implements Listener {

    private static final HashMap<Material, HealingItem> materialToStats = new HashMap<>();
    private static final HashSet<UUID> playerBars = new HashSet<>(); // does the player currently have a healing bar?
    private static final DecimalFormat df = new DecimalFormat("#.##"); // timer will display up to 100th of a second
    private final ZombieGame plugin;

    public HealingListener(ZombieGame plugin) {
        this.plugin = plugin;
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

    @EventHandler
    public void onPlayerHeal(PlayerInteractEvent event) {

        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {

            Player player = event.getPlayer();
            PlayerInventory inventory = player.getInventory();
            Material material = inventory.getItemInMainHand().getType();
            UUID playerUUID = player.getUniqueId();

            // if player is less than full health and does not currently have a boss bar timer
            if (materialToStats.containsKey(material) && player.getHealth() < 20 && !playerBars.contains(playerUUID)) {

                playerBars.add(playerUUID);
                HealingItem healingItem = materialToStats.get(material);

                // create and display the countdown timer
                BossBar bossBar = Bukkit.createBossBar("", BarColor.RED, BarStyle.SOLID);
                bossBar.addPlayer(player);

                // runnable to decrement timer
                new BukkitRunnable() {
                    final double endTime = System.currentTimeMillis() + healingItem.getWaitingTime();

                    @Override
                    public void run() {

                        // check if the player changed the item in their hand
                        if (!inventory.getItemInMainHand().getType().equals(material)) {
                            bossBar.setTitle("§cHealing canceled");
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    bossBar.removeAll();
                                    playerBars.remove(playerUUID);
                                }
                            }.runTaskLater(plugin, 10L); // display cancellation message for 10 ticks
                            cancel();
                            return;
                        }

                        // check if there is waiting time remaining
                        double timeLeft = endTime - System.currentTimeMillis();
                        if (timeLeft > 0) {
                            double progress = timeLeft / healingItem.getWaitingTime();
                            bossBar.setProgress(progress);
                            bossBar.setTitle("Healing in: " + df.format(timeLeft / 1000.0) + "s");
                        } else {

                            // remove an item from the player's inventory safely
                            ItemStack itemInHand = inventory.getItemInMainHand();
                            if (itemInHand.getAmount() > 1) {
                                itemInHand.setAmount(itemInHand.getAmount() - 1);
                            } else {
                                inventory.setItemInMainHand(null);
                            }

                            // heal the player
                            player.setHealth(Math.min(20, player.getHealth() + healingItem.getHealthRestored()));

                            // display healing confirmation message for 10 ticks
                            bossBar.setTitle("§aHealing complete");
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    bossBar.removeAll();
                                    playerBars.remove(playerUUID);
                                }
                            }.runTaskLater(plugin, 10L); // remove the boss bar after displaying healing message for 10 ticks
                            cancel();
                        }
                    }
                }.runTaskTimer(plugin, 0L, 1L); // decrement timer for every tick
            }
        }
    }
}
