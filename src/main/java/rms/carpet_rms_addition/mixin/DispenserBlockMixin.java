package rms.carpet_rms_addition.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.DispenserBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import rms.carpet_rms_addition.CarpetRMSAdditionSettings;

import static net.minecraft.state.property.Properties.TRIGGERED;

@Mixin(DispenserBlock.class)
public abstract class DispenserBlockMixin extends Block {
    public DispenserBlockMixin(final Settings settings) {
        super(settings);
    }
    
    @Inject(method = "getPlacementState", at = @At("RETURN"), cancellable = true)
    private void getPlacementState(final ItemPlacementContext ctx, final CallbackInfoReturnable<BlockState> cir) {
        if ("silent".equals(CarpetRMSAdditionSettings.selfCheckOnPlacement)) cir.setReturnValue(cir.getReturnValue()
            .with(TRIGGERED, ctx.getWorld().isReceivingRedstonePower(ctx.getBlockPos())));
    }
    
    //#if MC < 12100
    @Inject(method = "onPlaced", at = @At("HEAD"))
    private void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack, CallbackInfo ci) {
        super.onPlaced(world, pos, state, placer, itemStack);
    }
    //#endif
}
