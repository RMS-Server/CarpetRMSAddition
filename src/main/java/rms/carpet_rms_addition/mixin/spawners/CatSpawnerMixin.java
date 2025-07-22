package rms.carpet_rms_addition.mixin.spawners;

import it.unimi.dsi.fastutil.objects.ReferenceArrayList;
import net.minecraft.entity.EntityType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.gen.CatSpawner;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import rms.carpet_rms_addition.CarpetRMSAdditionSettings;
import rms.carpet_rms_addition.NaturalSpawnBlacklistEnforcer;

@Mixin(CatSpawner.class)
public abstract class CatSpawnerMixin implements NaturalSpawnBlacklistEnforcer {
    @Unique
    private boolean isInNaturalSpawnBlacklist;

    @SuppressWarnings("AddedMixinMembersNamePattern")
    @Override
    public void updateNaturalSpawnBlacklist(ReferenceArrayList<EntityType<?>> entityTypes) {
        this.isInNaturalSpawnBlacklist = entityTypes.contains(EntityType.CAT);
    }

    @Inject(method = "<init>", at = @At("RETURN"))
    private void init(CallbackInfo ci) {
        this.isInNaturalSpawnBlacklist = CarpetRMSAdditionSettings.getNaturalSpawnBlacklistEntityTypes().contains(EntityType.CAT);
    }

    @Inject(method = "spawn(Lnet/minecraft/server/world/ServerWorld;ZZ)I", at = @At("HEAD"), cancellable = true)
    private void spawn(ServerWorld world, boolean spawnMonsters, boolean spawnAnimals, CallbackInfoReturnable<Integer> cir) {
        if (this.isInNaturalSpawnBlacklist) cir.setReturnValue(0);
    }
}
