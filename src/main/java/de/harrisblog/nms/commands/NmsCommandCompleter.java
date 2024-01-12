package de.harrisblog.nms.commands;

import de.harrisblog.nms.Nms;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class NmsCommandCompleter implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {

        if(args.length == 1){
            List<String> strs = new ArrayList<>();

            strs.add("reload");
            strs.add("effects");
            strs.add("give");
            strs.add("admin");
            strs.add("help");


            return strs;
        }else if(args.length == 2 && args[0].equalsIgnoreCase("give")){
            List<String> strs = new ArrayList<>();

            for(String s1 : Nms.getPlugin().getConfig().getConfigurationSection("balloons").getKeys(false)){
                strs.add(s1);
            }

            return strs;
        }else if(args.length == 2){
            List<String> empty = new ArrayList<>();
            return empty;
        }
        return null;
    }
}
