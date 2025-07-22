package rms.carpet_rms_addition.mixin;

import it.unimi.dsi.fastutil.objects.ReferenceArrayList;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.thrown.ThrownEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import rms.carpet_rms_addition.CarpetRMSAdditionSettings;
import rms.carpet_rms_addition.UsePortalBlacklistEnforcer;

@Mixin(ThrownEntity.class)
public abstract class ThrownEntityMixin extends Entity implements UsePortalBlacklistEnforcer {
    //#if MC < 12104
    @SuppressWarnings("unused")
    //#endif
    @Unique
    private boolean isInUsePortalBlacklist;

    private ThrownEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @SuppressWarnings("AddedMixinMembersNamePattern")
    @Override
    public void updateUsePortalBlacklist(ReferenceArrayList<EntityType<?>> entityTypes) {
        this.isInUsePortalBlacklist = entityTypes.contains(this.getType());
    }

    @Inject(method = "<init>(Lnet/minecraft/entity/EntityType;Lnet/minecraft/world/World;)V", at = @At("RETURN"))
    private void init(EntityType<?> entityType, World world, CallbackInfo ci) {
        this.isInUsePortalBlacklist = CarpetRMSAdditionSettings.getUsePortalBlacklistEntityTypes().contains(entityType);
    }

    @Inject(method = "<init>(Lnet/minecraft/entity/EntityType;DDDLnet/minecraft/world/World;)V", at = @At("RETURN"))
    private void init(EntityType<?> type, double x, double y, double z, World world, CallbackInfo ci) {
        this.isInUsePortalBlacklist = CarpetRMSAdditionSettings.getUsePortalBlacklistEntityTypes().contains(type);
    }

    //#if MC >= 12104
    //$$ @Inject(method = "canUsePortals", at = @At("HEAD"), cancellable = true)
    //$$ private void canUsePortals(boolean allowVehicles, org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable<Boolean> cir) {
    //$$     if (this.isInUsePortalBlacklist) cir.setReturnValue(false);
    //$$ }
    //#endif
}
