package net.cozycosmos.newenchanting.commands;

import net.cozycosmos.newenchanting.api.EnchantManager;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Core implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            if (args.length > 0) {
                if (args[0].equalsIgnoreCase("ench") || args[0].equalsIgnoreCase("enchant") || args[0].equalsIgnoreCase("e")) {
                    if (sender.hasPermission("nouveauenchanting.command.enchant")) {
                        if (args.length >= 3) {
                            Player p = (Player) sender;
                            if (p.getInventory().getItemInMainHand() != null) {
                                if (isInteger(args[2])) {
                                    int level = Integer.parseInt(args[2]);
                                    ItemStack item = p.getInventory().getItemInMainHand();
                                    EnchantManager eMananger = new EnchantManager();
                                    if (args.length >= 4 && args[3].equalsIgnoreCase("true")) {
                                        eMananger.applyEnchant(item, Enchantment.getByKey(NamespacedKey.minecraft(args[1])),level,true);
                                    } else {
                                        eMananger.applyEnchant(item, Enchantment.getByKey(NamespacedKey.minecraft(args[1])),level,false);
                                    }
                                } else {
                                    sender.sendMessage(ChatColor.RED + "Please enter a number for the level");
                                }



                            } else {
                                sender.sendMessage(ChatColor.RED + "You aren't holding anything!");
                            }
                        } else {
                            sender.sendMessage(ChatColor.RED + "Not enough arguments. Format: /ne ench enchant_name level [true/false]");
                        }
                    } else {
                        sender.sendMessage(ChatColor.RED + "You do not have permission to run this command!");
                    }
                }
            } else {
                //List commands
            }
        } else {
            sender.sendMessage(ChatColor.RED + "You must be a player to run this command!");
        }
        return true;
    }

    public boolean isInteger(String num) {
        try {
            Integer.parseInt(num);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
