package net.cozycosmos.newenchanting.events;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;

public class AnvilInvInteract implements Listener {

    @EventHandler
    public void onInvClick(InventoryClickEvent e) {
        if (e.getView().getType().equals(InventoryType.ANVIL)) {
            if (e.getCursor().getType().equals(Material.ENCHANTED_BOOK)) {
                if (e.getRawSlot()==1) {
                    e.setCancelled(true);
                }
            } else if (e.getCurrentItem().getType().equals(Material.ENCHANTED_BOOK)) {
                if (e.getClick().equals(ClickType.SHIFT_LEFT)) {
                    e.setCancelled(true);
                }
            }
        }
    }

}
