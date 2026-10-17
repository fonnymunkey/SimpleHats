package fonnymunkey.simplehats.client.hatdisplay;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class HatDisplayModel extends EntityModel<HatDisplayRenderState> {
    
    public HatDisplayModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        
        PartDefinition bb_main = partdefinition.addOrReplaceChild("bb_main", CubeListBuilder.create()
               .texOffs(0, 0).addBox(-6.0F, -2.0F, -6.0F, 12.0F, 2.0F, 12.0F, new CubeDeformation(0.0F))
               .texOffs(0, 0).addBox(-0.5F, -5.0F, -0.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
               .texOffs(0, 14).addBox(-4.5F, -13.0F, -4.5F, 9.0F, 9.0F, 9.0F, new CubeDeformation(0.0F)),
               PartPose.offset(0.0F, 24.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }
}
