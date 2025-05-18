package rms.carpet_rms_addition;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;
import net.minecraft.world.chunk.light.ChunkLightProvider;

public final class LightLevelGetter {
    public static int get(WorldView instance, BlockPos blockPos, int i) {
        final boolean keepBlockLightLevel = CarpetRMSAdditionSettings.isKeepingBlockLightLevel();
        final boolean keepSkyLightLevel = CarpetRMSAdditionSettings.isKeepingSkyLightLevel();
        if (keepBlockLightLevel && keepSkyLightLevel) {
            return instance.getLightLevel(blockPos, i);
        }
        final int x = blockPos.getX();
        final int z = blockPos.getZ();
        final boolean isInsideWorld = x > -30000000 && z > -30000000 && x < 30000000 && z < 30000000;
        if (keepBlockLightLevel) {
            final ChunkLightProvider<?, ?> blockEngine = instance.getLightingProvider().blockLightProvider;
            return isInsideWorld ? Math.max(blockEngine == null ? 0 : blockEngine.getLightLevel(blockPos), CarpetRMSAdditionSettings.getSkyLightLevel() - i) : 15;
        }
        if (keepSkyLightLevel) {
            final ChunkLightProvider<?, ?> skyEngine = instance.getLightingProvider().skyLightProvider;
            return isInsideWorld ? Math.max(CarpetRMSAdditionSettings.getBlockLightLevel(), skyEngine == null ? 0 : skyEngine.getLightLevel(blockPos) - i) : 15;
        }
        return isInsideWorld ? Math.max(CarpetRMSAdditionSettings.getBlockLightLevel(), CarpetRMSAdditionSettings.getSkyLightLevel() - i) : 15;
    }

    public static int get(WorldView instance, BlockPos blockPos) {
        return get(instance, blockPos, instance.getAmbientDarkness());
    }
}