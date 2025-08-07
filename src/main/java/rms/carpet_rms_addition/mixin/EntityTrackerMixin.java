package rms.carpet_rms_addition.mixin;

import it.unimi.dsi.fastutil.objects.ReferenceArraySet;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.server.network.EntityTrackerEntry;
import net.minecraft.server.world.EntityTrackingListener;
import net.minecraft.server.world.ThreadedAnvilChunkStorage;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import rms.carpet_rms_addition.AllEntityPacketInterceptor;
import rms.carpet_rms_addition.CarpetRMSAdditionSettings;
import rms.carpet_rms_addition.UsePortalBlacklistEnforcer;

import java.util.Set;

@Mixin(ThreadedAnvilChunkStorage.EntityTracker.class)
public abstract class EntityTrackerMixin implements AllEntityPacketInterceptor, UsePortalBlacklistEnforcer {
    @Shadow
    @Final
    public EntityTrackerEntry entry;
    @Shadow
    @Final
    Entity entity;
    @Shadow
    @Final
    private Set<EntityTrackingListener> listeners;
    @Unique
    private EntityType<?> type;
    @Unique
    private boolean intercept;
    
    @SuppressWarnings("AddedMixinMembersNamePattern")
    @Override
    public void updateInterceptAllPacketsEntityTypes(final ReferenceArraySet<EntityType<?>> entityTypes) {
        final boolean intercept = entityTypes.contains(this.type);
        if (intercept && !this.intercept) {
            for (final EntityTrackingListener listener : this.listeners) {
                this.entry.stopTracking(listener.getPlayer());
            }
        } else if (!intercept && this.intercept) {
            for (final EntityTrackingListener listener : this.listeners) {
                this.entry.startTracking(listener.getPlayer());
            }
        }
        this.intercept = intercept;
    }
    
    @SuppressWarnings("AddedMixinMembersNamePattern")
    @Override
    public void updateUsePortalBlacklist(final ReferenceArraySet<EntityType<?>> entityTypes) {
        ((UsePortalBlacklistEnforcer) this.entity).updateUsePortalBlacklist(entityTypes);
    }
    
    @Inject(method = "<init>", at = @At("RETURN"))
    private void init(final ThreadedAnvilChunkStorage threadedAnvilChunkStorage, final Entity entity, final int maxDistance, final int tickInterval, final boolean alwaysUpdateVelocity, final CallbackInfo ci) {
        this.type = entity.getType();
        this.intercept = CarpetRMSAdditionSettings.getInterceptAllPacketsEntityTypes().contains(this.type);
    }
    
    @Redirect(method = "updateTrackedStatus(Lnet/minecraft/server/network/ServerPlayerEntity;)V", at = @At(value = "INVOKE", target = "Ljava/util/Set;add(Ljava/lang/Object;)Z"))
    private <E> boolean add(final Set<E> instance, final E e) {
        if (this.intercept) {
            return false;
        }
        return instance.add(e);
    }
}
