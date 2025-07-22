package rms.carpet_rms_addition.mixin;

import it.unimi.dsi.fastutil.objects.ReferenceArrayList;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.network.Packet;
import net.minecraft.server.network.EntityTrackerEntry;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import rms.carpet_rms_addition.AllEntityPacketInterceptor;
import rms.carpet_rms_addition.CarpetRMSAdditionSettings;
import rms.carpet_rms_addition.UpdateEntityPacketInterceptor;

import java.util.function.Consumer;

@Mixin(EntityTrackerEntry.class)
public abstract class EntityTrackerEntryMixin implements UpdateEntityPacketInterceptor, AllEntityPacketInterceptor {
    @Unique
    private EntityType<?> type;
    @Unique
    private boolean interceptUpdate;
    @Unique
    private boolean interceptAll;
    @Unique
    private boolean intercept;

    @SuppressWarnings("AddedMixinMembersNamePattern")
    @Override
    public void updateInterceptUpdatePacketsEntityTypes(ReferenceArrayList<EntityType<?>> entityTypes) {
        this.interceptUpdate = entityTypes.contains(this.type);
        this.intercept = this.interceptUpdate || this.interceptAll;
    }

    @SuppressWarnings("AddedMixinMembersNamePattern")
    @Override
    public void updateInterceptAllPacketsEntityTypes(ReferenceArrayList<EntityType<?>> entityTypes) {
        this.interceptAll = entityTypes.contains(this.type);
        this.intercept = this.interceptUpdate || this.interceptAll;
    }

    @Inject(method = "<init>", at = @At("RETURN"))
    private void init(ServerWorld world, Entity entity, int tickInterval, boolean alwaysUpdateVelocity, Consumer<Packet<?>> receiver, CallbackInfo ci) {
        this.type = entity.getType();
        this.interceptUpdate = CarpetRMSAdditionSettings.getInterceptUpdatePacketsEntityTypes().contains(this.type);
        this.interceptAll = CarpetRMSAdditionSettings.getInterceptAllPacketsEntityTypes().contains(this.type);
        this.intercept = this.interceptUpdate || this.interceptAll;
    }

    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    private void tick(CallbackInfo ci) {
        if (this.intercept) ci.cancel();
    }
}
