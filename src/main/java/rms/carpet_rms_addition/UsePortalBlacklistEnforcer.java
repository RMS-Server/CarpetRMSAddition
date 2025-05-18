package rms.carpet_rms_addition;

import it.unimi.dsi.fastutil.objects.ReferenceArrayList;
import net.minecraft.entity.EntityType;

public interface UsePortalBlacklistEnforcer {
    void updateUsePortalBlacklist(ReferenceArrayList<EntityType<?>> entityTypes);
}
