package taewookim.commands;

import net.minecraft.world.level.World;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_20_R2.CraftWorld;
import org.bukkit.event.entity.CreatureSpawnEvent;
import taewookim.LGWTFM;
import taewookim.entity.Circle;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.*;

public class LGCMD implements CommandExecutor, TabCompleter {

    public void load(CommandSender sender, String string1, String string2) {
        sender.sendMessage("가져오는중");
        LGWTFM.listy.clear();
        LGWTFM.listx.clear();
        for(Circle cir : LGWTFM.circlelist) {
            cir.getBukkitEntity().remove();
        }
        LGWTFM.circlelist.clear();
        File file = new File(LGWTFM.plugin.getDataFolder() + string1);
        Properties pro = new Properties();
        int size = 0;
        try{
            InputStream stream = new FileInputStream(file.toString());
            pro.load(stream);
            size = Integer.parseInt(pro.getProperty("size"));
            for(int i = 0; i<size; i++) {
                LGWTFM.listx.add((double)Integer.parseInt(pro.getProperty("x" + i)));
                LGWTFM.listy.add((double)Integer.parseInt(pro.getProperty("y" + i)));
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
        int n = Integer.parseInt(string2);
        World w = ((CraftWorld)Bukkit.getWorld("world")).getHandle();
        for(int i = -250;i<250;i++) {
            if(i !=0 ) {
                double AnX = LGWTFM.AnfX(i);
                double AnY = LGWTFM.AnfY(i);
                double BnX = LGWTFM.BnfX(i);
                double BnY = LGWTFM.BnfY(i);
                double x = AnX-BnY;
                double y = BnX+AnY;
                x/=200.0D;
                y/=200.0D;
                Circle cir = new Circle(w, Math.sqrt(x*x+y*y), i, x, y);
                w.addFreshEntity(cir, CreatureSpawnEvent.SpawnReason.CUSTOM);
                LGWTFM.circlelist.add(cir);
            }
        }
        for(int j = 1; j<LGWTFM.circlelist.size()-1;j++) {
            for(int i = 0; i<LGWTFM.circlelist.size()-j;i++) {
                if(LGWTFM.circlelist.get(i).size<LGWTFM.circlelist.get(i+1).size) {
                    Circle cir = LGWTFM.circlelist.get(i);
                    LGWTFM.circlelist.set(i, LGWTFM.circlelist.get(i+1));
                    LGWTFM.circlelist.set(i+1, cir);
                }
            }
        }
        Vector<Circle> removinglist = new Vector<>();
        for(int i = n;i<LGWTFM.circlelist.size();i++) {
            removinglist.add(LGWTFM.circlelist.get(i));
        }
        for(Circle cir : removinglist) {
            cir.getBukkitEntity().remove();
        }
        LGWTFM.circlelist.removeAll(removinglist);
        LGWTFM.Up();
        sender.sendMessage("가져오기 완료");
    }

    public void start(CommandSender sender) {
        LGWTFM.isStart = true;
    }

    public void stop(CommandSender sender) {
        LGWTFM.isStart = false;
    }

    public void speed(CommandSender sender, String string1) {
        LGWTFM.speed = Double.parseDouble(string1);
    }

    public void distance(CommandSender commandSender, String string1) {
        LGWTFM.distance = Double.parseDouble(string1);
    }

    public void isfollowing(CommandSender commandSender, String string1) {
        LGWTFM.isfollow = Boolean.parseBoolean(string1);
    }

    public void distancehelp(CommandSender commandSender) {
        commandSender.sendMessage("/lgwtfm distance <value>");
    }

    public void isfollowinghelp(CommandSender commandSender) {
        commandSender.sendMessage("/lgwtfm isfollowing <true/false>");
    }

    public void loadhelp(CommandSender sender) {
        sender.sendMessage("/lgwtfm load <FileLoc> <Num>");
    }

    public void starthelp(CommandSender sender) {
        sender.sendMessage("/lgwtfm start");
    }

    public void stophelp(CommandSender sender) {
        sender.sendMessage("/lgwtfm stop");
    }

    public void speedhelp(CommandSender sender) {
        sender.sendMessage("/lgwtfm speed <value>");
    }

    public void wt(CommandSender sender) {
        sender.sendMessage("?");
    }

    public void help(CommandSender sender) {
        loadhelp(sender);
        starthelp(sender);
        stophelp(sender);
        speedhelp(sender);
        distancehelp(sender);
        isfollowinghelp(sender);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        switch(strings.length) {
            case 0:
                help(commandSender);
                break;
            case 1:
                switch(strings[0]) {
                    case "load":
                        loadhelp(commandSender);
                        break;
                    case "start":
                        start(commandSender);
                        break;
                    case "stop":
                        stop(commandSender);
                        break;
                    case "speed":
                        speedhelp(commandSender);
                        break;
                    case "isfollowing":
                        isfollowinghelp(commandSender);
                        break;
                    case "distance":
                        distancehelp(commandSender);
                        break;
                    default:
                        wt(commandSender);
                        break;
                }
                break;
            case 2:
                switch(strings[0]) {
                    case "load":
                        loadhelp(commandSender);
                        break;
                    case "start":
                        start(commandSender);
                        break;
                    case "stop":
                        stop(commandSender);
                        break;
                    case "speed":
                        speed(commandSender, strings[1]);
                        break;
                    case "isfollowing":
                        isfollowing(commandSender, strings[1]);
                        break;
                    case "distance":
                        distance(commandSender, strings[1]);
                        break;
                    default:
                        wt(commandSender);
                        break;
                }
                break;
            default:
                switch(strings[0]) {
                    case "load":
                        load(commandSender, strings[1], strings[2]);
                        break;
                    case "start":
                        start(commandSender);
                        break;
                    case "stop":
                        stop(commandSender);
                        break;
                    case "speed":
                        speed(commandSender, strings[1]);
                        break;
                    case "isfollowing":
                        isfollowing(commandSender, strings[1]);
                        break;
                    case "distance":
                        distance(commandSender, strings[1]);
                        break;
                    default:
                        wt(commandSender);
                        break;
                }
                break;
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        switch(strings.length) {
            case 1:
                return Arrays.asList("load", "start", "stop", "speed", "isfollowing", "distance");
            case 2:
                switch(strings[0]) {
                    case "load":
                        return Arrays.asList("<파일위치>");
                    case "speed":
                        return Arrays.asList("<숫자>");
                    case "isfollowing":
                        return Arrays.asList("<true/false>");
                    case "distance":
                        return Arrays.asList("<숫자>");
                }
                break;
            case 3:
                if(strings[0]=="load") {
                    return Arrays.asList("<int>");
                }
                break;
        }
        return Arrays.asList();
    }
}
