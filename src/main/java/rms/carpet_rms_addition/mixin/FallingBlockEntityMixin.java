package rms.carpet_rms_addition.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(FallingBlockEntity.class)
public abstract class FallingBlockEntityMixin extends Entity {
    private FallingBlockEntityMixin(final EntityType<?> type, final World world) {
        super(type, world);
    }
    
    //#if MC < 11802
    @org.spongepowered.asm.mixin.injection.Redirect(method = "tick", at = @org.spongepowered.asm.mixin.injection.At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;isOf(Lnet/minecraft/block/Block;)Z", ordinal = 0))
    private boolean isOf(final net.minecraft.block.BlockState instance, final net.minecraft.block.Block block) {
        return !rms.carpet_rms_addition.CarpetRMSAdditionSettings.fallingBlockBackport && instance.isOf(block);
    }
    
    @org.spongepowered.asm.mixin.injection.Redirect(method = "tick", at = @org.spongepowered.asm.mixin.injection.At(value = "FIELD", target = "Lnet/minecraft/world/World;isClient:Z", opcode = org.objectweb.asm.Opcodes.GETFIELD, ordinal = 0))
    private boolean isClient(final World instance) {
        return rms.carpet_rms_addition.CarpetRMSAdditionSettings.fallingBlockBackport || instance.isClient;
    }
    
    @org.spongepowered.asm.mixin.injection.Inject(method = "<init>(Lnet/minecraft/world/World;DDDLnet/minecraft/block/BlockState;)V", at = @org.spongepowered.asm.mixin.injection.At(value = "INVOKE", target = "Lnet/minecraft/entity/FallingBlockEntity;setVelocity(Lnet/minecraft/util/math/Vec3d;)V", ordinal = 0))
    private void init(final World world, final double x, final double y, final double z, final net.minecraft.block.BlockState block, final org.spongepowered.asm.mixin.injection.callback.CallbackInfo ci) {
        if (rms.carpet_rms_addition.CarpetRMSAdditionSettings.fallingBlockBackport) this.setPosition(x, y, z);
    }
    //#endif
}
