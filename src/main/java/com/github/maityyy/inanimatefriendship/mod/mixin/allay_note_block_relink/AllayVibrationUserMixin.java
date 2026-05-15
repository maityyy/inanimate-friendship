package com.github.maityyy.inanimatefriendship.mod.mixin.allay_note_block_relink;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(targets = "net/minecraft/world/entity/animal/allay/Allay$VibrationUser")
class AllayVibrationUserMixin {

    // Allow the allay to receive vibrations from another note block even if it is already linked
    @ModifyExpressionValue(method = "canReceiveVibration", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/BlockPos;equals(Ljava/lang/Object;)Z"))
    boolean removeIsLikedNoteBlockCheck(boolean isLikedNoteBlock) {
        return true;
    }
}
