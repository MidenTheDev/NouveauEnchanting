package net.cozycosmos.newenchanting.api;

import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class EnchantBuilder extends Enchantment {

    private final String name;
    private final int maxLevel;

    private EnchantmentTarget target = null;
    private List<Enchantment> conflicts = new ArrayList<>();

    public EnchantBuilder(String Key, String Name, int MaxLevel) {
        super(NamespacedKey.minecraft(Key));
        name = Name;
        maxLevel = MaxLevel;

    }
    public EnchantBuilder(String Key, String Name, int MaxLevel, EnchantmentTarget enchantmentTarget) {
        super(NamespacedKey.minecraft(Key));
        name = Name;
        maxLevel = MaxLevel;
        target = enchantmentTarget;

    }
    public EnchantBuilder(String Key, String Name, int MaxLevel, EnchantmentTarget enchantmentTarget, List<Enchantment> Conflicts) {
        super(NamespacedKey.minecraft(Key));
        name = Name;
        maxLevel = MaxLevel;
        target = enchantmentTarget;
        conflicts = Conflicts;

    }
    public EnchantBuilder(String Key, String Name, int MaxLevel, List<Enchantment> Conflicts) {
        super(NamespacedKey.minecraft(Key));
        name = Name;
        maxLevel = MaxLevel;
        conflicts = Conflicts;

    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getMaxLevel() {
        return maxLevel;
    }

    @Override
    public int getStartLevel() {
        return 0;
    }

    @Override
    public EnchantmentTarget getItemTarget() {
        return target;
    }

    @Override
    public boolean isTreasure() {
        return false;
    }

    @Override
    public boolean isCursed() {
        return false;
    }

    @Override
    public boolean conflictsWith(Enchantment enchantment) {
        return conflicts.contains(enchantment);
    }

    @Override
    public boolean canEnchantItem(ItemStack itemStack) {
        return getItemTarget().includes(itemStack);
    }




}
