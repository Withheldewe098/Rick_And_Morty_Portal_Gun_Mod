package com.withheldewe.rmportalgun;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.*;

/**
 * Spawns a temporary 3×2 nether-portal at the given center, oriented
 * perpendicular to the look direction, and removes it after 10 seconds.
 */
@Mod.EventBusSubscriber
public class PortalManager {
    private static class ActivePortal {
        final Level world;
        final List<BlockPos> blocks;
        int ticksLeft;
        ActivePortal(Level w, List<BlockPos> b, int t) { world = w; blocks = b; ticksLeft = t; }
    }

    private static final List<ActivePortal> ACTIVE = new ArrayList<>();

    /**
     * @param world  must be server side
     * @param center where to center the portal plane
     * @param look   player's look vector (normalized)
     */
    public static void spawnPortal(Level world, BlockPos center, Vec3 look) {
        if (world.isClientSide) return;

        // decide orientation: if looking more along X axis, portal spans Z; else spans X
        boolean spanZ = Math.abs(look.x) > Math.abs(look.z);

        List<BlockPos> placed = new ArrayList<>();
        for (int dz = 0; dz < 2; dz++) {      // height 2
            for (int w = -1; w <= 1; w++) {   // width 3
                int dx = spanZ ? 0 : w;
                int dzOff = spanZ ? w : 0;
                BlockPos pos = center.offset(dx, dz, dzOff);
                world.setBlockAndUpdate(pos, Blocks.NETHER_PORTAL.defaultBlockState());
                placed.add(pos);
            }
        }

        // schedule removal after 10s = 200 ticks
        ACTIVE.add(new ActivePortal(world, placed, 200));
    }

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent ev) {
        if (ev.phase != TickEvent.Phase.END) return;
        Iterator<ActivePortal> it = ACTIVE.iterator();
        while (it.hasNext()) {
            ActivePortal ap = it.next();
            if (--ap.ticksLeft <= 0) {
                // remove portal blocks
                for (BlockPos pos : ap.blocks) {
                    if (ap.world.getBlockState(pos).getBlock() == Blocks.NETHER_PORTAL) {
                        ap.world.removeBlock(pos, false);
                    }
                }
                it.remove();
            }
        }
    }
}
