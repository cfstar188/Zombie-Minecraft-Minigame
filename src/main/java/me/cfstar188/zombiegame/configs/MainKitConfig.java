package me.cfstar188.zombiegame.configs;

import me.cfstar188.zombiegame.ZombieGame;
import me.cfstar188.zombiegame.kits.Kit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class MainKitConfig {

    private static final HashMap<String, Kit> nameToKit = new HashMap<>();
    private static int kitGUISize;
    private static final HashSet<String> kitNames = new HashSet<>();

    public MainKitConfig(ZombieGame plugin) {
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

            System.out.println(armorData);
            HashMap<String, ItemStack> armor = establishArmor(armorData);

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
            nameToKit.put(kitName, new Kit(kitName, slot, representativeItem, items, armor));

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
    private HashMap<String, ItemStack> establishArmor(List<?> armorData) {

        HashMap<String, ItemStack> armor = new HashMap<>();

        for (Object armorPiece: armorData) {

            String materialName = ((String) armorPiece).toUpperCase();
            Material material = Material.getMaterial(materialName);

            // error checking
            if (material == null) {
                System.out.println("ERROR: " + materialName + " IS AN INVALID ARMOR PIECE");
                material = Material.BARRIER;
            }

            String armorType = getArmorType(materialName);

            if (armorType.isEmpty()) {
                System.out.println("ERROR: " + materialName + " IS AN INVALID ARMOR PIECE");
            }

            armor.put(armorType, new ItemStack(material));

        }
        return armor;

    }

    // returns either helmet, chestplate, leggings, or boots
    private String getArmorType(String materialName) {

        switch (getSuffix(materialName)) {
            case "HELMET":
                return "helmet";
            case "CHESTPLATE":
                return "chestplate";
            case "LEGGINGS":
                return "leggings";
            case "BOOTS":
                return "boots";
            default:
                return "";
        }

    }

    // get the suffix from the material name by trimming everything before the last underscore
    private static String getSuffix(String materialName) {
        int lastUnderscoreIndex = materialName.lastIndexOf('_');
        return materialName.substring(lastUnderscoreIndex + 1);
    }

    public static int getKitGUISize() {
        return kitGUISize;
    }

    public static HashMap<String, Kit> getNameToKit() {
        return nameToKit;
    }

    public static HashSet<String> getKitNames() {
        return kitNames;
    }

}
