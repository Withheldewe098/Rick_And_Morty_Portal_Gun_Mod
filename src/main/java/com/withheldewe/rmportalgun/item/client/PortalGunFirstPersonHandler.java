package com.withheldewe.rmportalgun.item.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.withheldewe.rmportalgun.item.ModItems;
import com.withheldewe.rmportalgun.item.PortalGunItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = "rmportalgun")
public class PortalGunFirstPersonHandler {

    @SubscribeEvent
    public static void onRenderHand(RenderHandEvent event) {
        ItemStack stack = event.getItemStack();

        // Only override if holding any Portal Gun
        if (stack.is(ModItems.Portal_Gun.get()) ||
                stack.is(ModItems.MK1_Portal_Gun.get()) ||
                stack.is(ModItems.MK2_Portal_Gun.get()) ||
                stack.is(ModItems.MK3_Portal_Gun.get())) {

            event.setCanceled(true);

            PoseStack poseStack = event.getPoseStack();
            MultiBufferSource buffer = event.getMultiBufferSource();
            int light = event.getPackedLight();

            poseStack.pushPose();

            boolean isLeft = (event.getHand() == InteractionHand.OFF_HAND);

            // 🔹 Separate translations by gun type
            if (stack.is(ModItems.MK2_Portal_Gun.get()) || stack.is(ModItems.MK3_Portal_Gun.get())) {
                // ✅ MK2 & MK3 - Keep perfect original positions
                if (isLeft) {
                    poseStack.translate(-0.125F, -0.425F, -0.7F);
                } else {
                    poseStack.translate(0.5F, -0.425F, -0.7F);
                }
            } else if (stack.is(ModItems.MK1_Portal_Gun.get()) || stack.is(ModItems.Portal_Gun.get())) {
                // 🔄 MK1 & Regular Portal Gun - Slightly move to the left
                if (isLeft) {
                    poseStack.translate(-0.41F, -0.4F, -0.7F); // Left hand shifted
                } else {
                    poseStack.translate(0.55F, -0.4F, -0.7F); // Right hand shifted
                }
            }

            // Recoil logic
            int recoil = PortalGunItem.getRecoilTicks(stack);
            if (recoil > 0) {
                float maxTicks = 15.0f;
                float progress = recoil / maxTicks;
                poseStack.translate(0, 0, 0.05F * progress);
                poseStack.mulPose(Axis.XP.rotationDegrees(2.5F * progress));
                PortalGunItem.tickRecoil(stack);
            }

            // Render
            ItemDisplayContext context = isLeft
                    ? ItemDisplayContext.FIRST_PERSON_LEFT_HAND
                    : ItemDisplayContext.FIRST_PERSON_RIGHT_HAND;

            Minecraft mc = Minecraft.getInstance();
            mc.getItemRenderer().renderStatic(stack, context,
                    light, OverlayTexture.NO_OVERLAY, poseStack, buffer, mc.level, 0);

            poseStack.popPose();
        }
    }
}
