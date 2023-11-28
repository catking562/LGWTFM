package taewookim.entity;

import net.minecraft.world.entity.Display;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.level.World;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Transformation;

public class Circle extends Display.ItemDisplay {

    public final double size;
    public final double n;
    public final double a;
    public final double b;

    public Circle(World var1, double size, int n, double a, double b) {
        super(EntityTypes.ae, var1);
        this.size = size;
        this.n = n;
        this.a = a;
        this.b = b;
        org.bukkit.entity.ItemDisplay id = (org.bukkit.entity.ItemDisplay) this.getBukkitEntity();
        Transformation tran = id.getTransformation();
        tran.getScale().set(size*2, size*2, size*2);
        id.setTransformation(tran);
        ItemStack i = new ItemStack(Material.LEATHER_HORSE_ARMOR);
        ItemMeta m = i.getItemMeta();
        m.setCustomModelData(1);
        i.setItemMeta(m);
        id.setItemStack(i);
        this.getBukkitEntity().teleport(new Location(Bukkit.getWorld("world"), -5, 77, -11));
    }
}
