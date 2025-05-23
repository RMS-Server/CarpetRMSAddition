package rms.carpet_rms_addition.mixin;

import net.minecraft.server.command.ScoreboardCommand;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ScoreboardCommand.class)
public abstract class ScoreboardCommandMixin {
    //#if MC < 12104
    @org.spongepowered.asm.mixin.injection.ModifyConstant(method = "executeAddObjective", constant = @org.spongepowered.asm.mixin.injection.Constant(intValue = 16))
    private static int executeAddObjective(int constant) {
        if (rms.carpet_rms_addition.CarpetRMSAdditionSettings.removeScoreboardLengthLimits) {
            return Integer.MAX_VALUE;
        }
        return 16;
    }
    //#endif
}