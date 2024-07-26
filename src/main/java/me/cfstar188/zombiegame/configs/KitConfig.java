package me.cfstar188.zombiegame.configs;

import me.cfstar188.zombiegame.ZombieGame;
import me.cfstar188.zombiegame.builders.KitBuilder;
import me.cfstar188.zombiegame.errors.CustomError;
import me.cfstar188.zombiegame.kits.Kit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.*;

/*
Saves the data about kits from config.yml
*/
public class KitConfig {

    private static KitConfig instance; // for singleton design pattern
    private static final HashMap<String, Kit> nameToKit = new HashMap<>();
    private static int kitGUISize;

    private KitConfig(ZombieGame plugin) {
        establishKits(Objects.requireNonNull(plugin.getConfig().getList("kits")));
    }

    // there should only ever be one instance of KitConfig
    public static synchronized void getInstance(ZombieGame plugin) {
        if (instance == null) {
            instance = new KitConfig(plugin);
        }
    }

    private void establishKits(List<?> kits) {

        int maxSlot = 8;

        for (Object kit : kits) {

            // extract data from kit
            String kitName = (String) ((LinkedHashMap<?, ?>) kit).get("name");

            // dealing with representative item
            String representativeItemName = ((String) ((LinkedHashMap<?, ?>) kit).get("representative-item")).toUpperCase();
            Material material = Material.valueOf(representativeItemName);

            // error checking
            if (material == null) {
                System.out.println(CustomError.getInvalidMaterialError(representativeItemName));
                material = Material.BARRIER;
            }

            ItemStack representativeItem = new ItemStack(Material.valueOf(representativeItemName));

            Integer slot = (Integer) ((LinkedHashMap<?, ?>) kit).get("slot");
            maxSlot = Math.max(maxSlot, slot);

            // dealing with items
            List<?> itemData;
            try {
                itemData = (List<?>) ((LinkedHashMap<?, ?>) kit).get("items");
            }
            catch (NullPointerException e) {
                itemData = new ArrayList<>();
            }
            catch (ClassCastException e) {
                System.out.println(CustomError.getCustomError("Items config is not formatted correctly (check " + kitName + ")"));
                return;
            }
            ArrayList<ItemStack> items = establishItems(itemData);

            // dealing with armor
            List<?> armorData;
            try {
                armorData = (List<?>) ((LinkedHashMap<?, ?>) kit).get("armor");
            }
            catch (NullPointerException e) {
                armorData = new ArrayList<>();
            }
            catch (ClassCastException e) {
                System.out.println(CustomError.getCustomError("Armor config is not formatted correctly (check " + kitName + ")"));
                return;
            }
            HashMap<String, ItemStack> armor = establishArmor(armorData);

            // error checking to see if a kit has too many items and armor pieces
            if (items.size() + armor.size() > 18) {
                System.out.println(CustomError.getCustomError(kitName + " contains more than 18 items and armor pieces"));
                return;
            }

            // put into hashmap // kitName, slot, representativeItem, items, armor
            nameToKit.put(kitName, new KitBuilder()
                    .setName(kitName)
                    .setSlot(slot)
                    .setRepresentativeItem(representativeItem)
                    .setItems(items)
                    .setArmor(armor)
                    .build());

        }

        // generating the GUI size based on maximum slot
        int proposedInventorySize = ((maxSlot + 8) / 9) * 9;
        kitGUISize = Math.min(proposedInventorySize, 54);

    }

    // return the array of ItemStacks (each ItemStack stores the item and the quantity)
    private ArrayList<ItemStack> establishItems(List<?> itemData) {

        ArrayList<ItemStack> items = new ArrayList<>();

        if (itemData != null) {

            for (Object item : itemData) {

                String materialName = ((String) ((LinkedHashMap<?, ?>) item).get("name")).toUpperCase();
                Material material = Material.getMaterial(materialName);

                // error checking
                if (material == null) {
                    System.out.println(CustomError.getInvalidMaterialError(materialName));
                    material = Material.BARRIER;
                }

                int quantity = (int) ((LinkedHashMap<?, ?>) item).get("quantity");
                items.add(new ItemStack(material, quantity));

            }

        }

        return items;

    }

    // return the array of ItemStacks of armor
    private HashMap<String, ItemStack> establishArmor(List<?> armorData) {

        HashMap<String, ItemStack> armor = new HashMap<>();

        if (armorData != null) {

            for (Object armorPiece: armorData) {

                String materialName = ((String) armorPiece).toUpperCase();
                Material material = Material.getMaterial(materialName);

                // error checking
                if (material == null) {
                    System.out.println(CustomError.getInvalidMaterialError(materialName));
                    material = Material.BARRIER;
                }

                String armorType = getArmorType(materialName);

                if (armorType.isEmpty()) {
                    System.out.println(CustomError.getCustomError(material + " is an invalid armor piece"));
                }

                armor.put(armorType, new ItemStack(material));

            }

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

    /*
    Getter methods
    */
    public static int getKitGUISize() {
        return kitGUISize;
    }

    public static HashMap<String, Kit> getNameToKit() {
        return nameToKit;
    }

}
