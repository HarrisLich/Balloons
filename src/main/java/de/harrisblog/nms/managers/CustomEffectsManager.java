package de.harrisblog.nms.managers;

import de.harrisblog.nms.Nms;
import de.harrisblog.nms.data.Balloon;
import de.harrisblog.nms.data.CustomEffect;
import de.harrisblog.nms.effects.*;
import it.unimi.dsi.fastutil.Hash;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CustomEffectsManager {
    private HashMap<String, CustomEffect> customEffects;
    private static Plugin plugin;
    private Class[] classes;
    public CustomEffectsManager(){
        plugin = Nms.getPlugin();
        customEffects = new HashMap<>();
        this.classes = new Class[] { PotionEffect.class, FeedEffect.class, DamageEffect.class, GodSpeedEffect.class, ImmunityEffect.class, CharizardEffect.class,
        SnowGlobeEffect.class, ExpEffect.class, CureDefenseEffect.class, CureAttackEffect.class, AddDurabilityEffect.class, AddDurabilityHeldEffect.class,
        ReductionEffect.class, HealDefenseEffect.class};
        loadCustomEffects();
    }

    public void runCustomEffect(CustomEffect customEffect, Event event, Balloon balloon, Player player) {
        List<String> str = this.plugin.getConfig().getStringList("balloons." + balloon.getKey() + ".effects");
        List<String> args = new ArrayList<>();
        for (String s : str) {
            if (s.contains(customEffect.getName())) {
                String[] strs = s.split(":");
                for (String j : strs)
                    args.add(j);
            }
        }
        args.remove(0);
        customEffect.run(event, args.<String>toArray(new String[0]), player);
    }


    public HashMap<String, CustomEffect> getEffects() {
        return this.customEffects;
    }

    public CustomEffect getCustomEffectByKey(String key) {
        for (CustomEffect customEffect : this.customEffects.values()) {
            if (customEffect.getName().equalsIgnoreCase(key))
                return customEffect;
        }
        return null;
    }
    public void loadCustomEffects() {
        for (Class<CustomEffect> effectClass : this.classes) {
            try {
                CustomEffect customEffect = effectClass.newInstance();
                this.customEffects.put(customEffect.getName(), customEffect);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
