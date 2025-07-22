package rms.carpet_rms_addition.mixin;

import net.minecraft.command.EntityDataObject;
import net.minecraft.entity.Entity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.predicate.NbtPredicate;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import rms.carpet_rms_addition.CarpetRMSAdditionSettings;

@Mixin(EntityDataObject.class)
public abstract class EntityDataObjectMixin {
    @Redirect(method = "getNbt", at = @At(value = "INVOKE", target = "Lnet/minecraft/predicate/NbtPredicate;entityToNbt(Lnet/minecraft/entity/Entity;)Lnet/minecraft/nbt/NbtCompound;"))
    private NbtCompound entityToNbt(Entity entity) {
        final NbtCompound nbtCompound = NbtPredicate.entityToNbt(entity);
        if (CarpetRMSAdditionSettings.enhancedDataGet) {
            //#if MC < 12101
            nbtCompound.putBoolean("inNetherPortal", entity.inNetherPortal);
            //#else
            //$$ if (entity.portalManager == null) nbtCompound.putBoolean("inNetherPortal", false);
            //$$ else nbtCompound.putBoolean("inNetherPortal", entity.portalManager.isInPortal());
            //#endif
            if (entity instanceof BoatEntity boatEntity)
                nbtCompound.putFloat("ticksUnderwater", boatEntity.ticksUnderwater);
        }
        return nbtCompound;
    }
}
