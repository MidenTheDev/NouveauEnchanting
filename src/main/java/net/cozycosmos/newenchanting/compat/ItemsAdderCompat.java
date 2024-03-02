package net.cozycosmos.newenchanting.compat;

import dev.lone.itemsadder.api.CustomStack;
import dev.lone.itemsadder.api.ItemsAdder;
import org.bukkit.inventory.ItemStack;

public class ItemsAdderCompat {

    public boolean isCustomItem(ItemStack item) {

        if (CustomStack.byItemStack(item) != null) {
            return true;
        } else {
            return false;
        }
    }



    public String getID(ItemStack item) {
        CustomStack stack = CustomStack.byItemStack(item);
        return stack.getId();
    }

}
