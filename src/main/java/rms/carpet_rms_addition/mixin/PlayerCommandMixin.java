package rms.carpet_rms_addition.mixin;

import carpet.commands.PlayerCommand;
import org.spongepowered.asm.mixin.Mixin;

/**
 * Mixin to skip Mojang API UUID lookup in PlayerCommand.cantSpawn().
 * Only needed for MC < 1.21.1 where Carpet calls Mojang API synchronously.
 */
@Mixin(value = PlayerCommand.class, remap = false)
public abstract class PlayerCommandMixin {
    //#if MC < 12101
    @org.spongepowered.asm.mixin.injection.Redirect(method = "cantSpawn", at = @org.spongepowered.asm.mixin.injection.At(value = "INVOKE", target = "Lnet/minecraft/util/UserCache;findByName(Ljava/lang/String;)Ljava/util/Optional;"), remap = true)
    private static java.util.Optional<com.mojang.authlib.GameProfile> redirectCantSpawnFindByName(net.minecraft.util.UserCache userCache, String name) {
        if (rms.carpet_rms_addition.CarpetRMSAdditionSettings.fakePlayerOfflineUUID)
            return java.util.Optional.of(rms.carpet_rms_addition.OfflineUUIDHelper.createOfflineProfile(name));
        return userCache.findByName(name);
    }
    //#endif
}
