package rms.carpet_rms_addition;

import it.unimi.dsi.fastutil.objects.ReferenceArraySet;
import net.minecraft.entity.EntityType;

public interface UsePortalBlacklistEnforcer {
    void updateUsePortalBlacklist(final ReferenceArraySet<EntityType<?>> entityTypes);
}
