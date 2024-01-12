package de.harrisblog.nms.effects;

import de.harrisblog.nms.data.CustomEffect;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nullable;

public class AddDurabilityEffect extends CustomEffect {

    public AddDurabilityEffect(){
        super("ADD_DURABILITY", "ADD_DURABILITY:[AMOUNT]:[CHANCE]", "heal players armor durability by specified amount", "DEFENSE");
    }

    @Override
    public void cancel(Event paramEvent, String[] paramArrayOfString) {

    }

    @Override
    public void run(Event event, String[] args, @Nullable Player p) {
        if(!(event instanceof EntityDamageByEntityEvent)) return;
        EntityDamageEvent e = (EntityDamageByEntityEvent) event;
        if(!(e.getEntity() instanceof Player)) return;
        Player player = (Player) e.getEntity();
        int repairAmount = Integer.parseInt(args[0]);
        double chance = Double.parseDouble(args[1]);
        double rnd = Math.random();
        ItemStack[] armor = player.getInventory().getArmorContents();
        if(rnd < chance){
            for(ItemStack i : armor){
                if(i != null && !i.getType().equals(Material.AIR)){
                    Damageable dmgable = (Damageable) i.getItemMeta();
                    dmgable.setDamage(dmgable.getDamage()-repairAmount);
                    i.setItemMeta((ItemMeta) dmgable);
                }
            }
        }
    }
}
