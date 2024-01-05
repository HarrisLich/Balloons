package de.harrisblog.nms.effects;

import de.harrisblog.nms.data.CustomEffect;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import javax.annotation.Nullable;

public class DamageEffect extends CustomEffect {
    public DamageEffect(){
        super("DAMAGE", "DAMAGE:[0.0-1.0]", "Increases attack by chosen %", "ATTACK");
    }

    public void run(Event event, String[] args, @Nullable Player player){
        EntityDamageByEntityEvent e = (EntityDamageByEntityEvent) event;
        if(!(e.getDamager() instanceof Player)) return;
        Player p = (Player) e.getDamager();
        double dmg = e.getDamage();
        p.sendMessage(Double.toString(dmg));
        dmg = dmg + (dmg * Double.parseDouble(args[0]));
        p.sendMessage("after: " + Double.toString(dmg));
        e.setDamage(dmg);

    }

    public void cancel(Event event, String[] args){

    }
}
