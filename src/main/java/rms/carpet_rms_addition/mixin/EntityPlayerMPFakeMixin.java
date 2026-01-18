package rms.carpet_rms_addition.mixin;

import carpet.patches.EntityPlayerMPFake;
import org.spongepowered.asm.mixin.Mixin;

/**
 * Mixin to skip Mojang API UUID lookup when creating fake players.
 * Only needed for MC < 1.21.1 where Carpet calls Mojang API synchronously.
 */
@Mixin(value = EntityPlayerMPFake.class, remap = false)
public abstract class EntityPlayerMPFakeMixin {
    //#if MC < 12101
    @org.spongepowered.asm.mixin.injection.Redirect(method = "createFake", at = @org.spongepowered.asm.mixin.injection.At(value = "INVOKE", target = "Lnet/minecraft/util/UserCache;findByName(Ljava/lang/String;)Ljava/util/Optional;"), remap = true)
    private static java.util.Optional<com.mojang.authlib.GameProfile> redirectFindByName(net.minecraft.util.UserCache userCache, String name) {
        if (rms.carpet_rms_addition.CarpetRMSAdditionSettings.fakePlayerOfflineUUID)
            return java.util.Optional.of(rms.carpet_rms_addition.OfflineUUIDHelper.createOfflineProfile(name));
        return userCache.findByName(name);
    }
    //#endif
}
