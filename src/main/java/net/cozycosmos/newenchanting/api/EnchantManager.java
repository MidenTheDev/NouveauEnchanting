package net.cozycosmos.newenchanting.api;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EnchantManager {

    public static void registerEnchant(Enchantment enchant) {
        boolean registered = Arrays.stream(Enchantment.values()).collect(Collectors.toList()).contains(enchant);

        if (!registered) {
            try {
                Field f = Enchantment.class.getDeclaredField("acceptingNew");
                f.setAccessible(true);
                f.set(null, true);
                Enchantment.registerEnchantment(enchant);
            } catch (Exception e) {
                Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Nouveau Enchanting Error: Custom Enchantment " + enchant.getName() + "is Invalid");
                e.printStackTrace();
            }
        } else {
            Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_PURPLE + "[Nouveau Enchanting]: " + ChatColor.RED +"Custom Enchantment " + enchant.getName() + "is already registered, check your code for redundant registers");

        }
    }

    public boolean hasEnchant(ItemStack item, Enchantment enchant) {
        if (item.getItemMeta().hasEnchant(enchant)) {
            return true;
        } else {
            return false;
        }
    }

    public void applyEnchant(ItemStack item, Enchantment enchant, int level, boolean bypassLevelLimit) {
        ItemMeta meta = item.getItemMeta();
        meta.addEnchant(enchant, level,bypassLevelLimit);
        if (enchant instanceof EnchantBuilder) {
            List<String> lore = meta.getLore();

            lore.add(ChatColor.GRAY + enchant.getName() + " " + numberToRomanNumeral(level));

            meta.setLore(lore);
            item.setItemMeta(meta);
        }

    }

    public String numberToRomanNumeral(int number) {
        if (number > 10) {
            return String.valueOf(number);
        }
        String finalString = "";
        for (int i = 1; i <= number; i++) {
            finalString += "I";
        }
        return finalString.replace("IIIIIIIIII","X").replace("IIIIIIIII","IX").replace("IIIII","V").replace("IIII","IV");


    }




}
