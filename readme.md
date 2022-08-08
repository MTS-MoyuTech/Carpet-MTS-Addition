# Carpet MTS Addition

# 1.16.5
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
- 参考选项: `false`, `true`
- 分类: `NIQIU`, `SPAWNER`, `SURVIVAL`

### 猫咪生成间隔 (CatSpawnerTracker)

mc尝试生成猫咪的间隔时间为1200gt,修改这一规则以修改间隔时间

- 类型: `int`
- 默认值: `1200`
- 参考选项: `1200`, `114514`
- 分类: `NIQIU`, `SPAWNER`, `SURVIVAL`
