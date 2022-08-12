# Carpet MTS Addition
#### On Minecraft 1.16.5
[![License](https://img.shields.io/github/license/MTS-MoyuTech/Carpet-MTS-Addition.svg)](http://www.gnu.org/licenses/lgpl-3.0.html)
[![Issues](https://img.shields.io/github/issues/MTS-MoyuTech/Carpet-MTS-Addition.svg)](https://github.com/Rene8028/carpet-iee-addition/issues)
[![Parent](https://img.shields.io/badge/Parent-fabric--carpet-blue)](https://github.com/gnembon/fabric-carpet)

## 这是一个 [Carpet mod](https://github.com/gnembon/fabric-carpet) (fabric-carpet) 的扩展 mod,主要用于调试机器

### Alpha版本,功能很少(

## 规则

### 猫咪生成器追踪 (CatSpawnerTracker)

在mc生成猫咪(不是豹猫)时,把相应的信息发送到生成器同维度的玩家

- 类型: `boolean`
- 默认值: `false`
- 分类: `MTS`, `SPAWNER`, `SURVIVAL`
- 在v0.0.1a加入

### 猫咪生成间隔 (CatSpawnerInterval)

mc尝试生成猫咪的间隔时间为1200gt,修改这一规则以修改间隔时间

- 类型: `int`
- 默认值: `1200`
- 参考选项: `1200`, `114514`
- 分类: `MTS`, `SPAWNER`, `SURVIVAL`
- 在v0.0.1a加入

### 烟花火箭生存时间 (FireworkLifeTime)

原版中,烟花的生命时长是随机的,无法控制
这条规则会把烟花的生命时长固定成 烟花等级 * 这条规则的数值 + 6 的时长
将这条规则设置为-1以启用原版随机烟花生命

- 类型: `int`
- 默认值: `-1`
- 参考选项: `-1`, `10`
- 分类: `MTS`, `SURVIVAL`
- 在v0.0.2a加入

### 冰的融化亮度 (IceMeltLightLevel)

- 类型: `int`
- 默认值: `11`
- 参考选项: `11`, `0`
- 分类: `MTS`, `SURVIVAL`
- 在v0.0.3a加入

### 冰永远不会融化 (IceNeverMelt)

- 类型: `boolean`
- 默认值: `false`
- 分类: `MTS`, `SURVIVAL`
- 在v0.0.3a加入
