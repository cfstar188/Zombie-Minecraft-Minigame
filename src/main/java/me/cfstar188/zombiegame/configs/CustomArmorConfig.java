package me.cfstar188.zombiegame.configs;

import me.cfstar188.zombiegame.ZombieGame;
import me.cfstar188.zombiegame.builders.CustomArmorBuilder;
import me.cfstar188.zombiegame.errors.CustomError;
import me.cfstar188.zombiegame.items.CustomArmor;
import me.cfstar188.zombiegame.misc.FormatNum;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class CustomArmorConfig {

    ZombieGame plugin;
    static CustomArmorConfig instance;
    static HashMap<String, CustomArmor> nameToCustomArmor = new HashMap<>();

    public CustomArmorConfig(ZombieGame plugin) {
        this.plugin = Objects.requireNonNull(plugin, CustomError.getCustomError("Plugin cannot be null"));
        establishHashmap(Objects.requireNonNull(plugin.getConfig().getList("custom-armor"), CustomError.getCustomError("CustomArmor config cannot be null")));
    }

    // there should only ever be one instance of HealingConfig
    public static synchronized void getInstance(ZombieGame plugin) {
        if (instance == null) {
            instance = new CustomArmorConfig(plugin);
        }
    }

    // establish the nameToCustomArmor hashmap
    private static void establishHashmap(List<?> customArmorList) {
        for (Object customArmor : customArmorList) {

            // extract data from healing
            String name = ((String) ((LinkedHashMap<?, ?>) customArmor).get("name"));
            String lore = (String) ((LinkedHashMap<?, ?>) customArmor).get("lore");
            double armorLevel = FormatNum.getDouble(((LinkedHashMap<?, ?>) customArmor).get("armor-level"));
            String materialString = ((String) ((LinkedHashMap<?, ?>) customArmor).get("material")).toUpperCase();
            Material material = Material.valueOf(materialString);
            System.out.printf("%s %s %f %s%n", name, lore, armorLevel, materialString);

            // put into hashmap
            nameToCustomArmor.put(name, new CustomArmorBuilder()
                    .setLore(lore)
                    .setArmorLevel(armorLevel)
                    .setMaterial(material)
                    .build());

        }
    }

    public static boolean contains(String customArmor) {
        return nameToCustomArmor.containsKey(customArmor);
    }

    public static CustomArmor getCustomArmor(String customArmor) {
        return nameToCustomArmor.get(customArmor);
    }

    public static ItemStack createCustomItemStack(String materialName, int quantity) {
        CustomArmor customArmor = CustomArmorConfig.getCustomArmor(materialName);
        ItemStack itemStack = new ItemStack(customArmor.getMaterial(), quantity);
        ItemMeta meta = itemStack.getItemMeta();
        assert meta != null;
        meta.setDisplayName(materialName);
        List<String> lore = customArmor.getLore();

        double armorLevel = customArmor.getArmorLevel();
        changeArmorLevel(meta, materialName, armorLevel);
        lore.add(ChatColor.RED + "Armor Level: " + armorLevel);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.setLore(lore);
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    // modifies armor level of ItemMeta
    private static void changeArmorLevel(ItemMeta meta, String materialName, double armorLevel) {
        AttributeModifier armorModifier = new AttributeModifier(
                UUID.randomUUID(),
                materialName,
                armorLevel,
                AttributeModifier.Operation.ADD_NUMBER
        );
        meta.addAttributeModifier(Attribute.GENERIC_ARMOR, armorModifier);
    }
}
