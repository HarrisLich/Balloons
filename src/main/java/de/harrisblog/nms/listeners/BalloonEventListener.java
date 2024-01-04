package de.harrisblog.nms.listeners;

import de.harrisblog.nms.Nms;
import de.harrisblog.nms.data.Balloon;
import de.harrisblog.nms.data.CustomEffect;
import de.harrisblog.nms.data.EffectType;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class BalloonEventListener implements Listener {
    @EventHandler
    public void onMoveEvent(PlayerMoveEvent event){
        if(!(event.getPlayer() instanceof Player)) return;
        Player p = event.getPlayer();
        //check if player has a balloon summoned
        if(Nms.getArmorStands().containsKey(p)){
            //Item in hand will always have a balloon because armor stand is summoned
            ItemStack i = p.getItemInHand();
            Balloon balloon = Nms.getBalloonsManager().getAppliedBalloonFromItem(i);
            if(balloon == null) return;
            if(!balloon.getAppliesTo().contains(i.getType())) return;
            HashMap<String, CustomEffect> effects = balloon.getEffects();
            for(CustomEffect effect: effects.values()){
                if(effect != null && effect.getType().equals(EffectType.MOVE)){
                    Nms.getCustomEffectsManager().runCustomEffect(effect, (Event) event, balloon, null);
                }
            }

        }
    }
    @EventHandler
    public void onPlayerAttack(EntityDamageByEntityEvent event){
        if(!(event.getDamager() instanceof Player)) return;
        Player p = (Player) event.getDamager();
        //check if player has a balloon summoned
        if(Nms.getArmorStands().containsKey(p)){
            //Item in hand will always have a balloon because armor stand is summoned
            ItemStack i = p.getItemInHand();
            Balloon balloon = Nms.getBalloonsManager().getAppliedBalloonFromItem(i);
            if(balloon == null) return;
            HashMap<String, CustomEffect> effects = balloon.getEffects();
            for(CustomEffect effect: effects.values()){
                if(effect != null && effect.getType().equals(EffectType.ATTACK)){
                    Nms.getCustomEffectsManager().runCustomEffect(effect, (Event) event, balloon, null);
                }
            }

        }
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event){
        if(!(event.getEntity() instanceof Player)) return;
        Player p = (Player) event.getEntity();
        if(Nms.getArmorStands().containsKey(p)){
            ItemStack i = p.getItemInHand();
            Balloon balloon = Nms.getBalloonsManager().getAppliedBalloonFromItem(i);
            if(balloon == null) return;
            HashMap<String, CustomEffect> effects = balloon.getEffects();
            for(CustomEffect effect: effects.values()){
                if(effect != null && effect.getType().equals(EffectType.DEFENSE)){
                    Nms.getCustomEffectsManager().runCustomEffect(effect, (Event) event, balloon, p);
                }
            }
        }
    }


}
