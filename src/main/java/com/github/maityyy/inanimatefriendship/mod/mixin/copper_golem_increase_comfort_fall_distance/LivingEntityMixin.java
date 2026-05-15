package com.github.maityyy.inanimatefriendship.mod.mixin.copper_golem_increase_comfort_fall_distance;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.golem.CopperGolem;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

// Use the safe fall distance as the comfortable fall distance so
// that the copper golem can shorten its path
@Mixin(LivingEntity.class)
class LivingEntityMixin {

    @ModifyConstant(method = "getComfortableFallDistance", constant = @Constant(floatValue = 3f))
    float copperGolemIncreaseComfortFallDistance(float comfortFallDist) {
        var self = (LivingEntity) (Object) this;

        if (self instanceof CopperGolem) {
            return (float) self.getAttributeValue(Attributes.SAFE_FALL_DISTANCE);
        }

        return comfortFallDist; // 3 blocks
    }
}
