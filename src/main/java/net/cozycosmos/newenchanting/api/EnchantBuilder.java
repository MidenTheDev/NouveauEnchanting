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
    private List<Enchantment> conflicts;

    public EnchantBuilder(String Key, String Name, int MaxLevel) {
        super(NamespacedKey.minecraft(Key));
        name = Name;
        maxLevel = MaxLevel;

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
        if (conflicts.contains(enchantment)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean canEnchantItem(ItemStack itemStack) {
        if (getItemTarget().includes(itemStack)) {
            return true;
        } else {
            return false;
        }
    }

    public void setEnchantmentTarget(EnchantmentTarget eTarget) {
        target = eTarget;
    }

    public void addEnchantmentConflict(Enchantment ench) {
        if (conflicts.isEmpty()) {
            conflicts = new ArrayList<>();
        }
        conflicts.add(ench);
    }
}
