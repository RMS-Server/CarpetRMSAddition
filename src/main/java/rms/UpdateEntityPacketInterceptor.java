package rms;

import it.unimi.dsi.fastutil.objects.ReferenceArrayList;
import net.minecraft.entity.EntityType;

public interface UpdateEntityPacketInterceptor {
    void updateInterceptUpdatePacketsEntityTypes(ReferenceArrayList<EntityType<?>> entityTypes);
}