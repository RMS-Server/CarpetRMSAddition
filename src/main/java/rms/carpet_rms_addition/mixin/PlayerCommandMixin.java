package rms.carpet_rms_addition.mixin;

import carpet.commands.PlayerCommand;
//#if MC < 12101
import com.mojang.authlib.GameProfile;
import net.minecraft.util.UserCache;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import rms.carpet_rms_addition.CarpetRMSAdditionSettings;
import rms.carpet_rms_addition.OfflineUUIDHelper;

import java.util.Optional;
//#endif

import org.spongepowered.asm.mixin.Mixin;

/**
 * Mixin to skip Mojang API UUID lookup in PlayerCommand.cantSpawn().
 * Only needed for MC < 1.21.1 where Carpet calls Mojang API synchronously.
 */
@Mixin(value = PlayerCommand.class, remap = false)
public abstract class PlayerCommandMixin {
    //#if MC < 12101
    @Redirect(
        method = "cantSpawn",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/util/UserCache;findByName(Ljava/lang/String;)Ljava/util/Optional;"
        ),
        remap = true
    )
    private static Optional<GameProfile> redirectCantSpawnFindByName(UserCache userCache, String name) {
        if (CarpetRMSAdditionSettings.fakePlayerOfflineUUID) {
            return Optional.of(OfflineUUIDHelper.createOfflineProfile(name));
        }
        return userCache.findByName(name);
    }
    //#endif
}
