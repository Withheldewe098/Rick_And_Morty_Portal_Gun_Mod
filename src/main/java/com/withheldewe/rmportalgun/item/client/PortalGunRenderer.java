package com.withheldewe.rmportalgun.item.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.withheldewe.rmportalgun.item.PortalGunItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class PortalGunRenderer extends BlockEntityWithoutLevelRenderer {

    public PortalGunRenderer(net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher dispatcher,
                             EntityModelSet entityModelSet) {
        super(dispatcher, entityModelSet);
    }

    @Override
    public void renderByItem(ItemStack stack, ItemDisplayContext displayContext,
                             PoseStack poseStack, MultiBufferSource buffer, int light, int overlay) {

        // Only modify in first person right hand
        if (displayContext == ItemDisplayContext.FIRST_PERSON_RIGHT_HAND) {
            // Cancel vanilla swing by applying inverse of the bobbing motion
            // (Simple counter-transform; fine for testing)
            poseStack.translate(0.0F, 0.0F, 0.0F); // Neutralize translation
            poseStack.mulPose(Axis.XP.rotationDegrees(0.0F)); // Neutralize rotation

            // Apply exaggerated recoil transform
            int recoil = PortalGunItem.getRecoilTicks(stack);
            if (recoil > 0) {
                float progress = recoil / 3.0f; // Normalize 0–1
                poseStack.translate(0, 0, -0.5F * progress); // Large push back
                poseStack.mulPose(Axis.XP.rotationDegrees(15F * progress)); // Large tilt
                PortalGunItem.tickRecoil(stack);
            }
        }

        // Render the item normally
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        BakedModel model = itemRenderer.getModel(stack, null, null, 0);
        itemRenderer.render(stack, displayContext, false, poseStack, buffer, light, overlay, model);
    }
}
