package de.harrisblog.nms.managers;

import de.harrisblog.nms.Nms;
import de.harrisblog.nms.NmsUtil;
import de.harrisblog.nms.data.Balloon;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BalloonsManager {
    private static Plugin plugin;
    private HashMap<String, Balloon> balloons;


    public BalloonsManager(){
        plugin = Nms.getPlugin();
        loadBalloons();


    }

    public HashMap<String, Balloon> getBalloons() {
        return balloons;
    }

    public void loadBalloons(){
        balloons = new HashMap<>();
        for(String key : plugin.getConfig().getConfigurationSection("balloons").getKeys(false)){
            Balloon balloon = new Balloon(plugin.getConfig().getString("balloons." + key + ".name"),
                    plugin.getConfig().getStringList("balloons." + key + ".lore"),
                    plugin.getConfig().getString("balloons." + key + ".skull_value"),
                    key,
                    plugin.getConfig().getStringList("balloons." + key + ".effects"),
                    plugin.getConfig().getStringList("balloons." + key + ".appliesTo"));
            balloons.put(key, balloon);
        }
    }

    public Balloon getAppliedBalloonFromItem(ItemStack i){
        if(i != null && i.hasItemMeta() && i.getItemMeta().hasLore()){
            List<String> lore = i.getItemMeta().getLore();
            for(String s : lore){
                if(s.contains(NmsUtil.format("&4&lBalloon ("))){
                    String s1 = s.substring(13, s.length() - 5);
                    for(Balloon balloon : balloons.values()){
                        if(balloon.getName().equalsIgnoreCase(s1)){
                            return balloon;

                        }
                    }
                }
            }
        }
        return null;
    }

    public ArrayList<Balloon> getBalloonsAsList(){
        ArrayList<Balloon> balloons1 = new ArrayList<>();
        balloons1.addAll(balloons.values());
        return balloons1;
    }

    public Balloon getBalloonFromItem(ItemStack i){
        for(Balloon balloon : this.balloons.values()){
            if(i.getItemMeta().getDisplayName().equalsIgnoreCase(balloon.getName())){
                return balloon;
            }
        }
        return null;
    }

    public Balloon getBalloonFromKey(String key) {
        for (Map.Entry<String, Balloon> entry : this.balloons.entrySet()) {
            if (key.equalsIgnoreCase(entry.getKey()))
                return entry.getValue();
        }
        return null;
    }

}
