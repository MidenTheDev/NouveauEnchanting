package net.cozycosmos.newenchanting.guis;


import net.cozycosmos.newenchanting.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;


public class EnchantingGUI implements InventoryHolder {
    //private final Main plugin = Main.getPlugin(Main.class);
    //FileConfiguration config = plugin.getConfig();

    private final Inventory inv;

    public EnchantingGUI(Main plugin) {
        // Create a new inventory, with no owner (as this isn't a real inventory), a size of nine, called example
        inv = plugin.getServer().createInventory(this, 27, ":offset_-16::enchanting_window:");

        // Put the items into the inventory
        initializeItems();
    }

    // You can call this whenever you want to put the items in
    public void initializeItems() {


        inv.setItem(14,createGuiItem(Material.PAPER, ChatColor.LIGHT_PURPLE+"Cost: 0 levels",10001,ChatColor.BLUE+"No material entered."));
    }

    // Nice little method to create a gui item with a custom name, and description
    protected ItemStack createGuiItem(final Material material, final String name, int customModelData, String lore) {
        final ItemStack item = new ItemStack(material, 1);
        final ItemMeta meta = item.getItemMeta();

        // Set the name of the item
        meta.setDisplayName(name);

        // Set the custom model data
        meta.setCustomModelData(customModelData);

        // Add the lore line
        List<String> newLore = new ArrayList<>();
        newLore.add(lore);
        meta.setLore(newLore);

        item.setItemMeta(meta);

        return item;
    }




    @Override
    public Inventory getInventory() {
        return inv;
    }
}