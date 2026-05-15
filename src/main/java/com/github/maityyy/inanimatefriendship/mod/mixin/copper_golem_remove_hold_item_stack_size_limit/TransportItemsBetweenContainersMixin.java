package com.github.maityyy.inanimatefriendship.mod.mixin.copper_golem_remove_hold_item_stack_size_limit;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.entity.ai.behavior.TransportItemsBetweenContainers;
import net.minecraft.world.item.ItemStack;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(TransportItemsBetweenContainers.class)
class TransportItemsBetweenContainersMixin {

    // Reinit variable
    @ModifyVariable(method = "pickupItemFromContainer", name = "itemCount", at = @At("STORE"))
    private static int removePickupItemStackSizeLimit(int sizeLimit, @Local(name = "itemStack") ItemStack item) {
        return item.getCount();
    }
}
