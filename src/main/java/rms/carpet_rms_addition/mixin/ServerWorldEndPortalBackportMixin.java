package rms.carpet_rms_addition.mixin;

import net.minecraft.block.Blocks;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import rms.carpet_rms_addition.CarpetRMSAdditionSettings;

import java.util.function.Consumer;

import static net.minecraft.server.world.ServerWorld.END_SPAWN_POS;

@Mixin(ServerWorld.class)
public class ServerWorldEndPortalBackportMixin {
    @Inject(
        method = "createEndSpawnPlatform",
        at = @At("HEAD")
    )
    private static void createEndSpawnPlatform(ServerWorld world, CallbackInfo ci) {
        if (!CarpetRMSAdditionSettings.endPlatformBreakingBackport) {
            BlockPos blockPos = END_SPAWN_POS;
            int i = blockPos.getX();
            int j = blockPos.getY() - 2;
            int k = blockPos.getZ();
            BlockPos.iterate(i - 2, j + 1, k - 2, i + 2, j + 3, k + 2)
                .forEach((blockPosx) -> {
                    if (!world.getBlockState(blockPosx).isAir()) world.breakBlock(blockPosx, true, null, 512);
                });
            BlockPos.iterate(i - 2, j, k - 2, i + 2, j, k + 2)
                .forEach((blockPosx) -> {
                    if (!world.getBlockState(blockPosx).isOf(Blocks.OBSIDIAN))
                        world.breakBlock(blockPosx, true, null, 512);
                });
        }
    }
}
