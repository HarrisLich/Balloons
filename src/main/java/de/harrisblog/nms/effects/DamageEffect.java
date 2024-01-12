package de.harrisblog.nms.effects;

import de.harrisblog.nms.data.CustomEffect;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import javax.annotation.Nullable;

public class DamageEffect extends CustomEffect {
    public DamageEffect(){
        super("DAMAGE", "DAMAGE:[MULTIPLIER]:[CHANCE]", "Increases attack by chosen multiplier", "ATTACK");
    }

    public void run(Event event, String[] args, @Nullable Player player){
        EntityDamageByEntityEvent e = (EntityDamageByEntityEvent) event;
        if(!(e.getDamager() instanceof Player)) return;
        Player p = (Player) e.getDamager();
        double dmg = e.getDamage();
        double chance = Double.parseDouble(args[1]);
        double rnd = Math.random();
        if(rnd < chance){
            dmg = (dmg * Double.parseDouble(args[0]));
            e.setDamage(dmg);
        }
    }

    public void cancel(Event event, String[] args){

    }
}
