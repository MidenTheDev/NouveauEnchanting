package net.cozycosmos.newenchanting.events;

import net.cozycosmos.newenchanting.guis.EnchantingGUI;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class InvClose implements Listener {

    @EventHandler
    public void closeInventory(InventoryCloseEvent e) {

        if (e.getInventory().getHolder() instanceof EnchantingGUI) {
            if (e.getInventory().getItem(12) != null) {
                e.getPlayer().getWorld().dropItem(e.getPlayer().getLocation(),e.getInventory().getItem(12));
            }
            if (e.getInventory().getItem(10) != null) {
                e.getPlayer().getWorld().dropItem(e.getPlayer().getLocation(),e.getInventory().getItem(10));
            }

        }
    }

}
