package rms.carpet_rms_addition.mixin;

import net.minecraft.util.registry.RegistryKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.WorldGenerationProgressListener;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.level.ServerWorldProperties;
import net.minecraft.world.level.storage.LevelStorage;
import net.minecraft.world.gen.Spawner;
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
    private void init(MinecraftServer server, Executor workerExecutor, LevelStorage.Session session, ServerWorldProperties properties, RegistryKey<World> worldKey,
                      //#if MC < 12104
                      net.minecraft.world.dimension.DimensionType dimensionType,
                      //#else
                      //$$ net.minecraft.world.dimension.DimensionOptions dimensionOptions,
                      //#endif
                      WorldGenerationProgressListener worldGenerationProgressListener,
                      //#if MC < 12104
                      net.minecraft.world.gen.chunk.ChunkGenerator chunkGenerator,
                      //#endif
                      boolean debugWorld, long seed, List<Spawner> spawners, boolean shouldTickTime,
                      //#if MC >= 12104
                      //$$ net.minecraft.util.math.random.RandomSequencesState randomSequencesState,
                      //#endif
                      CallbackInfo ci) {
        CarpetRMSAdditionSettings.registerSpawners(spawners);
    }
}