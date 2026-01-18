package rms.carpet_rms_addition.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.NoteBlock;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.property.Properties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import rms.carpet_rms_addition.CarpetRMSAdditionSettings;

@Mixin(NoteBlock.class)
public class NoteBlockMixin {
    @Inject(method = "getPlacementState", at = @At("RETURN"), cancellable = true)
    private void getPlacementState(final ItemPlacementContext ctx, final CallbackInfoReturnable<BlockState> cir) {
        if (CarpetRMSAdditionSettings.updateNoteBlockOnPlacement) cir.setReturnValue(cir.getReturnValue()
            .with(Properties.POWERED, ctx.getWorld().isReceivingRedstonePower(ctx.getBlockPos())));
    }
}
