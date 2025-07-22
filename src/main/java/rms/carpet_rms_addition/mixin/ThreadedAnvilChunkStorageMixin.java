package rms.carpet_rms_addition.mixin;

import net.minecraft.server.world.ThreadedAnvilChunkStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import rms.carpet_rms_addition.CarpetRMSAdditionSettings;

@Mixin(ThreadedAnvilChunkStorage.class)
public abstract class ThreadedAnvilChunkStorageMixin {
    @Inject(method = "<init>", at = @At("RETURN"))
    private void init(final CallbackInfo ci) {
        CarpetRMSAdditionSettings.registerChunkStorage((ThreadedAnvilChunkStorage) (Object) this);
    }
}
