package rms.carpet_rms_addition.mixin;

import it.unimi.dsi.fastutil.objects.ReferenceArrayList;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import rms.carpet_rms_addition.CarpetRMSAdditionSettings;
import rms.carpet_rms_addition.UsePortalBlacklistEnforcer;

@Mixin(Entity.class)
public abstract class EntityMixin implements UsePortalBlacklistEnforcer {
    @Shadow
    @Final
    private EntityType<?> type;
    @Unique
    private boolean isInUsePortalBlacklist;

    @SuppressWarnings("AddedMixinMembersNamePattern")
    @Override
    public void updateUsePortalBlacklist(ReferenceArrayList<EntityType<?>> entityTypes) {
        this.isInUsePortalBlacklist = entityTypes.contains(this.type);
    }

    @Inject(method = "<init>", at = @At("RETURN"))
    private void init(EntityType<?> type, World world, CallbackInfo ci) {
        this.isInUsePortalBlacklist = CarpetRMSAdditionSettings.getUsePortalBlacklistEntityTypes().contains(type);
    }

    @Inject(method = "canUsePortals", at = @At("HEAD"), cancellable = true)
    private void canUsePortals(CallbackInfoReturnable<Boolean> cir) {
        if (this.isInUsePortalBlacklist) cir.setReturnValue(false);
    }
}
