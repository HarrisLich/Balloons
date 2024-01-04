package de.harrisblog.nms.effects;

import de.harrisblog.nms.NmsUtil;
import de.harrisblog.nms.data.CustomEffect;
import net.minecraft.world.damagesource.DamageSource;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageEvent;

import javax.annotation.Nullable;

public class ImmunityEffect extends CustomEffect {
    public ImmunityEffect(){
        super("IMMUNITY", "IMMUNITY:[DAMAGE_SOURCE]:[CHANCE]", "grants immunity to a type of damage", "DEFENSE");
    }

    @Override
    public void run(Event event, String[] args, @Nullable Player p) {
        if(!(event instanceof EntityDamageEvent)) return;
        EntityDamageEvent damageEvent = (EntityDamageEvent) event;
        if(!(damageEvent.getEntity() instanceof Player)) return;
        Player player = (Player) damageEvent.getEntity();
        EntityDamageEvent.DamageCause damageSource = NmsUtil.parseDamageCause(args[0]);
        Double chance = Double.parseDouble(args[1]);
        Double rnd = Math.random();
        if(rnd < chance){
            if(damageEvent.getCause().equals(damageSource)){
                damageEvent.setCancelled(true);
            }
        }
    }

    @Override
    public void cancel(Event event, String[] args) {

    }
}
