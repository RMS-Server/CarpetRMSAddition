package rms.carpet_rms_addition.mixin;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.WorldGenerationProgressListener;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import net.minecraft.world.gen.Spawner;
import net.minecraft.world.level.ServerWorldProperties;
import net.minecraft.world.level.storage.LevelStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import rms.carpet_rms_addition.CarpetRMSAdditionSettings;

import java.util.List;
import java.util.concurrent.Executor;

@Mixin(ServerWorld.class)
public abstract class ServerWorldMixin {
    @Inject(method = "<init>", at = @At("RETURN"))
    private void init(final MinecraftServer server, final Executor workerExecutor, final LevelStorage.Session session, final ServerWorldProperties properties, final RegistryKey<World> worldKey,
                      //#if MC < 12001
                      final net.minecraft.world.dimension.DimensionType dimensionType, final WorldGenerationProgressListener worldGenerationProgressListener, final net.minecraft.world.gen.chunk.ChunkGenerator chunkGenerator,
                      //#else
                      //$$ final net.minecraft.world.dimension.DimensionOptions dimensionOptions, final WorldGenerationProgressListener worldGenerationProgressListener,
                      //#endif
                      final boolean debugWorld, final long seed, final List<Spawner> spawners, final boolean shouldTickTime,
                      //#if MC >= 12001
                      //$$ final net.minecraft.util.math.random.RandomSequencesState randomSequencesState,
                      //#endif
                      final CallbackInfo ci) {
        CarpetRMSAdditionSettings.registerSpawners(spawners);
    }
}
