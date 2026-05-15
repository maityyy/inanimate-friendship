package com.github.maityyy.inanimatefriendship.mod.mixin.allay_adjust_item_deposit_pos;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.allay.AllayAi;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

// Adjust the allay deposit pos upwards if note block top surface is occupied
@Mixin(AllayAi.class)
class AllayAiMixin {

    @ModifyExpressionValue(method = "getItemDepositPosition", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/BlockPos;above()Lnet/minecraft/core/BlockPos;"))
    private static BlockPos adjustDepositPos(BlockPos depositPos, LivingEntity allay) {
        var depositPosBlock = allay.level().getBlockState(depositPos);
        return depositPosBlock.canBeReplaced() ? depositPos : depositPos.above();
    }
}
