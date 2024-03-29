package de.harrisblog.nms.commands;

import de.harrisblog.nms.Nms;
import de.harrisblog.nms.versions.spigot1_19_4.NmsUtil1_19_4;
import de.harrisblog.nms.data.Balloon;
import de.harrisblog.nms.data.CustomEffect;
import net.minecraft.nbt.NBTTagCompound;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftItemStack;
import org.bukkit.entity.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class NmsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(strings.length == 1 && strings[0].equalsIgnoreCase("admin") && (commandSender.hasPermission("balloons.admin") || commandSender.isOp() )){
            if(!(commandSender instanceof Player)) return false;
            Player p = (Player) commandSender;
            displayAdminGui(p);
            return true;
        }else if(strings.length == 1 && strings[0].equalsIgnoreCase("effects") && commandSender.hasPermission("balloons.effects")){
            for(CustomEffect effect : Nms.getCustomEffectsManager().getEffects().values()){
                commandSender.sendMessage(effect.getUsage());
            }
            return true;
        }else if(strings.length == 1 && strings[0].equalsIgnoreCase("help") && commandSender.hasPermission("balloons.help")){
            List<String> help = Nms.getPlugin().getConfig().getStringList("messages.help");
            for(String str : help){
                commandSender.sendMessage(NmsUtil1_19_4.format(str));
            }
            return true;
        }else if(strings.length == 2 && strings[0].equalsIgnoreCase("give") && commandSender.hasPermission("balloons.give")){
            String balloonKey = strings[1];
            if(!(commandSender instanceof Player)) return false;
            Player p = (Player) commandSender;
            Balloon balloon = Nms.getBalloonsManager().getBalloonFromKey(balloonKey);
            if(balloon == null) return false;
            if(balloon != null){
                p.getInventory().addItem(balloon.getBalloonItem());
            }
            return true;

         }else if(strings.length == 1 && strings[0].equalsIgnoreCase("reload") && commandSender.hasPermission("balloons.reload")){
            Nms.getPlugin().reloadConfig();
            Nms.getBalloonsManager().loadBalloons();
            commandSender.sendMessage(NmsUtil1_19_4.format("&aSuccessfully reloaded balloons config!"));
            return true;
        }else if(strings.length == 1 && strings[0].equalsIgnoreCase("nbt")){
            if(!(commandSender instanceof Player)) return false;
            Player p = (Player) commandSender;
            net.minecraft.world.item.ItemStack item = CraftItemStack.asNMSCopy(p.getItemInHand());
            NBTTagCompound nbt = item.v();
            for(String s1 : nbt.e()){
                p.sendMessage(s1);
            }
            return true;
        }

        if(!(commandSender instanceof Player)) return false;
        Player p = (Player) commandSender;
        displayGui(p);


        return true;
    }


    public void displayAdminGui(Player p){
        int numBalloons = Nms.getBalloonsManager().getBalloons().size();
        int guiPages = (int) Math.ceil(numBalloons / 21.0);
        List<Inventory> guiInventories = new ArrayList<>();
        int gui_page = 0;
        int balloon_index = 0;
        ItemStack placeholder = NmsUtil1_19_4.getPlaceholderItem();
        for(int i=0; i<guiPages; i++){
            Inventory inventory = Bukkit.createInventory(p, 45, NmsUtil1_19_4.format("&4&lBalloons Admin"));
            for(int j=0; j<10; j++){
                inventory.setItem(j, NmsUtil1_19_4.getPlaceholderItem());
            }
            inventory.setItem(17, placeholder);
            inventory.setItem(18, placeholder);
            inventory.setItem(26, placeholder);
            inventory.setItem(27, placeholder);
            inventory.setItem(35, placeholder);
            for(int j=36; j<45; j++) {
                inventory.setItem(j, placeholder);
            }
            //first 7 balloons
            for(int j=10; j<17; j++){
                if(balloon_index < Nms.getBalloonsManager().getBalloons().size()){
                    ArrayList<Balloon> balloons = Nms.getBalloonsManager().getBalloonsAsList();
                    inventory.setItem(j, balloons.get(balloon_index).getBalloonItem());
                    balloon_index += 1;
                }
            }
            for(int j=19; j<26; j++){
                if(balloon_index < Nms.getBalloonsManager().getBalloons().size()){
                    ArrayList<Balloon> balloons = Nms.getBalloonsManager().getBalloonsAsList();
                    inventory.setItem(j, balloons.get(balloon_index).getBalloonItem());
                    balloon_index += 1;
                }
            }
            for(int j=18; j<35; j++){
                if(balloon_index < Nms.getBalloonsManager().getBalloons().size()){
                    ArrayList<Balloon> balloons = Nms.getBalloonsManager().getBalloonsAsList();
                    inventory.setItem(j, balloons.get(balloon_index).getBalloonItem());
                    balloon_index += 1;
                }
            }

            guiInventories.add(inventory);
        }
        p.openInventory(guiInventories.get(gui_page));
    }

    public void displayGui(Player p){
        int numBalloons = Nms.getBalloonsManager().getBalloons().size();
        int guiPages = (int) Math.ceil(numBalloons / 21.0);
        List<Inventory> guiInventories = new ArrayList<>();
        int gui_page = 0;
        int balloon_index = 0;
        ItemStack placeholder = NmsUtil1_19_4.getPlaceholderItem();
        for(int i=0; i<guiPages; i++){
            Inventory inventory = Bukkit.createInventory(p, 45, NmsUtil1_19_4.format("&4&lBalloons"));
            for(int j=0; j<10; j++){
                inventory.setItem(j, NmsUtil1_19_4.getPlaceholderItem());
            }
            inventory.setItem(17, placeholder);
            inventory.setItem(18, placeholder);
            inventory.setItem(26, placeholder);
            inventory.setItem(27, placeholder);
            inventory.setItem(35, placeholder);
            for(int j=36; j<45; j++) {
                inventory.setItem(j, placeholder);
            }
            //first 7 balloons
            for(int j=10; j<17; j++){
                if(balloon_index < Nms.getBalloonsManager().getBalloons().size()){
                    ArrayList<Balloon> balloons = Nms.getBalloonsManager().getBalloonsAsList();
                    inventory.setItem(j, balloons.get(balloon_index).getBalloonItem());
                    balloon_index += 1;
                }
            }
            for(int j=19; j<26; j++){
                if(balloon_index < Nms.getBalloonsManager().getBalloons().size()){
                    ArrayList<Balloon> balloons = Nms.getBalloonsManager().getBalloonsAsList();
                    inventory.setItem(j, balloons.get(balloon_index).getBalloonItem());
                    balloon_index += 1;
                }
            }
            for(int j=18; j<35; j++){
                if(balloon_index < Nms.getBalloonsManager().getBalloons().size()){
                    ArrayList<Balloon> balloons = Nms.getBalloonsManager().getBalloonsAsList();
                    inventory.setItem(j, balloons.get(balloon_index).getBalloonItem());
                    balloon_index += 1;
                }
            }

            guiInventories.add(inventory);
        }
        p.openInventory(guiInventories.get(gui_page));
    }
}
