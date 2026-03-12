package rms.carpet_rms_addition.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import rms.carpet_rms_addition.CarpetRMSAdditionSettings;

@Mixin(FireballEntity.class)
public abstract class FireballEntityMixin extends Entity {
    public FireballEntityMixin(final EntityType<?> type, final World world) {
        super(type, world);
    }
    
    //#if MC < 11903
    @Redirect(method = "onCollision", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;createExplosion(Lnet/minecraft/entity/Entity;DDDFZLnet/minecraft/world/explosion/Explosion$DestructionType;)Lnet/minecraft/world/explosion/Explosion;"))
    private Explosion redirectExplosion(final World instance, final Entity entity, final double x, final double y, final double z, final float power, final boolean createFire, final Explosion.DestructionType destructionType) {
        return world.createExplosion(CarpetRMSAdditionSettings.fireballExplosionCreditBackport ? this : null, x, y, z, power, createFire, destructionType);
    }
    //#endif
}
