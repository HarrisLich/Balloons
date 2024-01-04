package de.harrisblog.nms.effects;

import de.harrisblog.nms.data.CustomEffect;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerMoveEvent;

import javax.annotation.Nullable;

public class GodSpeedEffect extends CustomEffect {

    public GodSpeedEffect(){
        super("GODSPEED", "GODSPEED:[chance]:[#ofTargets]:[dmg]", "Has a chance to strike entities around the player if they are running", "MOVE");
    }
    public void run(Event event, String[] args, @Nullable Player p){
        if(!(event instanceof PlayerMoveEvent)) return;
        PlayerMoveEvent e = (PlayerMoveEvent) event;
        if(e.getPlayer().isSprinting()){
            double rnd = Math.random();
            double chance = Double.parseDouble(args[0]);
            int numTargets = Integer.parseInt(args[1]);
            double dmg = Double.parseDouble(args[2]);
            if(rnd < chance){
                int strikes = 0;
                for(Entity entity : e.getPlayer().getNearbyEntities(7,7,7)){
                    if(entity instanceof Player){
                        Player player = (Player) entity;
                        if(!(player.equals(e.getPlayer())) && strikes < numTargets){
                            player.getWorld().strikeLightningEffect(player.getLocation());
                            player.damage(dmg);
                            strikes += 1;
                        }
                    }else if(!entity.getType().equals(EntityType.BAT) && !entity.getType().equals(EntityType.ARMOR_STAND) && strikes < numTargets){
                        if(entity instanceof LivingEntity){
                            LivingEntity livingEntity = (LivingEntity) entity;
                            livingEntity.getWorld().strikeLightningEffect(livingEntity.getLocation());
                            livingEntity.damage(dmg);
                            strikes += 1;
                        }
                    }
                }
            }
        }
    }
    public void cancel(Event event, String[] args){

    }
}
