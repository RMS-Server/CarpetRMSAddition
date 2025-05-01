package rms.mixin;

import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.LightType;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.WorldView;
import net.minecraft.world.chunk.light.ChunkLightProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import rms.CarpetRMSAdditionSettings;

@Mixin(HostileEntity.class)
public abstract class HostileEntityMixin {
    @Redirect(method = "isSpawnDark", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/ServerWorldAccess;getLightLevel(Lnet/minecraft/world/LightType;Lnet/minecraft/util/math/BlockPos;)I"))
    private static int getLightLevel(ServerWorldAccess instance, LightType lightLayer, BlockPos blockPos) {
        return switch (lightLayer) {
            case SKY ->
                    CarpetRMSAdditionSettings.skyLightLevel == -1 ? instance.getLightLevel(LightType.SKY, blockPos) : CarpetRMSAdditionSettings.skyLightLevel;
            case BLOCK ->
                    CarpetRMSAdditionSettings.blockLightLevel == -1 ? instance.getLightLevel(LightType.BLOCK, blockPos) : CarpetRMSAdditionSettings.blockLightLevel;
        };
    }

    @Unique
    private static int getLightLevelImpl(WorldView instance, BlockPos blockPos, int i) {
        final boolean keepBlockLightLevel = CarpetRMSAdditionSettings.blockLightLevel == -1;
        final boolean keepSkyLightLevel = CarpetRMSAdditionSettings.skyLightLevel == -1;
        if (keepBlockLightLevel && keepSkyLightLevel) {
            return instance.getLightLevel(blockPos, i);
        }
        final int x = blockPos.getX();
        final int z = blockPos.getZ();
        final boolean isInsideWorld = x > -30000000 && z > -30000000 && x < 30000000 && z < 30000000;
        if (keepBlockLightLevel) {
            final ChunkLightProvider<?, ?> blockEngine = ((LightingProviderAccessor) instance.getLightingProvider()).getBlockLightProvider();
            return isInsideWorld ? Math.max(blockEngine == null ? 0 : blockEngine.getLightLevel(blockPos), CarpetRMSAdditionSettings.skyLightLevel - i) : 15;
        }
        if (keepSkyLightLevel) {
            final ChunkLightProvider<?, ?> skyEngine = ((LightingProviderAccessor) instance.getLightingProvider()).getSkyLightProvider();
            return isInsideWorld ? Math.max(CarpetRMSAdditionSettings.blockLightLevel, skyEngine == null ? 0 : skyEngine.getLightLevel(blockPos) - i) : 15;
        }
        return isInsideWorld ? Math.max(CarpetRMSAdditionSettings.blockLightLevel, CarpetRMSAdditionSettings.skyLightLevel - i) : 15;
    }

    @Unique
    private static int getLightLevelImpl(WorldView instance, BlockPos blockPos) {
        return getLightLevelImpl(instance, blockPos, instance.getAmbientDarkness());
    }

    @Redirect(method = "isSpawnDark", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/ServerWorldAccess;getLightLevel(Lnet/minecraft/util/math/BlockPos;I)I"))
    private static int getLightLevel(ServerWorldAccess instance, BlockPos blockPos, int i) {
        return getLightLevelImpl(instance, blockPos, i);
    }

    @Redirect(method = "isSpawnDark", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/ServerWorldAccess;getLightLevel(Lnet/minecraft/util/math/BlockPos;)I"))
    private static int getLightLevel(ServerWorldAccess instance, BlockPos blockPos) {
        return getLightLevelImpl(instance, blockPos);
    }

    @Redirect(method = "getPathfindingFavor", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/WorldView;getBrightness(Lnet/minecraft/util/math/BlockPos;)F"))
    private float getBrightness(WorldView instance, BlockPos pos) {
        return instance.getDimension().getBrightness(getLightLevelImpl(instance, pos));
    }
}