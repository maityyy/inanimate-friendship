package com.github.maityyy.inanimatefriendship.mod.mixin.allay_reset_item_pickup_cooldown;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.animal.allay.AllayAi;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AllayAi.class)
class AllayAiMixin {

    @Inject(method = "hearNoteblock", at = @At("RETURN"))
    private static void resetItemPickupCooldown(LivingEntity entity, BlockPos noteBlockPos, CallbackInfo callback, @Local(name = "brain") Brain<?> brain) {
        brain.eraseMemory(MemoryModuleType.ITEM_PICKUP_COOLDOWN_TICKS);
    }
}
