package rms.carpet_rms_addition;

import it.unimi.dsi.fastutil.objects.ReferenceArrayList;
import net.minecraft.entity.EntityType;

public interface AllEntityPacketInterceptor {
    void updateInterceptAllPacketsEntityTypes(ReferenceArrayList<EntityType<?>> entityTypes);
}
