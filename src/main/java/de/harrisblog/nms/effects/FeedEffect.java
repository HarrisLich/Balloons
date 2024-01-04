package de.harrisblog.nms.effects;

import de.harrisblog.nms.data.CustomEffect;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerMoveEvent;

import javax.annotation.Nullable;

public class FeedEffect extends CustomEffect {
    public FeedEffect(){
        super("FEED", "FEED:[amount]:[chance (0.0 - 1.0)]", "feeds the player", "MOVE");
    }
    public void run(Event event, String[] args, @Nullable Player player){
        if(!(event instanceof PlayerMoveEvent)) return;
        PlayerMoveEvent e = (PlayerMoveEvent) event;
        Player p = e.getPlayer();
        int food = p.getFoodLevel();
        int feedAmount = Integer.parseInt(args[0]);
        int toSet = food + feedAmount;
        double feedChance = Double.parseDouble(args[1]);
        double rnd = Math.random();
        if(rnd < feedChance) {
            p.setFoodLevel(toSet);
        }
    }
    public void cancel(Event event, String[] args){

    }


}
