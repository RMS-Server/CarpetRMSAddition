package rms.carpet_rms_addition.mixin.spawners;

import it.unimi.dsi.fastutil.objects.ReferenceArraySet;
import net.minecraft.entity.EntityType;
import net.minecraft.world.gen.CatSpawner;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import rms.carpet_rms_addition.CarpetRMSAdditionSettings;
import rms.carpet_rms_addition.NaturalSpawnBlacklistEnforcer;

@Mixin(CatSpawner.class)
public abstract class CatSpawnerMixin implements NaturalSpawnBlacklistEnforcer {
    @Unique
    private boolean isInNaturalSpawnBlacklist;
    
    @SuppressWarnings("AddedMixinMembersNamePattern")
    @Override
    public void updateNaturalSpawnBlacklist(final ReferenceArraySet<EntityType<?>> entityTypes) {
        this.isInNaturalSpawnBlacklist = entityTypes.contains(EntityType.CAT);
    }
    
    @Inject(method = "<init>", at = @At("RETURN"))
    private void init(final CallbackInfo ci) {
        this.isInNaturalSpawnBlacklist = CarpetRMSAdditionSettings.getNaturalSpawnBlacklistEntityTypes()
            .contains(EntityType.CAT);
    }
    
    @Inject(
        //#if MC < 12106
        method = "spawn(Lnet/minecraft/server/world/ServerWorld;ZZ)I",
        //#else
        //$$ method = "spawn(Lnet/minecraft/server/world/ServerWorld;ZZ)V",
        //#endif
        at = @At("HEAD"), cancellable = true)
    private void spawn(
        //#if MC < 12106
        final org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable<Integer> cir
        //#else
        //$$ final CallbackInfo ci
        //#endif
    ) {
        if (this.isInNaturalSpawnBlacklist) {
            //#if MC < 12106
            cir.setReturnValue(0);
            //#else
            //$$ ci.cancel();
            //#endif
        }
    }
}
