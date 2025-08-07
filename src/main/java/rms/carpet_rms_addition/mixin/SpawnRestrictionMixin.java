package rms.carpet_rms_addition.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ServerWorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import rms.carpet_rms_addition.CarpetRMSAdditionSettings;

@Mixin(SpawnRestriction.class)
public abstract class SpawnRestrictionMixin {
    @Inject(method = "canSpawn", at = @At("HEAD"), cancellable = true)
    private static void canSpawn(final EntityType<?> type, final ServerWorldAccess world, final SpawnReason spawnReason, final BlockPos pos,
        //#if MC < 12001
        final java.util.Random random,
        //#else
        //$$ final net.minecraft.util.math.random.Random random,
        //#endif
        final CallbackInfoReturnable<Boolean> cir) {
        if (CarpetRMSAdditionSettings.getNaturalSpawnBlacklistEntityTypes().contains(type)) cir.setReturnValue(false);
    }
}
