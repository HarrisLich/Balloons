package de.harrisblog.nms.effects;

import de.harrisblog.nms.data.CustomEffect;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffectType;

import javax.annotation.Nullable;

public class CureAttackEffect extends CustomEffect {
    public CureAttackEffect(){
        super("CUREATTACK", "CUREATTACK:[EFFECT]:[PLAYER/TARGET]:[CHANCE]", "remove a potion effect while attacking", "ATTACK");
    }

    @Override
    public void cancel(Event event, String[] args) {

    }

    @Override
    public void run(Event event, String[] args, @Nullable Player p) {
        if(!(event instanceof EntityDamageByEntityEvent)) return;
        EntityDamageByEntityEvent e = (EntityDamageByEntityEvent) event;
        if(!(e.getDamager() instanceof Player)) return;
        Player player = (Player) e.getDamager();
        PotionEffectType effectType = PotionEffectType.getByName(args[0]);
        String target = args[1];
        double chance = Double.parseDouble(args[2]);
        double rnd = Math.random();
        if(rnd < chance){
            if(target.equalsIgnoreCase("player")){
                player.removePotionEffect(effectType);
            }else if(target.equalsIgnoreCase("target")){
                if(e.getEntity() instanceof Player){
                    Player p1 = (Player) e.getEntity();
                    p1.removePotionEffect(effectType);
                }
            }
        }
    }
}
