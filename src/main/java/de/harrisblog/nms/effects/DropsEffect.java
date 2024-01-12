package de.harrisblog.nms.effects;

import de.harrisblog.nms.data.CustomEffect;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import javax.annotation.Nullable;

public class DropsEffect extends CustomEffect {
    public DropsEffect(){
        super("DROPS", "DROPS:[0.0-1.0]:[CHANCE]", "chance to multiply mob drops", "MOB_KILL");
    }

    @Override
    public void cancel(Event paramEvent, String[] paramArrayOfString) {

    }

    @Override
    public void run(Event event, String[] args, @Nullable Player p) {

    }
}
