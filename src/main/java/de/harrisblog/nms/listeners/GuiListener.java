package de.harrisblog.nms.listeners;

import de.harrisblog.nms.NmsUtil;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class GuiListener implements Listener {
    @EventHandler
    public void onClick(InventoryClickEvent event){
        if(event.getView().getTitle().equalsIgnoreCase(NmsUtil.format("&4&lBalloons"))){
            event.setCancelled(true);
        }
    }
}
