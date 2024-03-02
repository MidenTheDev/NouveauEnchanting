package net.cozycosmos.newenchanting.events;

import net.cozycosmos.newenchanting.Main;
import net.cozycosmos.newenchanting.guis.EnchantingGUI;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;

public class InventoryOpen implements Listener {

    private final Main plugin = Main.getPlugin(Main.class);


    @EventHandler
    public void onEnchtableOpened(InventoryOpenEvent e) {
        if (plugin.getConfig().getBoolean("override-vanilla-table")) {
            if (e.getInventory().getType().equals(InventoryType.ENCHANTING)) {


                e.setCancelled(true);
                EnchantingGUI ench = new EnchantingGUI(plugin);
                e.getPlayer().openInventory(ench.getInventory());
            }
        }



    }

}
