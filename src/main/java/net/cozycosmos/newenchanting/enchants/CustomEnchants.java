package net.cozycosmos.newenchanting.enchants;

import net.cozycosmos.newenchanting.api.EnchantBuilder;
import org.bukkit.enchantments.Enchantment;

public class CustomEnchants {
    public static final Enchantment ICE_ASPECT = new EnchantBuilder("ice_aspect", "Ice Aspect", 2);
    public CustomEnchants() {
        EnchantBuilder.registerEnchantment(ICE_ASPECT);

    }
}
