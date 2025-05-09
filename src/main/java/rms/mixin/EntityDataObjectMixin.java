package rms.mixin;

import net.minecraft.command.EntityDataObject;
import net.minecraft.entity.Entity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.predicate.NbtPredicate;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import rms.CarpetRMSAdditionSettings;

@Mixin(EntityDataObject.class)
public abstract class EntityDataObjectMixin {
    @Redirect(method = "getNbt", at = @At(value = "INVOKE", target = "Lnet/minecraft/predicate/NbtPredicate;entityToNbt(Lnet/minecraft/entity/Entity;)Lnet/minecraft/nbt/NbtCompound;"))
    private NbtCompound entityToNbt(Entity entity) {
        final NbtCompound nbtCompound = NbtPredicate.entityToNbt(entity);
        if (CarpetRMSAdditionSettings.enhancedDataGet) {
            nbtCompound.putBoolean("inNetherPortal", entity.inNetherPortal);
            if (entity instanceof BoatEntity boatEntity) {
                nbtCompound.putFloat("ticksUnderwater", boatEntity.ticksUnderwater);
            }
        }
        return nbtCompound;
    }
}