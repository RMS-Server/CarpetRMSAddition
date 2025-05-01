package rms;

import carpet.settings.ParsedRule;
import carpet.settings.Rule;
import carpet.settings.Validator;
import net.minecraft.server.command.ServerCommandSource;

public class CarpetRMSAdditionSettings {
    public static final String RMS = "RMS";
    public static int blockLightLevel = -1;
    public static int skyLightLevel = -1;
    @SuppressWarnings("unused")
    @Rule(desc = "Override the block light level when spawning monsters", category = {RMS}, options = {"false", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15"}, validate = OverrideBlockLightLevelValidator.class)
    public static String overrideMonsterBlockLightLevel = "false";
    @SuppressWarnings("unused")
    @Rule(desc = "Override the sky light level when spawning monsters", category = {RMS}, options = {"false", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15"}, validate = OverrideSkyLightLevelValidator.class)
    public static String overrideMonsterSkyLightLevel = "false";
    @Rule(desc = "Make /data get return more nbt", category = {RMS})
    public static boolean enhancedDataGet = false;

    private static int parseLightLevel(final String lightLevel) {
        return switch (lightLevel) {
            case "0" -> 0;
            case "1" -> 1;
            case "2" -> 2;
            case "3" -> 3;
            case "4" -> 4;
            case "5" -> 5;
            case "6" -> 6;
            case "7" -> 7;
            case "8" -> 8;
            case "9" -> 9;
            case "10" -> 10;
            case "11" -> 11;
            case "12" -> 12;
            case "13" -> 13;
            case "14" -> 14;
            case "15" -> 15;
            default -> -1;
        };
    }

    private static class OverrideBlockLightLevelValidator extends Validator<String> {
        @Override
        public String validate(ServerCommandSource source, ParsedRule<String> currentRule, String newValue, String string) {
            blockLightLevel = parseLightLevel(newValue);
            return newValue;
        }
    }

    private static class OverrideSkyLightLevelValidator extends Validator<String> {
        @Override
        public String validate(ServerCommandSource source, ParsedRule<String> currentRule, String newValue, String string) {
            skyLightLevel = parseLightLevel(newValue);
            return newValue;
        }
    }
}