package rms;

import carpet.CarpetExtension;
import carpet.CarpetServer;
import net.fabricmc.api.ModInitializer;

public class CarpetRMSAddition implements CarpetExtension, ModInitializer {
    @Override
    public String version() {
        return "carpet-rms-addition";
    }

    @Override
    public void onInitialize() {
        CarpetServer.manageExtension(new CarpetRMSAddition());
    }

    @Override
    public void onGameStarted() {
        CarpetServer.settingsManager.parseSettingsClass(CarpetRMSAdditionSettings.class);
    }
}