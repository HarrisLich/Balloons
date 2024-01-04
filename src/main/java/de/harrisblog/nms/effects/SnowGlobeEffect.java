package de.harrisblog.nms.effects;

import de.harrisblog.nms.data.CustomEffect;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import javax.annotation.Nullable;
import java.util.List;

public class SnowGlobeEffect extends CustomEffect {
    public SnowGlobeEffect(){
        super("SNOWGLOBE", "SNOWGLOBE:[CHANCE]:[#OFTARGETS]:[AMPLIFIER]:[DURATION]", "Slows down enemies in a radius around wielder", "DEFENSE");
    }

    @Override
    public void run(Event event, String[] args, @Nullable Player p) {
        if(!(event instanceof EntityDamageEvent)) return;
        EntityDamageEvent e = (EntityDamageEvent) event;
        if(!(e.getEntity() instanceof Player)) return;
        Player player = (Player) e.getEntity();
        List<Entity> entityList = player.getNearbyEntities(7,7,7);
        int numTargets = Integer.parseInt(args[1]);
        Double chance = Double.parseDouble(args[0]);
        Double rnd = Math.random();
        int duration = (int) Math.floor(Double.parseDouble(args[3]) * 20.0);
        int strikes = 0;
        int amplifier = Integer.parseInt(args[2]);
        if(rnd < chance) {
            for (Entity entity : entityList) {
                if (entity instanceof Player) {
                    Player p1 = (Player) entity;
                    if (!p1.equals(player) && strikes < numTargets) {

                        p1.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, duration, amplifier));
                        strikes += 1;
                    }
                } else if (!entity.getType().equals(EntityType.BAT) && !entity.getType().equals(EntityType.ARMOR_STAND) && entity instanceof LivingEntity) {

                    LivingEntity livingEntity = (LivingEntity) entity;
                    livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, duration, amplifier));
                    strikes += 1;
                }
            }
        }



    }

    @Override
    public void cancel(Event event, String[] args) {

    }
}
