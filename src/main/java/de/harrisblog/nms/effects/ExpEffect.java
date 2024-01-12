package de.harrisblog.nms.effects;

import de.harrisblog.nms.data.CustomEffect;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;

import javax.annotation.Nullable;

public class ExpEffect extends CustomEffect {

    public ExpEffect(){
        super("EXP", "EXP:[AMOUNT]:[CHANCE]", "drops a number of exp orbs when a mob is killed", "MOB_KILL");
    }

    @Override
    public void cancel(Event paramEvent, String[] paramArrayOfString) {

    }

    @Override
    public void run(Event event, String[] args, @Nullable Player p) {
        if(!(event instanceof EntityDamageByEntityEvent)) return;
        EntityDamageByEntityEvent e = (EntityDamageByEntityEvent) event;
        if(!(e.getDamager() instanceof Player)) return;
        Player player = (Player) e.getDamager();
        Entity entity = e.getEntity();
        int count = Integer.parseInt(args[0]);
        double chance = Double.parseDouble(args[1]);
        double rnd = Math.random();
        if(chance < rnd){
            for(int i=0; i<count; i++){
                player.getWorld().spawnEntity(entity.getLocation(), EntityType.EXPERIENCE_ORB);
            }
        }
    }
}
