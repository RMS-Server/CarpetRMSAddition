package rms.carpet_rms_addition.mixin.spawners;

import net.minecraft.entity.EntityType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.gen.CatSpawner;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import rms.carpet_rms_addition.CarpetRMSAdditionSettings;

@Mixin(CatSpawner.class)
public abstract class CatSpawnerMixin {
    @Inject(method = "spawn(Lnet/minecraft/server/world/ServerWorld;ZZ)I", at = @At("HEAD"), cancellable = true)
    private void spawn(ServerWorld world, boolean spawnMonsters, boolean spawnAnimals, CallbackInfoReturnable<Integer> cir) {
        if (CarpetRMSAdditionSettings.getNaturalSpawnBlacklistEntityTypes().contains(EntityType.CAT)) {
            cir.setReturnValue(0);
        }
    }
}