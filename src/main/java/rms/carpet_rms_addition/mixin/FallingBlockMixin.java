package rms.carpet_rms_addition.mixin;

import net.minecraft.block.FallingBlock;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Random;

@Mixin(FallingBlock.class)
public abstract class FallingBlockMixin {
    //#if MC < 11802
    @org.spongepowered.asm.mixin.injection.Inject(method = "scheduledTick", at = @org.spongepowered.asm.mixin.injection.At(value = "INVOKE", target = "Lnet/minecraft/block/FallingBlock;configureFallingBlockEntity(Lnet/minecraft/entity/FallingBlockEntity;)V", ordinal = 0))
    private void scheduleTick(final net.minecraft.block.BlockState state, final net.minecraft.server.world.ServerWorld world, final net.minecraft.util.math.BlockPos pos, final Random random, final org.spongepowered.asm.mixin.injection.callback.CallbackInfo ci) {
        if (rms.carpet_rms_addition.CarpetRMSAdditionSettings.fallingBlockBackport)
            world.setBlockState(pos, state.getFluidState().getBlockState(), 3);
    }
    //#endif
}
