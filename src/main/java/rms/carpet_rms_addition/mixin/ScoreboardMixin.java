package rms.carpet_rms_addition.mixin;

import net.minecraft.scoreboard.Scoreboard;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Scoreboard.class)
public abstract class ScoreboardMixin {
    //#if MC < 12104
    @org.spongepowered.asm.mixin.injection.ModifyConstant(method = "addObjective", constant = @org.spongepowered.asm.mixin.injection.Constant(intValue = 16))
    private int addObjective(int constant) {
        if (rms.carpet_rms_addition.CarpetRMSAdditionSettings.removeScoreboardLengthLimits) {
            return Integer.MAX_VALUE;
        }
        return 16;
    }

    @org.spongepowered.asm.mixin.injection.ModifyConstant(method = "addTeam", constant = @org.spongepowered.asm.mixin.injection.Constant(intValue = 16))
    private int addTeam(int constant) {
        if (rms.carpet_rms_addition.CarpetRMSAdditionSettings.removeScoreboardLengthLimits) {
            return Integer.MAX_VALUE;
        }
        return 16;
    }
    //#endif
}