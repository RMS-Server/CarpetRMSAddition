package rms.carpet_rms_addition.mixin;

import carpet.settings.SettingsManager;
import carpet.utils.Messenger;
import carpet.utils.Translations;
import net.minecraft.server.command.ServerCommandSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import rms.carpet_rms_addition.CarpetRMSAddition;

@Mixin(SettingsManager.class)
public abstract class SettingsManagerMixin {
    @Inject(method = "listAllSettings", at = @At(value = "INVOKE", target = "Lcarpet/settings/SettingsManager;getCategories()Ljava/lang/Iterable;", ordinal = 0), remap = false)
    private void listAllSettings(final ServerCommandSource source, final CallbackInfoReturnable<Integer> cir) {
        Messenger.m(source, "g " + CarpetRMSAddition.getName() + " " + Translations.tr("ui.version", "version") + ": " + CarpetRMSAddition.getVersion());
    }
}
