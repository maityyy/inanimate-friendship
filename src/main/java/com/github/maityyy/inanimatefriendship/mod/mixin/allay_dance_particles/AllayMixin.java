package com.github.maityyy.inanimatefriendship.mod.mixin.allay_dance_particles;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.animal.allay.Allay;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Allay.class)
class AllayMixin {

    @Inject(method = "aiStep", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/animal/allay/Allay;isDancing()Z"))
    void sendDanceParticles(CallbackInfo callback) {
        var self = (Allay) (Object) this;

        if (!self.level().isClientSide()) {
            if (self.tickCount % 20 == 0 && self.isDancing()) {
                // 16-18 notes — blue colors
                // NOTE https://minecraft.wiki/w/Note_Block#Notes
                var color = Mth.nextDouble(self.getRandom(), 16, 18) / 24d;

                // VANILLACOPY NoteBlock::triggerEvent. Also see ClientPacketListener::handleParticleEvent
                ((ServerLevel) self.level()).sendParticles(ParticleTypes.NOTE, self.getRandomX(0.5), self.getEyeY() + 0.3, self.getRandomZ(0.5), 0, 1, 0, 0, color);
            }
        }
    }
}
