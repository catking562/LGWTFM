package taewookim;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import taewookim.commands.LGCMD;
import taewookim.entity.Circle;
import taewookim.entity.Flame;

import java.io.File;
import java.util.Vector;

public class LGWTFM extends JavaPlugin {

    //-5 77 -11
    final public static double maxtick = 500;

    public static boolean isStart = false;
    public static double tick = 0;
    public static double speed = 1;
    public static Vector<Double> listx = new Vector<>();
    public static Vector<Double> listy = new Vector<>();
    public static Vector<Circle> circlelist = new Vector<>();
    public static LGWTFM plugin;
    public static Vector<Flame> flames = new Vector<>();
    public static double beforetick = 0;
    public static boolean isfollow = false;
    public static double distance = 5;

    public static double AnfX(double n) {
        int mi = listx.size();
        double result = 0;
        for(int i = 0; i<mi;i++) {
            double per = ((double)i)/((double)mi);
            result+=listx.get(i)*Math.sin(n*2*Math.PI*per);
        }
        return (result/mi)*2.0D;
    }

    public static double BnfX(double n) {
        int mi = listx.size();
        double result = 0;
        for(int i = 0; i<mi;i++) {
            double per = ((double)i)/((double)mi);
            result+=listx.get(i)*Math.cos(n*2*Math.PI*per);
        }
        return (result/mi)*2.0D;
    }

    public static double AnfY(double n) {
        int mi = listy.size();
        double result = 0;
        for(int i = 0; i<mi;i++) {
            double per = ((double)i)/((double)mi);
            result+=listy.get(i)*Math.sin(n*2*Math.PI*per);
        }
        return (result/mi)*2.0D;
    }

    public static double BnfY(double n) {
        int mi = listy.size();
        double result = 0;
        for(int i = 0; i<mi;i++) {
            double per = ((double)i)/((double)mi);
            result+=listy.get(i)*Math.cos(n*2.0D*Math.PI*per);
        }
        return (result/mi)*2.0D;
    }

    public static void Up() {
        double per = tick/maxtick;
        Location loc = new Location(Bukkit.getWorld("world"),-5.5, 77.5, -11.0D+0.0001D);
        for(Circle cir : circlelist) {
            double k = cir.n*per*2.0D*Math.PI;
            double dx = (cir.a*Math.sin(k) + cir.b*Math.cos(k));
            double dy = cir.a*Math.cos(k)-cir.b*Math.sin(k);
            loc.setDirection(new org.bukkit.util.Vector(dx, dy, 0));
            cir.getBukkitEntity().teleport(loc);
            loc.add(dx, dy, 0);
        }
        if(beforetick>tick) {
            beforetick-=maxtick;
        }
        if(beforetick+1<=tick) {
            flames.add(new Flame(loc, maxtick));
            beforetick=tick;
        }
        if(isfollow) {
            Location loc1 = loc.clone();
            loc1.setZ(-11.0D+distance);
            loc1.setY(loc1.getY()-1.75D);
            loc1.setYaw(180);
            loc1.setPitch(0);
            Bukkit.getPlayer("taewookim562").teleport(loc1);
        }else {
            Location loc1 = new Location(Bukkit.getWorld("world"), -5.5, 75.75D, -11.0D+distance);
            loc1.setYaw(180);
            loc1.setPitch(0);
            Bukkit.getPlayer("taewookim562").teleport(loc1);
        }
    }

    public void Update() {
        BukkitRunnable brun = new BukkitRunnable() {
            @Override
            public void run() {
                if(isStart) {
                    tick+=speed;
                    if(tick>maxtick) {
                        tick-=maxtick;
                    }
                    Up();
                    Vector<Flame> removing = new Vector<>();
                    for(Flame flame : flames) {
                        flame.tick+=speed;
                        if(flame.tick>=flame.maxtick) {
                            removing.add(flame);
                        }
                    }
                    flames.removeAll(removing);
                }
                for(Flame flame : flames) {
                    flame.Update();
                }
            }
        };brun.runTaskTimer(this, 0, 0);
    }

    public void loadCMD() {
        LGCMD cmd = new LGCMD();
        PluginCommand c = Bukkit.getPluginCommand("lgwtfm");
        c.setExecutor(cmd);
        c.setTabCompleter(cmd);
    }

    @Override
    public void onEnable() {
        try{
            getDataFolder().mkdir();
        }catch(Exception e) {
        }
        plugin = this;
        loadCMD();
        Update();
    }
}
