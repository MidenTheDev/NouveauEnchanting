package net.cozycosmos.newenchanting.events;

import net.cozycosmos.newenchanting.Main;
import net.cozycosmos.newenchanting.enchants.EnchantHandler;
import net.cozycosmos.newenchanting.guis.EnchantingGUI;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class EnchantingInvInteract implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        Inventory eWindow = e.getInventory();
        EnchantHandler enchHandler = new EnchantHandler();


        if (!(eWindow.getHolder() instanceof EnchantingGUI)) {

            return;
        }
        else if (e.getRawSlot() == 10 || e.getRawSlot() == 12 || e.getRawSlot() == 16) {


            if (e.getRawSlot() == 12 && e.getInventory().getItem(e.getRawSlot()) != null && e.getCursor() != null && e.getClick().equals(ClickType.LEFT)) {
                eWindow.clear(16);

            }

            else if (e.getRawSlot() == 10 && e.getInventory().getItem(e.getRawSlot()) != null && e.getCursor() != null && e.getClick().equals(ClickType.LEFT)) {
                eWindow.clear(16);

            }

            else if (e.getClick().equals(ClickType.SHIFT_LEFT) || e.getClick().equals(ClickType.SHIFT_RIGHT)) {

                e.setCancelled(true);
            }

            else if (e.getRawSlot() == 16 && eWindow.getItem(10) != null && eWindow.getItem(16) != null && eWindow.getItem(12) != null) {
                String enchant = enchHandler.getEnchant(eWindow.getItem(12),eWindow.getItem(10));
                ItemStack result = eWindow.getItem(16);
                int enchantLevel = result.getItemMeta().getEnchantLevel(Enchantment.getByName(enchant));


                if (p.getLevel()>=enchHandler.getExpCost(enchant,enchantLevel)) {
                    if(eWindow.getItem(12).getAmount()>=enchHandler.getMaterialCost(enchant,enchantLevel)){
                        p.setLevel(p.getLevel()-enchHandler.getExpCost(enchant,enchantLevel));
                        ItemStack materialFinal = eWindow.getItem(12).clone();
                        materialFinal.setAmount(materialFinal.getAmount()-enchHandler.getMaterialCost(enchant,enchantLevel));



                        eWindow.clear(10);

                        eWindow.getItem(12).setAmount(eWindow.getItem(12).getAmount()-enchHandler.getMaterialCost(enchant,enchantLevel));
                        p.playSound(p.getLocation(), Sound.BLOCK_ENCHANTMENT_TABLE_USE,1f,1f);
                    } else {
                        p.sendMessage(ChatColor.RED + "You don't have enough of the required material!");
                        e.setCancelled(true);
                    }

                } else {
                    p.sendMessage(ChatColor.RED + "You don't have enough EXP!");
                    e.setCancelled(true);
                }

            }

            else if (e.getRawSlot()==16 && eWindow.getItem(16) == null) {

                e.setCancelled(true);
            }
            else if (e.getRawSlot() == 12 && eWindow.getItem(10) != null && e.getCursor() != null) {
                enchHandler.guiEnchantVanilla(eWindow.getItem(10), e.getCursor(), eWindow);

            } else if(e.getRawSlot() == 10 && eWindow.getItem(12) != null && e.getInventory().getItem(12) != null) {
                enchHandler.guiEnchantVanilla(e.getCursor(), eWindow.getItem(12), eWindow);
            }
            else if (e.getRawSlot() == 16 && (eWindow.getItem(12) == null || eWindow.getItem(10) == null )) {
                eWindow.clear(16);
                e.setCancelled(true);

            }

        }
        else if(e.getRawSlot() >= 27) {

            if (e.getClick().equals(ClickType.SHIFT_LEFT) || e.getClick().equals(ClickType.SHIFT_RIGHT)) {
                e.setCancelled(true);
            } else {
                return;
            }

        }



        else {

            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent e) {
        if (e.getInventory().getHolder() instanceof EnchantingGUI) {
            e.setCancelled(true);
        }
    }
}
