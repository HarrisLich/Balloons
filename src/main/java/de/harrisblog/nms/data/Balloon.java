package de.harrisblog.nms.data;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import de.harrisblog.nms.Nms;
import de.harrisblog.nms.NmsUtil;
import de.tr7zw.nbtapi.NBT;
import de.tr7zw.nbtapi.NBTItem;
import net.minecraft.nbt.NBTTagCompound;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Balloon {
    private String name;
    private List<String> lore;
    private String skull_value;
    private String key;
    private HashMap<String, CustomEffect> effects;
    private List<String> effectList;
    private ItemStack balloonItem;
    private NamespacedKey namespacedKey;
    private List<Material> appliesTo;

    public Balloon(String name, List<String> lore, String skull_value, String key, List<String> effectList, List<String> appliesToList){
        this.name = NmsUtil.format(name);
        this.lore = formatLore(lore);
        this.key = key;
        this.skull_value = skull_value;
        this.effectList = effectList;
        this.namespacedKey = new NamespacedKey(Nms.getPlugin(), this.key);
        appliesTo = new ArrayList<>();
        loadAppliesTo(appliesToList);
        balloonItem = createBalloonItem();
        effects = loadEffects();


    }

    public void loadAppliesTo(List<String> strs){
        for(String s:strs){
            if(s.equalsIgnoreCase("SWORD")){
                appliesTo.add(Material.NETHERITE_SWORD);
                appliesTo.add(Material.WOODEN_SWORD);
                appliesTo.add(Material.IRON_SWORD);
                appliesTo.add(Material.GOLDEN_SWORD);
                appliesTo.add(Material.DIAMOND_SWORD);
                appliesTo.add(Material.STONE_SWORD);
            }else if(s.equalsIgnoreCase("AXE")){
                appliesTo.add(Material.DIAMOND_AXE);
                appliesTo.add(Material.WOODEN_AXE);
                appliesTo.add(Material.IRON_AXE);
                appliesTo.add(Material.STONE_AXE);
                appliesTo.add(Material.GOLDEN_AXE);
                appliesTo.add(Material.NETHERITE_AXE);
            }
        }
    }

    public List<Material> getAppliesTo() {
        return appliesTo;
    }
    public NamespacedKey getNamespacedKey() {
        return namespacedKey;
    }
    public String getName() {
        return name;
    }
    public List<String> getLore() {
        return lore;
    }

    public String getSkull_value() {
        return skull_value;
    }

    public String getKey() {
        return key;
    }

    public HashMap<String, CustomEffect> getEffects() {
        return effects;
    }

    public HashMap<String, CustomEffect> loadEffects() {
        HashMap<String, CustomEffect> customEffects = new HashMap<>();
        for (String s : this.effectList) {
            String[] splt = s.split(":");
            CustomEffect customEffect = Nms.getCustomEffectsManager().getCustomEffectByKey(splt[0]);
            customEffects.put(splt[0], customEffect);
        }
        return customEffects;
    }

    private List<String> formatLore(List<String> strs) {
        List<String> l = new ArrayList<>();
        for (String s : strs)
            l.add(NmsUtil.format(s));
        return l;
    }

    public ItemStack createBalloonItem(){
        ItemStack i = new ItemStack(Material.PLAYER_HEAD);
        ItemMeta meta = i.getItemMeta();
        meta.setDisplayName(this.getName());
        meta.setLore(this.getLore());
        SkullMeta skullMeta = (SkullMeta) meta;
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);

        profile.getProperties().put("textures", new Property("textures", skull_value));

        try {
            Method mtd = skullMeta.getClass().getDeclaredMethod("setProfile", GameProfile.class);
            mtd.setAccessible(true);
            mtd.invoke(skullMeta, profile);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
            ex.printStackTrace();
        }
        i.setItemMeta(skullMeta);
        //set nbt
        NBTItem nbtItem = new NBTItem(i);
        nbtItem.setString("balloon", this.getName());
        nbtItem.applyNBT(i);

        return i;
    }

    public ItemStack getBalloonItem() {
        return balloonItem;
    }
}
