# Carpet MTS Addition
#### On Minecraft 1.16.5
[![License](https://img.shields.io/github/license/MTS-MoyuTech/Carpet-MTS-Addition.svg)](http://www.gnu.org/licenses/lgpl-3.0.html)
[![Issues](https://img.shields.io/github/issues/MTS-MoyuTech/Carpet-MTS-Addition.svg)](https://github.com/MTS-MoyuTech/Carpet-MTS-Addition/issues)
[![Parent](https://img.shields.io/badge/Parent-fabric--carpet-blue)](https://github.com/gnembon/fabric-carpet)

## 这是一个 [Carpet mod](https://github.com/gnembon/fabric-carpet) (fabric-carpet) 的扩展 mod,主要用于调试机器
### 主要由 [泥鳅](https://github.com/bili-08A04-NQ3) 维护
#### [泥鳅b站](https://space.bilibili.com/538864655)

### Alpha版本,功能很少(

## 卡佩特规则

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

### 特殊区块刻 (SpecialTickChunks)

- STC
- 游戏在每gt都会给一个区块中的3个方块随机刻,修改gamerule中的randomtick可调整这一数值. 但是这样每一个区块的随机刻方块都会增加,导致MSPT++,不利于调试机器.
- 这一个规则可以让你修改单个区块的随机刻方块数量,能避免不必要的随机刻,减少MSPT占用.
- 注意,贸然提高区块刻是不文明的行为,即使调整单区块的随机刻,如果过高,依然会引起卡顿
- 经过测试,全局1024随机刻的mspt为4.9,单个区块1024随机刻的mspt为3.5(单人)
- 开启这个功能后,使用/specialtickchunk 来操作特殊区块刻
- /specialtickchunk add (ChunkX) (ChunkZ) (Random Tick Speed):给特殊区块刻添加区块,原版的Random Tick Speed为3
- /specialtickchunk remove (ChunkX) (ChunkZ) 把某个区块移除特殊区块刻
- /specialtickchunk list显示当前特殊区块刻列表
- 默认值: `3`
- 参考选项: `3`, `4096`
- 分类: `MTS`, `SURVIVAL`
- 在v0.1.2a加入

## 命令

### /specialtickchunk (特殊区块刻)

- /specialtickchunk add (ChunkX) (ChunkZ) (Random Tick Speed):给特殊区块刻添加区块,原版的Random Tick Speed为3
- /specialtickchunk remove (ChunkX) (ChunkZ) 把某个区块移除特殊区块刻
- /specialtickchunk list显示当前特殊区块刻列表
- 在v0.1.2a加入

### /setblockaftertime (在几gt后放置方块)

- /setblockaftertime (pos) (block) (WaitTime) 在WaiteTime gt后放置方块
- 在v0.1.3a加入
