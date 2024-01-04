package de.harrisblog.nms.effects;

import de.harrisblog.nms.Nms;
import de.harrisblog.nms.data.CustomEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageEvent;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CharizardEffect extends CustomEffect {
    public CharizardEffect(){
        super("CHARIZARD", "CHARIZARD:[#ofTargets]:[CHANCE]", "Charizard Balloon", "DEFENSE");
    }

    @Override
    public void run(Event event, String[] args, @Nullable Player p) {
        if(!(event instanceof EntityDamageEvent)) return;
        EntityDamageEvent damageEvent = (EntityDamageEvent) event;
        if(!(damageEvent.getEntity() instanceof Player)) return;
        Player player = (Player) damageEvent.getEntity();
        int targets_hit = 0;
        int max_targets = Integer.parseInt(args[0]);
        Double chance = Double.parseDouble(args[1]);
        Double rnd = Math.random();
        HashMap<Location, Material> lavaToReplace = new HashMap<>();
        if(rnd < chance){
            for(Entity e : player.getNearbyEntities(7,7,7)){
                if(!(e.getType().equals(EntityType.BAT)) && !(e.getType().equals(EntityType.ARMOR_STAND)) && targets_hit < max_targets){
                    if(e instanceof Player){
                        Player p1 = (Player) e;
                        if(!p1.equals(player)){
                            //spawn lava on player increase counter
                            Location l = p1.getLocation();
                            Material mat = p1.getWorld().getBlockAt(l).getType();
                            if(mat.equals(Material.LAVA)) mat = Material.AIR;
                            lavaToReplace.put(l, mat);
                            p1.getWorld().getBlockAt(l).setType(Material.LAVA);
                            targets_hit += 1;
                        }
                    }else{
                        //hit with leave, increase counter
                        Location l = e.getLocation();
                        Material mat = e.getWorld().getBlockAt(l).getType();
                        if(mat.equals(Material.LAVA)) mat = Material.AIR;
                        lavaToReplace.put(l, mat);
                        e.getWorld().getBlockAt(l).setType(Material.LAVA);
                        targets_hit += 1;
                    }
                }
            }
            Nms.getPlugin().getServer().getScheduler().runTaskLater(Nms.getPlugin(), new Runnable() {
                @Override
                public void run() {
                    for(Location l : lavaToReplace.keySet()){
                        Material m = lavaToReplace.get(l);
                        player.sendMessage("Lava replaced");
                        player.getWorld().getBlockAt(l).setType(m);
                    }

                }
            }, 30L);
        }
    }

    @Override
    public void cancel(Event event, String[] args) {

    }
}
