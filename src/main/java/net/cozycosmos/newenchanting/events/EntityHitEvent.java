package net.cozycosmos.newenchanting.events;

import net.cozycosmos.newenchanting.enchants.CustomEnchants;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class EntityHitEvent implements Listener {

    @EventHandler
    public void onEntityHit(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player) {
            Player p = (Player) e.getDamager();
            ItemStack tool = p.getInventory().getItemInMainHand();
            if (e.getEntity() instanceof LivingEntity) {
                if (tool.getItemMeta().hasEnchant(CustomEnchants.ICE_ASPECT)) {
                    int enchantLevel = tool.getEnchantmentLevel(CustomEnchants.ICE_ASPECT);
                    LivingEntity entity = (LivingEntity) e.getEntity();
                    entity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,((int)(1.5*enchantLevel)),1,false));
                }
            }


        }
    }

}





