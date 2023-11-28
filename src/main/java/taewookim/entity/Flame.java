package taewookim.entity;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;

public class Flame {

    public double maxtick = 0;
    public int tick = 0;
    public Location loc;

    public Flame(Location loc, double maxtick) {
        this.loc = loc;
        this.maxtick = maxtick;
    }

    public void Update() {
        double per = ((double)tick)/((double)maxtick);
        int k = 255-((int) (per*200));
        loc.getWorld().spawnParticle(Particle.REDSTONE, loc, 1, 0, 0, 0, new Particle.DustOptions(Color.fromARGB(255, k, k, k), 0.1f));

    }

}
