package de.harrisblog.nms.effects;

import de.harrisblog.nms.data.CustomEffect;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import javax.annotation.Nullable;

public class HealDefenseEffect extends CustomEffect {
    public HealDefenseEffect(){
        super("HEAL_DEF", "HEAL_DEF:[AMOUNT]:[CHANCE]", "heals the defending player", "DEFENSE");
    }

    @Override
    public void run(Event event, String[] args, @Nullable Player p) {
        if(!(event instanceof EntityDamageByEntityEvent)) return;
        EntityDamageByEntityEvent e = (EntityDamageByEntityEvent) event;
        if(!(e.getEntity() instanceof Player)) return;
        Player player = (Player) e.getEntity();
        double rnd = Math.random();
        double chance = Double.parseDouble(args[1]);
        double amount = Double.parseDouble(args[0]);
        if(rnd < chance){
            player.setHealth(player.getHealth() + amount);
        }
    }

    @Override
    public void cancel(Event paramEvent, String[] paramArrayOfString) {

    }
}
