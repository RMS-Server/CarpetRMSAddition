package rms.carpet_rms_addition.mixin;

import net.minecraft.entity.mob.SlimeEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import rms.carpet_rms_addition.LightLevelGetter;

@Mixin(SlimeEntity.class)
public abstract class SlimeEntityMixin {
    @Redirect(method = "canSpawn", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/WorldAccess;getLightLevel(Lnet/minecraft/util/math/BlockPos;)I"))
    private static int getLightLevel(WorldAccess instance, BlockPos blockPos) {
        return LightLevelGetter.get(instance, blockPos);
    }
}
