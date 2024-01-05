package de.harrisblog.nms.listeners;

import de.harrisblog.nms.Nms;
import de.harrisblog.nms.NmsUtil;
import de.harrisblog.nms.data.Balloon;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class BalloonApplyListener implements Listener {
    @EventHandler
    public void onInventory(InventoryClickEvent e){
        if(!(e.getWhoClicked() instanceof Player)) return;
        Player p = (Player) e.getWhoClicked();
        ItemStack itemClicked = e.getCurrentItem();
        ItemStack itemHeld = e.getCursor();
        if(itemHeld != null && itemClicked != null && itemHeld.getItemMeta() != null && itemClicked.getItemMeta() != null){
            if(NmsUtil.hasBalloonApplied(itemClicked)) {
                p.sendMessage(NmsUtil.format(Nms.getPlugin().getConfig().getString("messages.balloon_already_applied")));
                return;
            }
            Balloon balloon = Nms.getBalloonsManager().getBalloonFromItem(itemHeld);
            if(balloon == null) return;
            NBTItem nbtItem = new NBTItem(itemHeld);
            if(nbtItem.hasTag("balloon") && balloon.getAppliesTo().contains(itemClicked.getType()) && !NmsUtil.hasBalloonApplied(itemClicked)){
                //We can apply the balloon onto the item
                nbtItem.clearNBT();
                NBTItem nbtItem1 = new NBTItem(itemClicked);
                nbtItem1.setString("balloon", "hasBalloon");
                ItemMeta meta = itemClicked.getItemMeta();
                if(meta.hasLore()){
                    List<String> lore = meta.getLore();
                    lore.add("");
                    lore.add(NmsUtil.format("&4&lBalloon (") + balloon.getName() + NmsUtil.format("&4&l)"));
                    meta.setLore(lore);
                }else{
                    List<String> lore = new ArrayList<>();
                    lore.add("");
                    lore.add(NmsUtil.format("&4&lBalloon (") + balloon.getName() + NmsUtil.format("&4&l)"));
                    meta.setLore(lore);
                }
                p.setItemOnCursor(new ItemStack(Material.AIR));
                itemClicked.setItemMeta(meta);
                p.getInventory().setItem(e.getSlot(), itemClicked);
                p.updateInventory();
                p.sendMessage(NmsUtil.format(Nms.getPlugin().getConfig().getString("messages.on_apply")));
            }
        }
    }
}
