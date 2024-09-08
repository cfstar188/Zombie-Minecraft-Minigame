package me.cfstar188.zombiegame.configs;

import me.cfstar188.zombiegame.ZombieGame;
import me.cfstar188.zombiegame.builders.ShopCategoryBuilder;
import me.cfstar188.zombiegame.errors.CustomError;
import me.cfstar188.zombiegame.kits.ShopCategory;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

import static me.cfstar188.zombiegame.errors.CustomError.getAddToCustomArmorError;
import static me.cfstar188.zombiegame.errors.CustomError.getAddToCustomItemsError;

public class ShopConfig {

    private static ShopConfig instance; // for singleton design pattern
    private static final HashMap<String, ShopCategory> nameToCategory = new HashMap<>();
    private static int shopGUISize;

    private ShopConfig(ZombieGame plugin) {
        establishCategories(Objects.requireNonNull(plugin.getConfig().getList("shop")));
    }

    // there should only ever be one instance of KitConfig
    public static synchronized void getInstance(ZombieGame plugin) {
        if (instance == null) {
            instance = new ShopConfig(plugin);
        }
    }

    private void establishCategories(List<?> categories) {

        int maxSlot = 8;

        for (Object category : categories) {

            // extract data from kit
            String categoryName = (String) ((LinkedHashMap<?, ?>) category).get("category-name");

            // dealing with representative item
            String representativeItemName = ((String) ((LinkedHashMap<?, ?>) category).get("representative-item")).toUpperCase();
            Material material = Material.valueOf(representativeItemName);

            // error checking
            if (material == null) {
                System.out.println(CustomError.getInvalidMaterialError(representativeItemName));
                material = Material.BARRIER;
            }

            ItemStack representativeItem = new ItemStack(Material.valueOf(representativeItemName));

            Integer slot = (Integer) ((LinkedHashMap<?, ?>) category).get("slot");
            maxSlot = Math.max(maxSlot, slot);
            HashMap<String, Integer> entityNameToCost = new HashMap<>();
            HashMap<String, ItemStack> entityNameToItemStack = new HashMap<>();

            // dealing with items
            List<?> itemData;
            try {
                itemData = (List<?>) ((LinkedHashMap<?, ?>) category).get("items");
            }
            catch (NullPointerException e) {
                itemData = new ArrayList<>();
            }
            catch (ClassCastException e) {
                System.out.println(CustomError.getCustomError("Items config is not formatted correctly (check " + categoryName + ")"));
                return;
            }
            ArrayList<ItemStack> items = establishItems(itemData, entityNameToCost, entityNameToItemStack);

            // dealing with armor
            List<?> armorData;
            try {
                armorData = (List<?>) ((LinkedHashMap<?, ?>) category).get("armor");
            }
            catch (NullPointerException e) {
                armorData = new ArrayList<>();
            }
            catch (ClassCastException e) {
                System.out.println(CustomError.getCustomError("Armor config is not formatted correctly (check " + categoryName + ")"));
                return;
            }
            HashMap<String, ItemStack> armor = establishArmor(armorData, entityNameToCost, entityNameToItemStack);

            // dealing with weapons
            List<?> weaponData;
            try {
                weaponData = (List<?>) ((LinkedHashMap<?, ?>) category).get("weapons");
            }
            catch (NullPointerException e) {
                weaponData = new ArrayList<>();
            }
            catch (ClassCastException e) {
                System.out.println(CustomError.getCustomError("Weapon config is not formatted correctly (check " + categoryName + ")"));
                return;
            }
            HashMap<String, Integer> weaponNameToQuantity = establishWeapons(weaponData, entityNameToCost, entityNameToItemStack);

            // error checking to see if a kit has too many items and armor pieces
            if (items.size() + armor.size() + weaponNameToQuantity.size() > 18) {
                System.out.println(CustomError.getCustomError(categoryName + " contains more than 18 entities"));
                return;
            }


            // put into hashmap // kitName, slot, representativeItem, items, armor
            nameToCategory.put(categoryName, new ShopCategoryBuilder()
                    .setName(categoryName)
                    .setSlot(slot)
                    .setRepresentativeItem(representativeItem)
                    .setItems(items)
                    .setArmor(armor)
                    .setWeaponNameToQuantity(weaponNameToQuantity)
                    .setEntityNameToCost(entityNameToCost).setEntityNameToItemStack(entityNameToItemStack)
                    .build());
            System.out.println(entityNameToCost);

        }

        // generating the GUI size based on maximum slot
        int proposedInventorySize = ((maxSlot + 8) / 9) * 9;
        shopGUISize = Math.min(proposedInventorySize, 54);

    }

    // return the array of ItemStacks (each ItemStack stores the item and the quantity)
    private ArrayList<ItemStack> establishItems(List<?> itemData, HashMap<String, Integer> entityNameToCost, HashMap<String, ItemStack> entityNameToItemStack) {

        ArrayList<ItemStack> items = new ArrayList<>();

        if (itemData != null) {

            for (Object item : itemData) {

                String materialName = ((String) ((LinkedHashMap<?, ?>) item).get("name"));
                int quantity = (int) ((LinkedHashMap<?, ?>) item).get("quantity");
                ItemStack itemStack;
                if (CustomItemConfig.contains(materialName)) {
                    itemStack = CustomItemConfig.createCustomItemStack(materialName, quantity);
                }
                else {
                    System.out.println(getAddToCustomItemsError(materialName));
                    itemStack = new ItemStack(Material.BARRIER, quantity);
                }
                items.add(itemStack);
                int cost = (int) ((LinkedHashMap<?, ?>) item).get("cost");

                ItemMeta meta = itemStack.getItemMeta();
                assert meta != null;
                System.out.println(materialName + ": " + meta.getDisplayName());
                String displayName = meta.getDisplayName();
                entityNameToCost.put(displayName, cost);
                entityNameToItemStack.put(displayName, itemStack);

            }

        }

        return items;

    }

    // return the array of ItemStacks of armor
    private HashMap<String, ItemStack> establishArmor(List<?> armorData, HashMap<String, Integer> entityNameToCost, HashMap<String, ItemStack> entityNameToItemStack) {

        HashMap<String, ItemStack> armor = new HashMap<>();
        if (armorData != null) {

            for (Object armorPiece: armorData) {

                String materialName = ((String) ((LinkedHashMap<?, ?>) armorPiece).get("name"));
                int quantity = (int) ((LinkedHashMap<?, ?>) armorPiece).get("quantity");
                ItemStack itemStack;
                if (CustomArmorConfig.contains(materialName)) {
                    itemStack = CustomArmorConfig.createCustomItemStack(materialName, 1);
                    materialName = CustomArmorConfig.getCustomArmor(materialName).getMaterial().name();
                }
                else {
                    System.out.println(getAddToCustomArmorError(materialName));
                    itemStack = new ItemStack(Material.BARRIER, quantity);
                }

                String armorType = getArmorType(materialName);

                if (armorType.isEmpty()) {
                    System.out.println(CustomError.getCustomError(materialName + " is an invalid armor piece"));
                }
                armor.put(armorType, itemStack);

                int cost = (int) ((LinkedHashMap<?, ?>) armorPiece).get("cost");

                ItemMeta meta = itemStack.getItemMeta();
                assert meta != null;
                String displayName = meta.getDisplayName();
                entityNameToCost.put(displayName, cost);
                entityNameToItemStack.put(displayName, itemStack);

            }

        }

        return armor;

    }

    // return the array of weapon names
    private HashMap<String, Integer> establishWeapons(List<?> weaponData, HashMap<String, Integer> entityNameToCost, HashMap<String, ItemStack> entityNameToItemStack) {

        HashMap<String, Integer> weaponNameToQuantity = new HashMap<>();
        if (weaponData != null) {
            for (Object weapon : weaponData) {
                String weaponName = ((String) ((LinkedHashMap<?, ?>) weapon).get("name"));
                int quantity = (int) ((LinkedHashMap<?, ?>) weapon).get("quantity");
                weaponNameToQuantity.put(weaponName, quantity);
                int cost = (int) ((LinkedHashMap<?, ?>) weapon).get("cost");
                entityNameToCost.put(weaponName, cost);
            }
        }
        return weaponNameToQuantity;
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
    public static int getShopGUISize() {
        return shopGUISize;
    }

    public static HashMap<String, ShopCategory> getNameToCategory() {
        return nameToCategory;
    }

}
