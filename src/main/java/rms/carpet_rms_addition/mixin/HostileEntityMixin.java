package rms.carpet_rms_addition.mixin;

import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.LightType;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import rms.carpet_rms_addition.CarpetRMSAdditionSettings;
import rms.carpet_rms_addition.LightLevelGetter;

@Mixin(HostileEntity.class)
public abstract class HostileEntityMixin {
    @Redirect(method = "isSpawnDark", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/ServerWorldAccess;getLightLevel(Lnet/minecraft/world/LightType;Lnet/minecraft/util/math/BlockPos;)I"))
    private static int getLightLevel(final ServerWorldAccess instance, final LightType lightLayer, final BlockPos blockPos) {
        return switch (lightLayer) {
            case BLOCK ->
                CarpetRMSAdditionSettings.isKeepingBlockLightLevel() ? instance.getLightLevel(LightType.BLOCK, blockPos) : CarpetRMSAdditionSettings.getBlockLightLevel();
            case SKY ->
                CarpetRMSAdditionSettings.isKeepingSkyLightLevel() ? instance.getLightLevel(LightType.SKY, blockPos) : CarpetRMSAdditionSettings.getSkyLightLevel();
        };
    }
    
    @Redirect(method = "isSpawnDark", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/ServerWorldAccess;getLightLevel(Lnet/minecraft/util/math/BlockPos;I)I"))
    private static int getLightLevel(final ServerWorldAccess instance, final BlockPos blockPos, final int i) {
        return LightLevelGetter.get(instance, blockPos, i);
    }
    
    @Redirect(method = "isSpawnDark", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/ServerWorldAccess;getLightLevel(Lnet/minecraft/util/math/BlockPos;)I"))
    private static int getLightLevel(final ServerWorldAccess instance, final BlockPos blockPos) {
        return LightLevelGetter.get(instance, blockPos);
    }
    
    @Redirect(method = "getPathfindingFavor", at = @At(value = "INVOKE",
        //#if MC < 12001
        target = "Lnet/minecraft/world/WorldView;getBrightness(Lnet/minecraft/util/math/BlockPos;)F"
        //#else
        //$$ target = "Lnet/minecraft/world/WorldView;getPhototaxisFavor(Lnet/minecraft/util/math/BlockPos;)F"
        //#endif
    ))
    private float getPhototaxisFavor(final WorldView instance, final BlockPos pos) {
        //#if MC < 12001
        return instance.getDimension().getBrightness(LightLevelGetter.get(instance, pos));
        //#else
        //$$ final float f = LightLevelGetter.get(instance, pos) / 15.0F;
        //$$ final float g = f / (4.0F - 3.0F * f);
        //$$ return net.minecraft.util.math.MathHelper.lerp(instance.getDimension().ambientLight(), g, 1.0F) - 0.5F;
        //#endif
    }
}
