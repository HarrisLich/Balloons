package de.harrisblog.nms;

import de.harrisblog.nms.commands.NmsCommand;
import de.harrisblog.nms.commands.NmsCommandCompleter;
import de.harrisblog.nms.data.Balloon;
import de.harrisblog.nms.data.CustomEffect;
import de.harrisblog.nms.data.EffectType;
import de.harrisblog.nms.listeners.*;
import de.harrisblog.nms.managers.BalloonsManager;
import de.harrisblog.nms.managers.CustomEffectsManager;
import de.harrisblog.nms.versions.spigot1_19_4.NmsUtil1_19_4;
import org.bukkit.Bukkit;
import org.bukkit.entity.*;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public final class Nms extends JavaPlugin {

    //TODO
    //Swapping between two balloons doesnt switch which balloon appears
    //
    //ABILITIES
    //DAMAGE_ARMOR:[AMOUNT]:[CHANCE] damage players armor durability by specified amount
    //
    //DROPS:[MULTIPLIER]:[CHANCE] chance to multiply mob drops
    //EXP -> rework


    //UPDATE
    //Removed NBT API Dependency
    //New Effects
    //Command completer


    private static Plugin plugin;
    private static CustomEffectsManager customEffectsManager;
    private static BalloonsManager balloonsManager;
    private static HashMap<Player, ArmorStand> armorStands;

    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();
        plugin = this;
        armorStands = new HashMap<>();
        customEffectsManager = new CustomEffectsManager();
        balloonsManager = new BalloonsManager();
        getCommand("balloons").setExecutor(new NmsCommand());
        getCommand("balloons").setTabCompleter(new NmsCommandCompleter());

        registerListeners();

        Bukkit.getScheduler().runTaskTimer(plugin, new Runnable() {
            @Override
            public void run() {
                for(Player p : plugin.getServer().getOnlinePlayers()){
                    NmsUtil1_19_4.balloonCleanup(p);
                    ItemStack itemInHand = p.getItemInHand();
                    if(itemInHand != null && itemInHand.hasItemMeta() && itemInHand.getItemMeta().hasLore()){
                        if(NmsUtil1_19_4.hasBalloonApplied(itemInHand)){
                            //player is holding a item with a balloon applied
                            boolean hasNearbyBalloon = false;
                            for(Entity nearby : p.getNearbyEntities(10,10,10)){
                                if(nearby.getType().equals(EntityType.BAT) && nearby.getName().equalsIgnoreCase(p.getName())){
                                    hasNearbyBalloon = true;
                                }
                            }
                            if(!hasNearbyBalloon){
                                NmsUtil1_19_4.spawnBalloon(p, itemInHand);
                            }
                            NmsUtil1_19_4.teleportBalloonToPlayer(p);
                            //Run passive effects
                            Balloon balloon = balloonsManager.getAppliedBalloonFromItem(itemInHand);
                            if(balloon == null) return;
                            HashMap<String, CustomEffect> effects = balloon.getEffects();
                            for(CustomEffect effect: effects.values()){
                                if(effect != null && effect.getType().equals(EffectType.PASSIVE)){
                                    Nms.getCustomEffectsManager().runCustomEffect(effect, (Event) null, balloon, p);
                                }
                            }
                        }
                    }

                    //Balloon Cleanup

                }
            }
        }, 20L, 1L);
    }



    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static HashMap<Player, ArmorStand> getArmorStands() {
        return armorStands;
    }

    public static CustomEffectsManager getCustomEffectsManager() {
        return customEffectsManager;
    }

    public static BalloonsManager getBalloonsManager() {
        return balloonsManager;
    }

    private void registerListeners(){
        plugin.getServer().getPluginManager().registerEvents(new BalloonApplyListener(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new BalloonEventListener(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new AdminGuiListener(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new GuiListener(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new PlayerDisconnect(), plugin);
    }

    public static Plugin getPlugin() {
        return plugin;
    }
}
