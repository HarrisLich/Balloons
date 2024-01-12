package de.harrisblog.nms.effects;

import de.harrisblog.nms.data.CustomEffect;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import javax.annotation.Nullable;

public class ReductionEffect extends CustomEffect {
    public ReductionEffect(){
        super("REDUCTION", "REUCTION:[0.0-1.0]:[CHANCE]", "chance to reduce incoming damage", "DEFENSE");
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
        double reduction = Double.parseDouble(args[0]);
        double chance = Double.parseDouble(args[1]);
        double rnd = Math.random();
        if(rnd < chance){
            e.setDamage(e.getDamage() - (e.getDamage() * reduction));
        }
    }
}
