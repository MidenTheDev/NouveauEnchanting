package net.cozycosmos.newenchanting.enchants;

import net.cozycosmos.newenchanting.Main;
import net.cozycosmos.newenchanting.compat.ItemsAdderCompat;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class EnchantHandler {
    private int enchCost;
    private int matCost;
    private String enchant;

    Boolean conflicts;

    private final Main plugin = Main.getPlugin(Main.class);

    File enchants = new File(plugin.getDataFolder()+"/enchants.yml");
    FileConfiguration enchantsYml = YamlConfiguration.loadConfiguration(enchants);
    File materialData = new File(plugin.getDataFolder()+"/data/materialdata.yml");
    FileConfiguration materialDataYml = YamlConfiguration.loadConfiguration(materialData);
    File enchantItemData = new File(plugin.getDataFolder()+"/data/enchantitemdata.yml");
    FileConfiguration enchantItemDataYml = YamlConfiguration.loadConfiguration(enchantItemData);


    public void guiEnchantVanilla(ItemStack tool, ItemStack materialComp, Inventory eWindow) {
        if (tool.getAmount() > 1) {
            eWindow.clear(16);
            return;
        }
        enchant = getEnchant(materialComp,tool);

        if (enchant != null) {
            if (!checkConflicts(enchant, tool)) {

            ItemStack result = tool.clone();
            ItemMeta resultMeta = result.getItemMeta();
            int startLevel = resultMeta.getEnchantLevel(Enchantment.getByName(enchant));

            if (startLevel == 0) {
                resultMeta.addEnchant(Enchantment.getByName(enchant), 1, true);
                result.setItemMeta(resultMeta);
                enchCost = enchantsYml.getInt(enchant + ".1.exp-cost");
                matCost = enchantsYml.getInt(enchant + ".1.material-cost");

                ItemMeta infoItemMeta = eWindow.getItem(14).getItemMeta();
                infoItemMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Cost: " + enchCost + " levels");
                List<String> lore = infoItemMeta.getLore();
                String materialName;
                if (plugin.itemsadderLoaded) {
                    ItemsAdderCompat iacomp = new ItemsAdderCompat();
                    if (iacomp.isCustomItem(materialComp)) {
                        materialName = materialComp.getItemMeta().getDisplayName();
                    }else {
                        materialName = materialComp.getType().toString().toLowerCase().replace(materialComp.getType().toString().toLowerCase().charAt(0), Character.toUpperCase(materialComp.getType().toString().toLowerCase().charAt(0))).replace('_',' ');
                    }
                } else {
                    materialName = materialComp.getType().toString().toLowerCase().replace(materialComp.getType().toString().toLowerCase().charAt(0), Character.toUpperCase(materialComp.getType().toString().toLowerCase().charAt(0))).replace('_',' ');
                }

                if ((materialName.length()-1)=='y') {
                    materialName = materialName.substring(0,materialName.length()-1) + "ies";
                } else {
                    materialName = materialName + "s";
                }
                lore.set(0, ChatColor.WHITE + String.valueOf(matCost) + " " + materialName);
                infoItemMeta.setLore(lore);
                eWindow.getItem(14).setItemMeta(infoItemMeta);

                eWindow.setItem(16, result);
            } else if (enchantsYml.getConfigurationSection(enchant + "." + (startLevel + 1)) != null) {
                resultMeta.addEnchant(Enchantment.getByName(enchant), startLevel + 1, true);
                result.setItemMeta(resultMeta);
                enchCost = enchantsYml.getInt(enchant + "." + (startLevel + 1) + ".exp-cost");
                matCost = enchantsYml.getInt(enchant + "." + (startLevel + 1) + ".material-cost");

                ItemMeta infoItemMeta = eWindow.getItem(14).getItemMeta();
                infoItemMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Cost: " + enchCost + " levels");
                List<String> lore = infoItemMeta.getLore();
                String materialName;
                if (plugin.itemsadderLoaded) {
                    ItemsAdderCompat iacomp = new ItemsAdderCompat();
                    if (iacomp.isCustomItem(materialComp)) {
                        materialName = materialComp.getItemMeta().getDisplayName();
                    }else {
                        materialName = materialComp.getType().toString().toLowerCase().replace(materialComp.getType().toString().toLowerCase().charAt(0), Character.toUpperCase(materialComp.getType().toString().toLowerCase().charAt(0))).replace('_',' ');
                    }
                } else {
                    materialName = materialComp.getType().toString().toLowerCase().replace(materialComp.getType().toString().toLowerCase().charAt(0), Character.toUpperCase(materialComp.getType().toString().toLowerCase().charAt(0))).replace('_',' ');
                }

                if ((materialName.length()-1)=='y') {
                    materialName = materialName.substring(0,materialName.length()-1) + "ies";
                } else {
                    materialName = materialName + "s";
                }
                lore.set(0, ChatColor.WHITE + String.valueOf(matCost) + " " + materialName);
                infoItemMeta.setLore(lore);
                eWindow.getItem(14).setItemMeta(infoItemMeta);

                eWindow.setItem(16, result);
            } else {
                //enchant is max level already
                ItemMeta infoItemMeta = eWindow.getItem(14).getItemMeta();
                infoItemMeta.setDisplayName(ChatColor.RED + "Enchant already max level!");
                eWindow.getItem(14).setItemMeta(infoItemMeta);
            }
        }


        }

    }

    public boolean checkConflicts(String ench, ItemStack tool) {
        conflicts = false;
        tool.getEnchantments().forEach((enchantment, integer) -> {
            if (Enchantment.getByName(ench).conflictsWith(enchantment) && !(Enchantment.getByName(ench).equals(enchantment))) {
                conflicts = true;
            }

        });


        return conflicts;
    }


    public Boolean isValidEnchItemVanilla(ItemStack tool, String ench) {
        Boolean isValid;

        if (tool.getAmount()>1) {
            isValid = false;
        } else {

            if (ench != null) {



            if (enchantItemDataYml.getStringList(ench).contains(tool.getType().toString())) {
                isValid = true;
            } else {
                isValid = false;
            }

            if (plugin.itemsadderLoaded) {
                ItemsAdderCompat iacomp = new ItemsAdderCompat();
                if (iacomp.isCustomItem(tool)) {
                    if (enchantItemDataYml.getStringList(ench).contains(iacomp.getID(tool))) {
                        isValid = true;
                    } else {
                        isValid = false;
                    }
                }
            }
        } else {
                isValid = false;
            }

        }


    return isValid;
    }

    public String getEnchant(ItemStack materialComp,ItemStack tool) {
        List<String> validEnchants = new ArrayList<>();

        if (materialDataYml.get(materialComp.getType().toString()) instanceof String) {
            validEnchants.add(materialDataYml.getString(materialComp.getType().toString()));
        } else if (materialDataYml.get(materialComp.getType().toString()) instanceof List) {
            validEnchants.addAll(materialDataYml.getStringList(materialComp.getType().toString()));
        }

        //ItemsAdder Compatibility
        if (plugin.itemsadderLoaded) {
            ItemsAdderCompat iacomp = new ItemsAdderCompat();
            if (iacomp.isCustomItem(materialComp)) {
                if (materialDataYml.get(iacomp.getID(materialComp).replace(":","-")) instanceof String) {
                    validEnchants.add(materialDataYml.getString(iacomp.getID(materialComp).replace(":","-")));
                } else if (materialDataYml.get(iacomp.getID(materialComp).replace(":","-")) instanceof List) {
                    validEnchants.addAll(materialDataYml.getStringList(iacomp.getID(materialComp).replace(":","-")));
                } else {
                }
            }
        }

            if (validEnchants.size()>1) {
                validEnchants.forEach(ench -> {
                    if (isValidEnchItemVanilla(tool, ench)) {
                        enchant = ench;
                    }
                });
            } else if (validEnchants.size()==1) {
                if (isValidEnchItemVanilla(tool,validEnchants.get(0))) {
                    enchant = validEnchants.get(0);
                } else {
                    enchant = null;
                }

            } else {
                enchant = null;
            }

        return enchant;
    }

    public int getExpCost(String ench,int level) {
        enchCost = enchantsYml.getInt(ench+"."+level+".exp-cost");
        return enchCost;
    }

    public int getMaterialCost(String ench,int level) {
        matCost = enchantsYml.getInt(ench+"."+level+".material-cost");
        return matCost;
    }


}
