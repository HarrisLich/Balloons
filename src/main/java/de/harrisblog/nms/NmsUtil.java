package de.harrisblog.nms;

import de.harrisblog.nms.data.Balloon;
import de.harrisblog.nms.data.EffectType;
import net.minecraft.network.protocol.game.PacketPlayOutAttachEntity;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.world.damagesource.DamageSource;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftPlayer;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class NmsUtil {
    public static Object getNMSEntity(Entity entity) {
        return ((CraftEntity) entity).getHandle();
    }
    public static Object getNMSPlayer(Player player) {
        return ((CraftPlayer) player).getHandle();
    }
    public static EffectType getEffectTypeFromString(String s) {
        switch (s) {
            case "ATTACK":
                return EffectType.ATTACK;
            case "DEFENSE":
                return EffectType.DEFENSE;
            case "EAT":
                return EffectType.EAT;
            case "MOVE":
                return EffectType.MOVE;
            case "DEATH":
                return EffectType.DEATH;
            case "SWIM":
                return EffectType.SWIM;
            case "PASSIVE":
                return EffectType.PASSIVE;
        }
        return null;
    }
    public static void spawnBalloon(Player p, ItemStack i){
        Location l = p.getLocation();
        l.add(0.0, 3.0, 0.0);
        Entity batEntity = p.getWorld().spawnEntity(l, EntityType.BAT);
        Bat bat = (Bat) batEntity;
        bat.setAwake(false);
        bat.setCustomName(p.getName());
        bat.setInvulnerable(true);
        bat.setInvisible(true);
        Location l2 = l.add(0.0, -1.0, 0.0);
        ArmorStand armorStand = (ArmorStand) p.getWorld().spawnEntity(l2, EntityType.ARMOR_STAND);
        Balloon balloon = Nms.getBalloonsManager().getAppliedBalloonFromItem(i);
        armorStand.setCustomName(p.getName());
        armorStand.setGravity(false);
        armorStand.setVisible(false);
        armorStand.setSmall(true);
        armorStand.setInvulnerable(true);
        armorStand.setHelmet(balloon.getBalloonItem());
        Nms.getArmorStands().put(p, armorStand);



    }

    public static void balloonCleanup(Player p){
        for(Entity entity : p.getNearbyEntities(10, 10, 10)){
            if(entity.getType().equals(EntityType.BAT) && entity.getName().equalsIgnoreCase(p.getName())){
                if(!p.getItemInHand().hasItemMeta()){
                    entity.remove();
                    Nms.getPlugin().getLogger().info("Balloon Removed");
                    ArmorStand armorStand = Nms.getArmorStands().get(p);
                    armorStand.remove();
                }
                if(p.getItemInHand().hasItemMeta() && !(p.getItemInHand().getItemMeta().hasLore())){
                    entity.remove();
                    Nms.getPlugin().getLogger().info("Balloon Removed");
                    ArmorStand armorStand = Nms.getArmorStands().get(p);
                    armorStand.remove();
                }
            }
        }
    }
    public static void teleportBalloonToPlayer(Player p){
        for(Entity nearby : p.getNearbyEntities(10,10,10)){
            if((nearby.getName().equalsIgnoreCase(p.getName()) && nearby.getType() == EntityType.BAT) || (nearby.getName().equalsIgnoreCase(p.getName()) && nearby.getType() == EntityType.ARMOR_STAND)){
                //Send packet to attach bat to a lead and to the player
                if(nearby.getType() == EntityType.BAT){
                    Bat bat = (Bat) nearby;
                    bat.setAwake(false);
                    bat.setAware(false);
                    Location l = p.getLocation();
                    l.add(0.0, 3.0, 0.0);
                    bat.teleport(l);
                    sendAttachPacket(p, nearby);
                }else if(nearby.getType() == EntityType.ARMOR_STAND){
                    if(!Nms.getArmorStands().containsValue(nearby)) Nms.getArmorStands().put(p, (ArmorStand) nearby);
                    ArmorStand armorStand = (ArmorStand) nearby;
                    Location l = p.getLocation();
                    l.add(0.0, 2.8, 0.0);
                    armorStand.teleport(l);
                }

            }
        }
    }
    private static void sendAttachPacket(Player p, Entity nearby){

        PacketPlayOutAttachEntity packet = new PacketPlayOutAttachEntity((net.minecraft.world.entity.Entity) NmsUtil.getNMSEntity(nearby), (net.minecraft.world.entity.Entity) NmsUtil.getNMSPlayer(p));
        EntityPlayer entityPlayer = ((CraftPlayer) p).getHandle();
        entityPlayer.b.a(packet);

    }
    public static boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    public static boolean hasBalloonApplied(ItemStack i){
        if(i != null && i.hasItemMeta() && i.getItemMeta().hasLore()){
            List<String> lore = i.getItemMeta().getLore();
            for(String s : lore){
                if(s.contains(NmsUtil.format("&4&lBalloon ("))){
                    return true;
                }
            }
        }
        return false;
    }
    public static ItemStack getPlaceholderItem() {
        ItemStack placeholder = new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1);
        ItemMeta phMeta = placeholder.getItemMeta();
        phMeta.setDisplayName(" ");
        placeholder.setItemMeta(phMeta);
        return placeholder;
    }
    public static EntityDamageEvent.DamageCause parseDamageCause(String s){
        switch(s){
            case "FALL_DAMAGE":
                return EntityDamageEvent.DamageCause.FALL;
        }
        return null;
    }
    public static String format(String format) {
        return ChatColor.translateAlternateColorCodes('&', format);
    }
}
