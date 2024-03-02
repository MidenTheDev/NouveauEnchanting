package net.cozycosmos.newenchanting;

import net.cozycosmos.newenchanting.commands.Core;
import net.cozycosmos.newenchanting.events.AnvilInvInteract;
import net.cozycosmos.newenchanting.events.EnchantingInvInteract;
import net.cozycosmos.newenchanting.events.InvClose;
import net.cozycosmos.newenchanting.events.InventoryOpen;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class Main extends JavaPlugin {

    PluginManager pm;
    ConsoleCommandSender cs;

    File enchants = new File(getDataFolder()+"/enchants.yml");
    FileConfiguration enchantsYml;
    File shortcuts = new File(getDataFolder()+"/shortcuts.yml");


    File materialData = new File(getDataFolder()+"/data/materialdata.yml");
    FileConfiguration materialDataYml;
    File enchantItemData = new File(getDataFolder()+"/data/enchantitemdata.yml");
    FileConfiguration enchantItemDataYml;
    FileConfiguration shortcutsYml;

    public boolean itemsadderLoaded = false;
    public boolean oraxenLoaded = false;

    @Override
    public void onEnable() {
        pm = this.getServer().getPluginManager();
        cs = this.getServer().getConsoleSender();

        cs.sendMessage(ChatColor.DARK_PURPLE+"[Nouveau Enchanting]: "+ChatColor.GREEN+ "Enabling Nouveau Enchanting");

        if(!getDataFolder().exists()) {
            getDataFolder().mkdirs();
            cs.sendMessage(ChatColor.DARK_PURPLE+"[Nouveau Enchanting]: "+ChatColor.GREEN+ "Performing first-time setup");
        }
        setupCompats();
        registerConfigs();

        registerEvents();
        registerCommands();

    }

    @Override
    public void onDisable() {
        cs.sendMessage(ChatColor.DARK_PURPLE+"[Nouveau Enchanting]: "+ChatColor.RED+ "Disabling Nouveau Enchanting");
    }

    private void setupCompats() {
        if (pm.getPlugin("ItemsAdder") != null && pm.getPlugin("ItemsAdder").isEnabled()) {
            cs.sendMessage(ChatColor.DARK_PURPLE+"[Nouveau Enchanting]: "+ChatColor.GREEN+ "Enabling ItemsAdder Compatibility");
            itemsadderLoaded = true;
        }
        if (pm.getPlugin("Oraxen") != null && pm.getPlugin("Oraxen").isEnabled()) {
            cs.sendMessage(ChatColor.DARK_PURPLE+"[Nouveau Enchanting]: "+ChatColor.GREEN+ "Enabling Oraxen Compatibility");
            itemsadderLoaded = true;
        }
        if (oraxenLoaded && itemsadderLoaded) {
            cs.sendMessage(ChatColor.DARK_PURPLE+"[Nouveau Enchanting]: "+ChatColor.GREEN+ "Using two custom item plugins may cause issues! Please only use one for enchanting components if possible.");
        }
    }

    private void registerCommands() {
        getCommand("nouveauenchanting").setExecutor(new Core());
    }

    private void registerEvents() {
        pm.registerEvents(new EnchantingInvInteract(),this);
        pm.registerEvents(new InventoryOpen(),this);
        pm.registerEvents(new InvClose(), this);
        if (getConfig().getBoolean("block-enchanted-books",true)) {
            pm.registerEvents(new AnvilInvInteract(), this);
        }
    }

    private void registerConfigs() {
        saveDefaultConfig();

        if (!enchants.exists()) {
            this.saveResource("enchants.yml", false);
        }
        if (!shortcuts.exists()) {
            this.saveResource("shortcuts.yml", false);
        }

        this.saveResource("data/materialdata.yml",true);

        this.saveResource("data/enchantitemdata.yml",true);


        setupMaterialData();
        setupEnchantItemData();

    }

    public void setupMaterialData() {

        materialDataYml = YamlConfiguration.loadConfiguration(materialData);
        enchantsYml = YamlConfiguration.loadConfiguration(enchants);

        cs.sendMessage(ChatColor.DARK_PURPLE+"[Nouveau Enchanting]: "+ChatColor.GREEN+ "Setting up materialdata.yml");
        enchantsYml.getConfigurationSection("").getKeys(false).forEach(enchant -> {
            String mat = enchantsYml.getString(enchant+".material").replace(':','-');
            if (materialDataYml.get(mat) != null) {

                if (materialDataYml.get(mat) instanceof String) {
                    if (enchant.equals(materialDataYml.getString(mat))) {
                        //do nothing
                    } else {
                        List<String> enchants = new ArrayList<>();
                        enchants.add(enchant);
                        enchants.add(materialDataYml.getString(mat));
                        materialDataYml.set(mat,enchants);
                    }



                } else if (materialDataYml.get(mat) instanceof List) {
                    if (materialDataYml.getStringList(mat).contains(enchant)) {
                        //do nothing
                    } else {
                        List<String> enchants = new ArrayList<>();
                        enchants.add(enchant);
                        enchants.addAll(materialDataYml.getStringList(mat));
                        materialDataYml.set(mat,enchants);
                    }


                }
            } else {
                materialDataYml.set(mat,enchant);
            }
        });

        try {
            materialDataYml.save(materialData);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void setupEnchantItemData() {
        enchantsYml = YamlConfiguration.loadConfiguration(enchants);
        enchantItemDataYml = YamlConfiguration.loadConfiguration(enchantItemData);
        shortcutsYml = YamlConfiguration.loadConfiguration(shortcuts);

        cs.sendMessage(ChatColor.DARK_PURPLE+"[Nouveau Enchanting]: "+ChatColor.GREEN+ "Setting up enchantitemdata.yml");

        enchantsYml.getConfigurationSection("").getKeys(false).forEach(enchant -> {
            List<String> enchItems = enchantsYml.getStringList(enchant+".allowed-items");
            List<String> finalList = new ArrayList<>();

            for (Material mat : Material.values()) {
                if (enchItems.contains(mat.toString())) {
                    finalList.add(mat.toString());
                    enchItems.remove(mat.toString());
                }
            }




            enchItems.forEach(shortcut -> {
                if (shortcutsYml.get(shortcut) != null) {
                    finalList.addAll(shortcutsYml.getStringList(shortcut));
                } else {
                    if (itemsadderLoaded) {
                        finalList.add(shortcut);

                    }
                }

            });



            enchantItemDataYml.set(enchant,finalList);
        });

        try {
            enchantItemDataYml.save(enchantItemData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
