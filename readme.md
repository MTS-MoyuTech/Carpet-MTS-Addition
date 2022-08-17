# Carpet MTS Addition
## 这个项目主要由[泥鳅](https://github.com/bili-08A04-NQ3)维护&更新
#### On Minecraft 1.16.5
[![License](https://img.shields.io/github/license/Rene8028/carpet-iee-addition.svg)](http://www.gnu.org/licenses/lgpl-3.0.html)
[![Issues](https://img.shields.io/github/issues/Rene8028/carpet-iee-addition.svg)](https://github.com/Rene8028/carpet-iee-addition/issues)
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

原版中,冰会在亮度11时融化

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

### 冰不管怎么样都会融化成水 (IceMeltAlwaysWater)

原版中,假如冰融化时下面没有方块,就会化成空气

- 类型: `boolean`
- 默认值: `false`
- 分类: `MTS`, `SURVIVAL`
- 在v0.0.4a加入

### 橡木树苗长大后分叉的概率 (FancyOakTreeProbability)

大橡树挺烦人的,要不ban了吧
设置成100可以生成超多大橡树(bushi
这个功能好像有点问题


- 类型: `int`
- 默认值: `10`
- 参考选项: `100`, `10`, `0`
- 分类: `MTS`, `SURVIVAL`
- 在v0.0.4a加入

### 树叶永远不会消失 (LeavesNeverDisappear)

树叶那么可爱,怎么能消失呢?

- 类型: `boolean`
- 默认值: `false`
- 分类: `MTS`, `SURVIVAL`
- 在v0.0.4a加入

### 树叶离原木多远会消失 (LeavesDisappearLogDistance)

- 类型: `int`
- 默认值: `7`
- 参考选项: `7`, `10`
- 分类: `MTS`, `SURVIVAL`
- 在v0.0.4a加入

### 给服务器增加MSPT,单位毫秒 (AddMSPT)

要是大于200会当200处理

- 类型: `int`
- 默认值: `0`
- 参考选项: `0`, `10`
- 分类: `MTS`, `SURVIVAL`
- 在v0.0.5a加入

### 树苗接受随机刻后长大的概率 (SaplingGrowProbability)

原版为1/7,约等于14%

- 类型: `int`
- 默认值: `14`
- 参考选项: `14`, `100`
- 分类: `MTS`, `SURVIVAL`
- 在v0.0.5a加入
