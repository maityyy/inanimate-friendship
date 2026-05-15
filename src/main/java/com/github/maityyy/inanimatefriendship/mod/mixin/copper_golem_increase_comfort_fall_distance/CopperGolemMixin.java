package com.github.maityyy.inanimatefriendship.mod.mixin.copper_golem_increase_comfort_fall_distance;

import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.golem.CopperGolem;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CopperGolem.class)
class CopperGolemMixin {

    @Inject(method = "createAttributes", at = @At("RETURN"))
    private static void increaseSafeFallDistace(CallbackInfoReturnable<Builder> callback) {
        callback.getReturnValue().add(Attributes.SAFE_FALL_DISTANCE, 6);
    }
}
