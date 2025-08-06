package com.withheldewe.rmportalgun.item;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.level.Level;

public class PortalGunItem extends Item {

    // Longer recoil animation duration
    private static final int RECOIL_TICKS = 15;

    public PortalGunItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (level.isClientSide) {
            CompoundTag tag = getOrCreateCustomTag(stack);
            tag.putInt("RecoilTicks", RECOIL_TICKS);
            stack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
        }

        return InteractionResultHolder.success(stack);
    }

    public static int getRecoilTicks(ItemStack stack) {
        CustomData data = stack.get(DataComponents.CUSTOM_DATA);
        if (data != null) {
            CompoundTag tag = data.copyTag();
            return tag.getInt("RecoilTicks");
        }
        return 0;
    }

    public static void tickRecoil(ItemStack stack) {
        CustomData data = stack.get(DataComponents.CUSTOM_DATA);
        if (data != null) {
            CompoundTag tag = data.copyTag();
            int ticks = tag.getInt("RecoilTicks");
            if (ticks > 0) {
                tag.putInt("RecoilTicks", ticks - 1);
                stack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
            }
        }
    }

    private static CompoundTag getOrCreateCustomTag(ItemStack stack) {
        CustomData data = stack.get(DataComponents.CUSTOM_DATA);
        return data != null ? data.copyTag() : new CompoundTag();
    }
}
