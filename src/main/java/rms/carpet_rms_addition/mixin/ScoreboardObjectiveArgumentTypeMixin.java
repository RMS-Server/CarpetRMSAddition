package rms.carpet_rms_addition.mixin;

import net.minecraft.command.argument.ScoreboardObjectiveArgumentType;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ScoreboardObjectiveArgumentType.class)
public abstract class ScoreboardObjectiveArgumentTypeMixin {
    //#if MC < 12104
    @org.spongepowered.asm.mixin.injection.ModifyConstant(method = "parse(Lcom/mojang/brigadier/StringReader;)Ljava/lang/String;", constant = @org.spongepowered.asm.mixin.injection.Constant(intValue = 16))
    private int parse(int constant) {
        if (rms.carpet_rms_addition.CarpetRMSAdditionSettings.removeScoreboardLengthLimits) {
            return Integer.MAX_VALUE;
        }
        return 16;
    }
    //#endif
}