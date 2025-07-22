package rms.carpet_rms_addition;

import it.unimi.dsi.fastutil.objects.ReferenceArraySet;
import net.minecraft.entity.EntityType;

public interface AllEntityPacketInterceptor {
    void updateInterceptAllPacketsEntityTypes(final ReferenceArraySet<EntityType<?>> entityTypes);
}
