package rms.carpet_rms_addition.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.FallingBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import rms.carpet_rms_addition.CarpetRMSAdditionSettings;

import java.util.Random;

@Mixin(FallingBlock.class)
public abstract class FallingBlockMixin {
    @Inject(method = "scheduledTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/FallingBlock;configureFallingBlockEntity(Lnet/minecraft/entity/FallingBlockEntity;)V", ordinal = 0))
    private void scheduleTick(final BlockState state, final ServerWorld world, final BlockPos pos, final Random random, final CallbackInfo ci) {
        if (CarpetRMSAdditionSettings.fallingBlockBackport)
            world.setBlockState(pos, state.getFluidState().getBlockState(), 3);
    }
}
