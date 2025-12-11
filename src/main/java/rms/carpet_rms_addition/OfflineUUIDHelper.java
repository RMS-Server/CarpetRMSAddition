package rms.carpet_rms_addition;

import com.mojang.authlib.GameProfile;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * Helper class for generating offline UUIDs.
 * Uses the same algorithm as Minecraft's offline mode.
 */
public final class OfflineUUIDHelper {
    
    private OfflineUUIDHelper() {}
    
    /**
     * Generate an offline UUID from a player name.
     * This is the same algorithm used by Minecraft servers in offline mode.
     * 
     * @param name the player name
     * @return the offline UUID
     */
    public static UUID getOfflineUUID(String name) {
        return UUID.nameUUIDFromBytes(("OfflinePlayer:" + name).getBytes(StandardCharsets.UTF_8));
    }
    
    /**
     * Create a GameProfile with an offline UUID.
     * 
     * @param name the player name
     * @return a GameProfile with offline UUID
     */
    public static GameProfile createOfflineProfile(String name) {
        return new GameProfile(getOfflineUUID(name), name);
    }
}
