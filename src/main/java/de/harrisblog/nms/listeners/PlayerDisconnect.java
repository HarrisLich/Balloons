package de.harrisblog.nms.listeners;

import de.harrisblog.nms.Nms;
import de.harrisblog.nms.versions.spigot1_19_4.NmsUtil1_19_4;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerDisconnect implements Listener {
    @EventHandler
    public void onLeave(PlayerQuitEvent e){
        Player p = e.getPlayer();
        ItemStack i = p.getItemInHand();
        if(NmsUtil1_19_4.hasBalloonApplied(i)){
            for(Entity entity : p.getNearbyEntities(10, 10, 10)){
                if(entity.getName().equalsIgnoreCase(p.getName())){
                    if(entity.getType().equals(EntityType.ARMOR_STAND)) Nms.getArmorStands().remove(p);
                    entity.remove();
                }
            }
        }


    }
}
