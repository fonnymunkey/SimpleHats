package fonnymunkey.simplehats.client.hatdisplay;

import fonnymunkey.simplehats.common.entity.HatDisplay;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;

public class HatDisplayModel<T extends Entity> extends EntityModel<HatDisplay> {
    private final ModelPart bb_main;
    public HatDisplayModel(ModelPart root) {
        this.bb_main = root.getChild("bb_main");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData meshdefinition = new ModelData();
        ModelPartData partdefinition = meshdefinition.getRoot();

        ModelPartData bb_main = partdefinition.addChild("bb_main", ModelPartBuilder.create().uv(0, 0).cuboid(-6.0F, -2.0F, -6.0F, 12.0F, 2.0F, 12.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-0.5F, -5.0F, -0.5F, 1.0F, 4.0F, 1.0F, new Dilation(0.0F))
                .uv(0, 14).cuboid(-4.5F, -13.0F, -4.5F, 9.0F, 9.0F, 9.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

        return TexturedModelData.of(meshdefinition, 64, 64);
    }

    @Override
    public void setAngles(HatDisplay entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) { }

    @Override
    public void render(MatrixStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
        bb_main.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
    }
}