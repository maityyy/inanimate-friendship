package com.github.maityyy.inanimatefriendship.mod.mixin.allay_note_block_relink;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.animal.allay.Allay;
import net.minecraft.world.entity.animal.allay.AllayAi;

import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;
import org.jetbrains.annotations.Nullable;

// Link the allay to the note block forever instead of 30 secs
@Mixin(AllayAi.class)
class AllayAiMixin {

    @WrapWithCondition(method = "hearNoteblock", at = @At(ordinal = 1, value = "INVOKE", target = "Lnet/minecraft/world/entity/ai/Brain;setMemory(Lnet/minecraft/world/entity/ai/memory/MemoryModuleType;Ljava/lang/Object;)V"))
    private static boolean doNotSetNoteBlockCooldown1(Brain<Allay> brain, MemoryModuleType<Object> cooldownType, @Nullable Object cooldown) {
        return false;
    }

    @WrapWithCondition(method = "hearNoteblock", at = @At(ordinal = 2, value = "INVOKE", target = "Lnet/minecraft/world/entity/ai/Brain;setMemory(Lnet/minecraft/world/entity/ai/memory/MemoryModuleType;Ljava/lang/Object;)V"))
    private static boolean doNotSetNoteBlockCooldown2(Brain<Allay> brain, MemoryModuleType<Object> cooldownType, @Nullable Object cooldown) {
        return false;
    }

    @ModifyExpressionValue(method = "shouldDepositItemsAtLikedNoteblock", at = @At(value = "INVOKE", target = "Ljava/util/Optional;isPresent()Z"))
    private static boolean doNotCheckNoteBlockCooldown(boolean hasCooldown) {
        return true;
    }

    // Allow the allay to be linked even if it's already linked.
    // NOTE also see AllayVibrationUserMixin
    @ModifyExpressionValue(method = "hearNoteblock", at = @At(value = "INVOKE", target = "Ljava/util/Optional;isEmpty()Z"))
    private static boolean allowRelink(boolean linkTimePassed) {
        return true;
    }

    // Mojang uses the chebyshev distance but provides the squared distance (1024 = DISTANCE_TO_WANTED_ITEM²).
    // Without this fix, the allay will keep trying to return to the note block until it is more than 1024 blocks away.
    @ModifyConstant(method = "shouldDepositItemsAtLikedNoteblock", constant = @Constant(intValue = 1024))
    private static int useCorrectCloseEnoughDistance(int isCloseEnoughDist) {
        return DISTANCE_TO_WANTED_ITEM; // 32
    }

    @Inject(method = "hearNoteblock", at = @At("RETURN"))
    private static void playRelinkAnimation(LivingEntity entity, BlockPos noteBlockPos, CallbackInfo callback, @Local(name = "brain") Brain<?> brain, @Local(name = "likedNoteblockPos") Optional<GlobalPos> linkedNoteBlock) {
        var allay = (Allay) entity;

        var isAlreadyLinkedToThatBlock = linkedNoteBlock.isPresent() && linkedNoteBlock.get().dimension().equals(allay.level().dimension()) && linkedNoteBlock.get().pos().equals(noteBlockPos);

        // FIXME wrong subtitles
        if (isAlreadyLinkedToThatBlock) {
            var hasWantedItem = brain.checkMemory(MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM, MemoryStatus.VALUE_PRESENT);

            if (hasWantedItem) {
                allay.playSound(SoundEvents.ALLAY_THROW, 2, 1.5f);
            } else {
                allay.playSound(SoundEvents.ALLAY_THROW, 2, 0.8f);
            }
        } else {
            allay.playSound(SoundEvents.ALLAY_ITEM_GIVEN, 2, 1.5f);

            // NOTE Allay::aiStep stops this animation every 20 ticks
            allay.setDancing(true);
        }
    }

    @Shadow
    @Final
    private static int DISTANCE_TO_WANTED_ITEM;
}
