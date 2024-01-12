package de.harrisblog.nms.versions.spigot1_19_4;

import net.minecraft.nbt.NBTTagCompound;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

public class NBTUtil1_19_4 {
    public static ItemStack setNBT(ItemStack item, String key, String value){
        net.minecraft.world.item.ItemStack itemStack = CraftItemStack.asNMSCopy(item);
        NBTTagCompound tagCompound = itemStack.v();
        tagCompound.a(key, value);
        itemStack.b(tagCompound);
        item = CraftItemStack.asBukkitCopy(itemStack);
        return item;
    }
    public static ItemStack setNBT(ItemStack item , String key, int i){
        net.minecraft.world.item.ItemStack itemStack = CraftItemStack.asNMSCopy(item);
        NBTTagCompound tagCompound = itemStack.v();
        tagCompound.a(key, i);
        itemStack.b(tagCompound);
        item = CraftItemStack.asBukkitCopy(itemStack);
        return item;
    }

    public static boolean hasKey(ItemStack item, String key){
        net.minecraft.world.item.ItemStack itemStack = CraftItemStack.asNMSCopy(item);
        NBTTagCompound tagCompound = itemStack.v();
        return tagCompound.e(key);
    }
}
