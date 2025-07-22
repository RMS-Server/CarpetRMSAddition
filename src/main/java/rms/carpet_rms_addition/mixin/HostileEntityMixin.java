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
    private static int getLightLevel(ServerWorldAccess instance, LightType lightLayer, BlockPos blockPos) {
        return switch (lightLayer) {
            case BLOCK ->
                    CarpetRMSAdditionSettings.isKeepingBlockLightLevel() ? instance.getLightLevel(LightType.BLOCK, blockPos) : CarpetRMSAdditionSettings.getBlockLightLevel();
            case SKY ->
                    CarpetRMSAdditionSettings.isKeepingSkyLightLevel() ? instance.getLightLevel(LightType.SKY, blockPos) : CarpetRMSAdditionSettings.getSkyLightLevel();
        };
    }

    @Redirect(method = "isSpawnDark", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/ServerWorldAccess;getLightLevel(Lnet/minecraft/util/math/BlockPos;I)I"))
    private static int getLightLevel(ServerWorldAccess instance, BlockPos blockPos, int i) {
        return LightLevelGetter.get(instance, blockPos, i);
    }

    @Redirect(method = "isSpawnDark", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/ServerWorldAccess;getLightLevel(Lnet/minecraft/util/math/BlockPos;)I"))
    private static int getLightLevel(ServerWorldAccess instance, BlockPos blockPos) {
        return LightLevelGetter.get(instance, blockPos);
    }

    //#if MC < 12001
    @Redirect(method = "getPathfindingFavor", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/WorldView;getBrightness(Lnet/minecraft/util/math/BlockPos;)F"))
    private float getBrightness(WorldView instance, BlockPos pos) {
        return instance.getDimension().getBrightness(LightLevelGetter.get(instance, pos));
    }
    //#else
    //$$ @Redirect(method = "getPathfindingFavor", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/WorldView;getPhototaxisFavor(Lnet/minecraft/util/math/BlockPos;)F"))
    //$$ private float getPhototaxisFavor(WorldView instance, BlockPos blockPos) {
    //$$     final float f = LightLevelGetter.get(instance, blockPos) / 15.0F;
    //$$     final float g = f / (4.0F - 3.0F * f);
    //$$     return net.minecraft.util.math.MathHelper.lerp(instance.getDimension().ambientLight(), g, 1.0F) - 0.5F;
    //$$ }
    //#endif
}
