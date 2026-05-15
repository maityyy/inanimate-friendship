package com.github.maityyy.inanimatefriendship.mod.mixin.allay_older_items_priority;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.sensing.NearestItemSensor;
import net.minecraft.world.entity.animal.allay.Allay;
import net.minecraft.world.entity.item.ItemEntity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.Comparator;

// Sort the items for the allay first by age and only then by distance
@Mixin(NearestItemSensor.class)
class NearestItemSensorMixin {

    @Unique
    private static final Comparator<ItemEntity> OLDEST = Comparator.comparingInt(ItemEntity::getAge).reversed();

    @SuppressWarnings({"AmbiguousMixinReference", "LocalMayUseName"}) // MCDev plugin bug
    @ModifyArg(method = "doTick", at = @At(value = "INVOKE", target = "Ljava/util/List;sort(Ljava/util/Comparator;)V"))
    Comparator<ItemEntity> allaySortByAgeFirst(Comparator<ItemEntity> nearest, @Local(argsOnly = true) Mob mob) {
        if (mob instanceof Allay) {
            return OLDEST.thenComparing(nearest);
        }

        return nearest;
    }
}
