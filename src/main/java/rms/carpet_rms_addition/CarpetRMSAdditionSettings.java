package rms.carpet_rms_addition;

import carpet.settings.ParsedRule;
import carpet.settings.Rule;
import carpet.settings.Validator;
import it.unimi.dsi.fastutil.objects.ReferenceArrayList;
import net.minecraft.entity.EntityType;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.world.ThreadedAnvilChunkStorage;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.Spawner;

import java.util.List;
import java.util.Optional;

//#if MC >= 12104
//$$ @SuppressWarnings("removal")
//#endif
public final class CarpetRMSAdditionSettings {
    private static final String RMS = "RMS";
    private static final ReferenceArrayList<ThreadedAnvilChunkStorage> chunkStorages = new ReferenceArrayList<>();
    private static final ReferenceArrayList<Spawner> spawners = new ReferenceArrayList<>();
    @SuppressWarnings("unused")
    @Rule(desc = "Override the block light level when spawning monsters", category = {RMS}, options = {"false", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15"}, validate = OverrideBlockLightLevelValidator.class)
    public static String overrideMonsterBlockLightLevel = "false";
    @SuppressWarnings("unused")
    @Rule(desc = "Override the sky light level when spawning monsters", category = {RMS}, options = {"false", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15"}, validate = OverrideSkyLightLevelValidator.class)
    public static String overrideMonsterSkyLightLevel = "false";
    @Rule(desc = "Make /data get return more nbt", category = {RMS})
    public static boolean enhancedDataGet = false;
    @SuppressWarnings("unused")
    @Rule(desc = "A list of entities, in the form of [minecraft:boat,minecraft:creeper], for each the server will not send update packets", category = {RMS}, validate = InterceptUpdatePacketEntitiesValidator.class)
    public static String interceptUpdatePacketEntities = "[]";
    @SuppressWarnings("unused")
    @Rule(desc = "A list of entities, in the form of [minecraft:boat,minecraft:creeper], for each the server will not send all packets and immediately remove from client. This takes precedence over interceptUpdatePacketEntities", category = {RMS}, validate = InterceptAllPacketEntitiesValidator.class)
    public static String interceptAllPacketEntities = "[]";
    @SuppressWarnings("unused")
    @Rule(desc = "A list of entities, in the form of [minecraft:cat,minecraft:creeper], that will not naturally spawn", category = {RMS}, validate = NaturalSpawnBlacklistValidator.class)
    public static String naturalSpawnBlacklist = "[]";
    @SuppressWarnings("unused")
    @Rule(desc = "A list of entities, in the form of [minecraft:boat,minecraft:creeper], that will not go through portals", category = {RMS}, validate = UsePortalBlacklistValidator.class)
    public static String usePortalBlacklist = "[]";
    @Rule(desc = "Stops all particle packets from being sent", category = {RMS})
    public static boolean interceptParticlePackets = false;
    //#if MC < 11802
    @Rule(desc = "Port the behavior of falling blocks from 1.18.2+", category = {RMS})
    public static boolean fallingBlockBackport = false;
    //#endif
    private static int blockLightLevel = -1;
    private static int skyLightLevel = -1;
    private static boolean keepBlockLightLevel = true;
    private static boolean keepSkyLightLevel = true;
    private static ReferenceArrayList<EntityType<?>> interceptUpdatePacketsEntityTypes = new ReferenceArrayList<>();
    private static ReferenceArrayList<EntityType<?>> interceptAllPacketsEntityTypes = new ReferenceArrayList<>();
    private static ReferenceArrayList<EntityType<?>> naturalSpawnBlacklistEntityTypes = new ReferenceArrayList<>();
    private static ReferenceArrayList<EntityType<?>> usePortalBlacklistEntityTypes = new ReferenceArrayList<>();

    public static int getBlockLightLevel() {
        return blockLightLevel;
    }

    public static int getSkyLightLevel() {
        return skyLightLevel;
    }

    public static boolean isKeepingBlockLightLevel() {
        return keepBlockLightLevel;
    }

    public static boolean isKeepingSkyLightLevel() {
        return keepSkyLightLevel;
    }

    public static void registerChunkStorage(ThreadedAnvilChunkStorage chunkStorage) {
        chunkStorages.add(chunkStorage);
    }

    public static void registerSpawners(List<Spawner> spawners) {
        CarpetRMSAdditionSettings.spawners.addAll(spawners);
    }

    public static ReferenceArrayList<EntityType<?>> getInterceptUpdatePacketsEntityTypes() {
        return interceptUpdatePacketsEntityTypes;
    }

    public static ReferenceArrayList<EntityType<?>> getInterceptAllPacketsEntityTypes() {
        return interceptAllPacketsEntityTypes;
    }

    public static ReferenceArrayList<EntityType<?>> getNaturalSpawnBlacklistEntityTypes() {
        return naturalSpawnBlacklistEntityTypes;
    }

    public static ReferenceArrayList<EntityType<?>> getUsePortalBlacklistEntityTypes() {
        return usePortalBlacklistEntityTypes;
    }

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
            keepBlockLightLevel = blockLightLevel == -1;
            return newValue;
        }
    }

    private static class OverrideSkyLightLevelValidator extends Validator<String> {
        @Override
        public String validate(ServerCommandSource source, ParsedRule<String> currentRule, String newValue, String string) {
            skyLightLevel = parseLightLevel(newValue);
            keepSkyLightLevel = skyLightLevel == -1;
            return newValue;
        }
    }

    private abstract static class EntityListValidator extends Validator<String> {
        protected abstract void update(ReferenceArrayList<EntityType<?>> entityTypes);

        @Override
        public String validate(ServerCommandSource source, ParsedRule<String> currentRule, String newValue, String string) {
            final String values = newValue.replaceAll("\\s", "");
            if (values.equals("[]")) {
                this.update(new ReferenceArrayList<>());
                return "[]";
            }
            final int i = values.length() - 1;
            if (values.charAt(0) != '[' || values.charAt(i) != ']') {
                return null;
            }
            final ReferenceArrayList<EntityType<?>> entityTypes = new ReferenceArrayList<>();
            final StringBuilder sb = new StringBuilder("[");
            boolean first = true;
            for (final String value : values.substring(1, i).split(",")) {
                final Identifier identifier = Identifier.tryParse(value);
                if (identifier == null) {
                    return null;
                }
                //#if MC < 12001
                final Optional<EntityType<?>> optionalEntityType = net.minecraft.util.registry.Registry.ENTITY_TYPE.getOrEmpty(identifier);
                //#elseif MC < 12104
                //$$ final Optional<EntityType<?>> optionalEntityType = net.minecraft.registry.Registries.ENTITY_TYPE.getOrEmpty(identifier);
                //#else
                //$$ final Optional<EntityType<?>> optionalEntityType = net.minecraft.registry.Registries.ENTITY_TYPE.getOptionalValue(identifier);
                //#endif
                if (optionalEntityType.isEmpty()) {
                    return null;
                }
                final EntityType<?> entityType = optionalEntityType.get();
                if (entityTypes.contains(entityType)) {
                    continue;
                }
                entityTypes.add(entityType);
                if (first) {
                    sb.append(identifier);
                    first = false;
                } else {
                    sb.append(',').append(identifier);
                }
            }
            this.update(entityTypes);
            return sb.append(']').toString();
        }
    }

    private static class InterceptUpdatePacketEntitiesValidator extends EntityListValidator {
        @Override
        protected void update(ReferenceArrayList<EntityType<?>> entityTypes) {
            interceptUpdatePacketsEntityTypes = entityTypes;
            for (final ThreadedAnvilChunkStorage chunkStorage : chunkStorages) {
                for (final ThreadedAnvilChunkStorage.EntityTracker entityTracker : chunkStorage.entityTrackers.values()) {
                    ((UpdateEntityPacketInterceptor) entityTracker.entry).updateInterceptUpdatePacketsEntityTypes(entityTypes);
                }
            }
        }
    }

    private static class InterceptAllPacketEntitiesValidator extends EntityListValidator {
        @Override
        protected void update(ReferenceArrayList<EntityType<?>> entityTypes) {
            interceptAllPacketsEntityTypes = entityTypes;
            for (final ThreadedAnvilChunkStorage chunkStorage : chunkStorages) {
                for (final ThreadedAnvilChunkStorage.EntityTracker entityTracker : chunkStorage.entityTrackers.values()) {
                    ((AllEntityPacketInterceptor) entityTracker).updateInterceptAllPacketsEntityTypes(entityTypes);
                    ((AllEntityPacketInterceptor) entityTracker.entry).updateInterceptAllPacketsEntityTypes(entityTypes);
                }
            }
        }
    }

    private static class NaturalSpawnBlacklistValidator extends EntityListValidator {
        @Override
        protected void update(ReferenceArrayList<EntityType<?>> entityTypes) {
            naturalSpawnBlacklistEntityTypes = entityTypes;
            for (final Spawner spawner : spawners) {
                ((NaturalSpawnBlacklistEnforcer) spawner).updateNaturalSpawnBlacklist(entityTypes);
            }
        }
    }

    private static class UsePortalBlacklistValidator extends EntityListValidator {
        @Override
        protected void update(ReferenceArrayList<EntityType<?>> entityTypes) {
            usePortalBlacklistEntityTypes = entityTypes;
            for (final ThreadedAnvilChunkStorage chunkStorage : chunkStorages) {
                for (final ThreadedAnvilChunkStorage.EntityTracker entityTracker : chunkStorage.entityTrackers.values()) {
                    ((UsePortalBlacklistEnforcer) entityTracker).updateUsePortalBlacklist(entityTypes);
                }
            }
        }
    }
}