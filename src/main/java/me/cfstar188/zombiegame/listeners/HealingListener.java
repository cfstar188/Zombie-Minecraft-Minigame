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

public class HealingListener implements Listener {

    private static final HashMap<Material, HealingItem> materialToStats = new HashMap<>();
    private final HashMap<UUID, BossBar> playerBars = new HashMap<>();
    private final DecimalFormat df = new DecimalFormat("#.##");
    private final ZombieGame plugin;

    public HealingListener(ZombieGame plugin) {
        this.plugin = plugin;
        establishHashmap(Objects.requireNonNull(plugin.getConfig().getList("healing")));
    }

    private static void establishHashmap(List<?> healings) {
        for (Object healing : healings) {
            // extract data
            String materialName = (String) ((LinkedHashMap<?, ?>) healing).get("material");
            if (materialName != null) {
                Material material = Material.valueOf(materialName.toUpperCase());
                double heartsGained = getDouble(((LinkedHashMap<?, ?>) healing).get("hearts-gained"));
                double waitingTime = getDouble(((LinkedHashMap<?, ?>) healing).get("waiting-time"));

                // put into hashmap
                materialToStats.put(material, new HealingItem(heartsGained * 2, waitingTime * 1000));
            }
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

            if (materialToStats.containsKey(material) && player.getHealth() < 20 && !playerBars.containsKey(playerUUID)) {

                HealingItem healingItem = materialToStats.get(material);
                long currentTime = System.currentTimeMillis();

                // Create and display the countdown timer
                BossBar bossBar = Bukkit.createBossBar("", BarColor.RED, BarStyle.SOLID);
                bossBar.addPlayer(player);
                playerBars.put(playerUUID, bossBar);

                new BukkitRunnable() {
                    final long endTime = currentTime + (long) healingItem.getWaitingTime();

                    @Override
                    public void run() {
                        long timeLeft = endTime - System.currentTimeMillis();

                        // Check if the player changed the item in their hand
                        if (!inventory.getItemInMainHand().getType().equals(material)) {
                            bossBar.setTitle("Healing canceled");
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    bossBar.removeAll();
                                    playerBars.remove(playerUUID);
                                }
                            }.runTaskLater(plugin, 20L); // Display cancellation message for 1 second
                            cancel();
                            return;
                        }

                        if (timeLeft > 0) {
                            double progress = (double) timeLeft / healingItem.getWaitingTime();
                            bossBar.setProgress(progress);
                            bossBar.setTitle("Healing in: " + df.format(timeLeft / 1000.0) + "s");
                        } else {
                            inventory.removeItem(new ItemStack(material, 1));
                            player.setHealth(Math.min(20, player.getHealth() + healingItem.getHealthRestored()));
                            bossBar.removeAll();
                            playerBars.remove(playerUUID);
                            cancel();
                        }
                    }
                }.runTaskTimer(plugin, 0L, 10L); // Run task every half second (10 ticks)
            }
        }
    }
}
