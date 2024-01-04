package de.harrisblog.nms.listeners;

import de.harrisblog.nms.Nms;
import de.harrisblog.nms.NmsUtil;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class AdminGuiListener implements Listener {
    @EventHandler
    public void onClick(InventoryClickEvent event){
        if(event.getView().getTitle().equalsIgnoreCase(NmsUtil.format("&4&lBalloons Admin"))){
            if(event.getCurrentItem() == null || event.getCurrentItem().equals(NmsUtil.getPlaceholderItem()) || event.getCurrentItem().getType().equals(Material.AIR)){
                event.setCancelled(true);
            }else{
                ItemStack balloon = event.getCurrentItem();
                event.getWhoClicked().getInventory().addItem(balloon);
                event.setCancelled(true);
            }
        }
    }
}
