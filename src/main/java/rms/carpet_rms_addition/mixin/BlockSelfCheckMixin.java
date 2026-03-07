package rms.carpet_rms_addition.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import rms.carpet_rms_addition.CarpetRMSAdditionSettings;

@Mixin(Block.class)
public class BlockSelfCheckMixin {
    @SuppressWarnings("deprecation")
    @Inject(method = "onPlaced", at = @At("HEAD"))
    private void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack, CallbackInfo ci) {
        if ("trigger".equals(CarpetRMSAdditionSettings.selfCheckOnPlacement)) {
            if (state.isOf(Blocks.NOTE_BLOCK)) Blocks.NOTE_BLOCK.neighborUpdate(state, world, pos, Blocks.NOTE_BLOCK,
                //#if MC < 12104
                pos,
                //#else
                //$$null,
                //#endif
                false);
            else if (state.isOf(Blocks.DISPENSER)) Blocks.DISPENSER.neighborUpdate(state, world, pos, Blocks.DISPENSER,
                //#if MC < 12104
                pos,
                //#else
                //$$null,
                //#endif
                false);
            else if (state.isOf(Blocks.DROPPER)) Blocks.DROPPER.neighborUpdate(state, world, pos, Blocks.DROPPER,
                //#if MC < 12104
                pos,
                //#else
                //$$null,
                //#endif
                false);
        }
    }
}
