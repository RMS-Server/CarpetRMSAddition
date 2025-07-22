package rms.carpet_rms_addition.mixin.spawners;

import it.unimi.dsi.fastutil.objects.ReferenceArraySet;
import net.minecraft.entity.EntityType;
import net.minecraft.village.ZombieSiegeManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import rms.carpet_rms_addition.CarpetRMSAdditionSettings;
import rms.carpet_rms_addition.NaturalSpawnBlacklistEnforcer;

@Mixin(ZombieSiegeManager.class)
public abstract class ZombieSiegeManagerMixin implements NaturalSpawnBlacklistEnforcer {
    @Unique
    private boolean isInNaturalSpawnBlacklist;

    @SuppressWarnings("AddedMixinMembersNamePattern")
    @Override
    public void updateNaturalSpawnBlacklist(final ReferenceArraySet<EntityType<?>> entityTypes) {
        this.isInNaturalSpawnBlacklist = entityTypes.contains(EntityType.ZOMBIE);
    }

    @Inject(method = "<init>", at = @At("RETURN"))
    private void init(final CallbackInfo ci) {
        this.isInNaturalSpawnBlacklist = CarpetRMSAdditionSettings.getNaturalSpawnBlacklistEntityTypes().contains(EntityType.ZOMBIE);
    }

    @Inject(method = "spawn(Lnet/minecraft/server/world/ServerWorld;ZZ)I", at = @At("HEAD"), cancellable = true)
    private void spawn(final CallbackInfoReturnable<Integer> cir) {
        if (this.isInNaturalSpawnBlacklist) cir.setReturnValue(0);
    }
}
