package de.harrisblog.nms.effects;

import de.harrisblog.nms.data.CustomEffect;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffectType;

import javax.annotation.Nullable;

public class PotionEffect extends CustomEffect {
    public PotionEffect() {
        super("POTION", "POTION:[effect]:[amplifier]:[duration]:[chance (0.0 - 1.0)]", "gives the player a potion effect", "PASSIVE");
    }

    public void cancel(Event event, String[] args) {

    }

    public void run(Event event, String[] args, Player p) {
        PotionEffectType effect = PotionEffectType.getByName(args[0]);
        int amplifier = Integer.parseInt(args[1]);
        int duration = Integer.parseInt(args[2]);
        if (duration == -1)
            duration = Integer.MAX_VALUE;

        double chance = Math.random();
        if(chance < Double.parseDouble(args[3])){
            p.addPotionEffect(new org.bukkit.potion.PotionEffect(effect, duration, amplifier));
        }
    }
}
