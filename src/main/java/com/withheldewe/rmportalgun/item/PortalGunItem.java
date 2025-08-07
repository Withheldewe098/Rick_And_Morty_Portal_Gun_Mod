package com.withheldewe.rmportalgun.item;

import org.joml.Vector3f;
import com.withheldewe.rmportalgun.PortalManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleEngine;

public class PortalGunItem extends Item {
    public PortalGunItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        // client‐only: spawn the 5 dust particles
        if (world.isClientSide) {
            Vec3 look = player.getLookAngle().normalize();
            double px = player.getX() + look.x * 0.5;
            double py = player.getEyeY() + look.y * 0.5;
            double pz = player.getZ() + look.z * 0.5;

            DustParticleOptions dustOpts = new DustParticleOptions(
                    new Vector3f(0f, 1f, 0f),  // green
                    1.0f                        // size
            );

            ParticleEngine engine = Minecraft.getInstance().particleEngine;
            int segments = 5;
            double spacing = 0.8;
            double xOffset = -0.2, yOffset = -0.2;

            for (int i = 0; i < segments; i++) {
                double x = px + look.x * spacing * i + xOffset;
                double y = py + look.y * spacing * i + yOffset;
                double z = pz + look.z * spacing * i;

                Particle p = engine.createParticle(dustOpts, x, y, z, 0, 0, 0);
                if (p != null) {
                    p.setLifetime(1);
                }
            }
        }

        // server-only: actually spawn + schedule portal removal
        else {
            Vec3 look = player.getLookAngle().normalize();
            double px = player.getX() + look.x * 0.5;
            double py = player.getEyeY() + look.y * 0.5;
            double pz = player.getZ() + look.z * 0.5;

            int segments = 5;
            double spacing = 0.8;

            // calculate end-point of the stream
            double tx = px + look.x * spacing * (segments - 1);
            double ty = py + look.y * spacing * (segments - 1);
            double tz = pz + look.z * spacing * (segments - 1);

            BlockPos target = new BlockPos(
                    (int)Math.floor(tx),
                    (int)Math.floor(ty),
                    (int)Math.floor(tz)
            );

            PortalManager.spawnPortal(world, target, look);
        }

        return InteractionResultHolder.consume(stack);
    }
}
