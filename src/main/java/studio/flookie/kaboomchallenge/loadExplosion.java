package studio.flookie.kaboomchallenge;

import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.entity.Entity;

public class loadExplosion {
    public static void explosion(Entity entity, World world) {
        Vec3d pos = entity.getPos();
        world.createExplosion(
                entity,
                pos.getX(),
                pos.getY(),
                pos.getZ(),
                5.0F,
                World.ExplosionSourceType.TNT
            );
    }
}

