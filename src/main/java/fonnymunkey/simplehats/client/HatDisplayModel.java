package fonnymunkey.simplehats.client;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class HatDisplayModel extends ModelBase {

    private final ModelRenderer base;

    public HatDisplayModel() {
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.base = new ModelRenderer(this);
        this.base.setRotationPoint(0.0F, 24.0F, 0.0F);
        this.base.setTextureOffset(0, 0).addBox(-6.0F, -2.0F, -6.0F, 12, 2, 12);
        this.base.setTextureOffset(0, 0).addBox(-0.5F, -5.0F, -0.5F, 1, 4, 1);
        this.base.setTextureOffset(0, 14).addBox(-4.5F, -13.0F, -4.5F, 9, 9, 9);
    }

    @Override
    public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        this.base.render(scale);
    }
}
