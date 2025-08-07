# Carpet RMS Addition

[![Modrinth](https://img.shields.io/modrinth/dt/ij9knTzG?label=Modrinth%E4%B8%8B%E8%BD%BD%E9%87%8F)](https://modrinth.com/project/ij9knTzG)

[English](README.md) | **简体中文**

一个 [Minecraft](https://www.minecraft.net/zh-hans) [Carpet模组](https://github.com/gnembon/fabric-carpet) [扩展](https://github.com/gnembon/fabric-carpet/wiki/List-of-Carpet-extensions).

## 规则

### enhancedDataGet

使 `/data get` 返回实体的 `inNetherPortal` 和船实体的 `ticksUnderwater`。

* 默认值：`false`
* 选项：`false`，`true`

### fallingBlockBackport

将 1.18.2+ 的下落方块行为带回低版本。

* 可用版本：`<1.18.2`
* 默认值：`false`
* 选项：`false`，`true`

### interceptAllPacketEntities

拦截列表中实体发送的所有包。这将会覆盖 `interceptUpdatePacketEntities`。在带宽受限时或许有用。

* 默认值：`[]`
* 示例用法：`[boat,minecraft:creeper]`

### interceptParticlePackets

拦截所有的粒子包。在带宽受限时或许有用。

* 默认值：`false`
* 选项：`false`，`true`

### interceptUpdatePacketEntities

拦截列表中实体发送的更新包（移动包和旋转包）。需注意的是，大多数非生物实体（例如矿车，TNT）也会在客户端被计算，所以启用此规则可能会导致客户端显示错误。在带宽受限时或许有用。

* 默认值：`[]`
* 示例用法：`[boat,minecraft:creeper]`

### naturalSpawnBlacklist

黑名单上的实体将不会自然生成。

* 默认值：`[]`
* 示例用法：`[cat,minecraft:creeper]`

### overrideMonsterBlockLightLevel

覆写传递给怪物生成逻辑的方块光照亮度。在设计光照抑制农场时或许有用。

* 默认值：`false`
* 选项：`false`，`0`，`1`，`2`，`3`，`4`，`5`，`6`，`7`，`8`，`9`，`10`，`11`，`12`，`13`，`14`，`15`

### overrideMonsterSkyLightLevel

覆写传递给怪物生成逻辑的天空光照亮度。在设计光照抑制农场时或许有用。

* 默认值：`false`
* 选项：`false`，`0`，`1`，`2`，`3`，`4`，`5`，`6`，`7`，`8`，`9`，`10`，`11`，`12`，`13`，`14`，`15`

### usePortalBlacklist

黑名单上的实体将无法使用传送门。

* 默认值：`[]`
