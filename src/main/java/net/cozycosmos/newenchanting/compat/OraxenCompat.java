package net.cozycosmos.newenchanting.compat;

import io.th0rgal.oraxen.api.OraxenItems;
import org.bukkit.inventory.ItemStack;

public class OraxenCompat {
    public boolean isCustomItem(ItemStack item) {
        if (OraxenItems.getIdByItem(item) != null) {
            return true;
        } else {
            return false;
        }
    }

    public String getID(ItemStack item) {
        return OraxenItems.getIdByItem(item);
    }
}
