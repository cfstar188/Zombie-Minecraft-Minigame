package me.cfstar188.zombiegame.listeners;

import me.cfstar188.zombiegame.ZombieGame;
import me.cfstar188.zombiegame.gui.DisplayKitGUI;
import me.cfstar188.zombiegame.kits.Kit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class MainKitListener implements Listener {

    private final ZombieGame plugin;
    private static final HashMap<Integer, Kit> slotToKit = new HashMap<>();
    private static int kitGUISize;
    private static final HashSet<String> kitNames = new HashSet<>();

    public MainKitListener(ZombieGame plugin) {
        this.plugin = plugin;
        establishKits(Objects.requireNonNull(plugin.getConfig().getList("kits")));
    }

    private void establishKits(List<?> kits) {

        int maxSlot = 8;

        for (Object kit : kits) {

            System.out.println("\nKIT");
            // extract data from kit
            String kitName = (String) ((LinkedHashMap<?, ?>) kit).get("name");
            kitNames.add(kitName);
            System.out.println(kitName);

            // dealing with representative item
            String representativeItemName = ((String) ((LinkedHashMap<?, ?>) kit).get("representative-item")).toUpperCase();
            System.out.println(representativeItemName);
            Material material = Material.valueOf(representativeItemName);

            // error checking
            if (material == null) {
                System.out.println("ERROR: " + representativeItemName + " IS AN INVALID MINECRAFT ITEM");
                material = Material.BARRIER;
            }

            ItemStack representativeItem = new ItemStack(Material.valueOf(representativeItemName));

            Integer slot = (Integer) ((LinkedHashMap<?, ?>) kit).get("slot");
            System.out.println(slot + "\n");
            maxSlot = Math.max(maxSlot, slot);

            // extracting item data
            System.out.println("ITEMS");
            List<?> itemData = (List<?>) ((LinkedHashMap<?, ?>) kit).get("items");
            ArrayList<ItemStack> items = establishItems(itemData);

            // error checking
            if (items.isEmpty()) {
                return;
            }

            System.out.println("\nARMOR");
            List<?> armorData = (List<?>) ((LinkedHashMap<?, ?>) kit).get("armor");
            ArrayList<ItemStack> armor = establishArmor(armorData);

            // error checking
            if (armor.isEmpty()) {
                return;
            }

            // error checking to see if a kit has too many items and armor pieces
            if (items.size() + armor.size() > 18) {
                System.out.println("ERROR: " + kitName + " contains more than 18 items and armor pieces");
                return;
            }

            // put into hashmap
            slotToKit.put(slot, new Kit(kitName, representativeItem, items, armor));

        }

        // generating the GUI size based on maximum slot
        int proposedInventorySize = ((maxSlot + 8) / 9) * 9;
        kitGUISize = Math.min(proposedInventorySize, 54);

    }

    // return the array of ItemStacks (each ItemStack stores the item and the quantity)
    private ArrayList<ItemStack> establishItems(List<?> itemData) {

        ArrayList<ItemStack> items = new ArrayList<>();

        for (Object item: itemData) {

            String materialName = ((String) ((LinkedHashMap<?, ?>) item).get("name")).toUpperCase();
            Material material = Material.getMaterial(materialName);

            // error checking
            if (material == null) {
                System.out.println("ERROR: " + materialName + " IS AN INVALID MINECRAFT ITEM");
                material = Material.BARRIER;
            }

            int quantity = (int) ((LinkedHashMap<?, ?>) item).get("quantity");
            items.add(new ItemStack(material, quantity));
            System.out.println(materialName + ", " + quantity);

        }

        return items;

    }

    // return the array of ItemStacks of armor
    private ArrayList<ItemStack> establishArmor(List<?> armorData) {

        ArrayList<ItemStack> armor = new ArrayList<>();

        for (Object armorPiece: armorData) {

            String materialName = ((String) armorPiece).toUpperCase();
            Material material = Material.getMaterial(materialName);

            // error checking
            if (material == null) {
                System.out.println("ERROR: " + materialName + " IS AN INVALID MINECRAFT ITEM");
                material = Material.BARRIER;
            }

            armor.add(new ItemStack(material));
            System.out.println(materialName);

        }

        return armor;

    }

    public static int getKitGUISize() {
        return kitGUISize;
    }

    public static HashMap<Integer, Kit> getSlotToKit() {
        return slotToKit;
    }

    public static HashSet<String> getKitNames() {
        return kitNames;
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {

        if (event.getView().getTitle().equals("Kits") && event.getCurrentItem() != null) {

            // ensures items cannot be placed into player's main inventory
            event.setCancelled(true);

            Player player = (Player) event.getWhoClicked();

            // what kit did the player select
            Kit selectedKit = slotToKit.get(event.getSlot());

            new DisplayKitGUI(player, selectedKit);

        }

    }

}
