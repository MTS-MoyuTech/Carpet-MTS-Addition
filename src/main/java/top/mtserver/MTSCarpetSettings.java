package top.mtserver;

import carpet.settings.Rule;

import java.lang.annotation.Repeatable;

import static carpet.settings.RuleCategory.*;

public class MTSCarpetSettings {
    public static final String MTS = "MTS";
    public static final String Spawner = "Spawner";

    @Rule(desc = "猫咪生成追踪器\n猫咪在一个拥有5张已被使用的床的村庄中,猫会每隔1200gt生成.生成时,会随机选择一位玩家(包含旁观者)," +
            "在X轴或Z轴正负8-32格中基于玩家的位置,随机选择一个位置,距离村庄2区块内的猫少于5只时就会在选定的位置生成1只猫." +
            "在满月时,猫在生成时会随机挑选11种外观;在非满月时,猫在生成时会随机挑选除黑猫外的10种外观.      ----Minecraft Wiki",
            category = {SURVIVAL, MTS, Spawner})
    public static boolean CatSpawnerTracker = false;

    @Rule(desc = "猫咪生成间隔修改,详情见 CatSpawnerTracker\n" +
            "注意,修改后游戏仍然会等待上一个生成器的时钟," +
            "这个设置将在下一生成器生效",
            category = {CREATIVE, MTS, Spawner})
    public static int CatSpawnerInterval = 1200;

    @Rule(desc = """
            特殊区块刻
            这个功能尚未实现!
            游戏在每gt都会给一个区块中的3个方块随机刻,修改gamerule中的randomtick可调整这一数值.但是这样每一个区块的随机刻方块都会增加,导致MSPT++,不利于调试机器.这一个规则可以让你修改单个区块的随机刻方块数量,能避免不必要的随机刻,减少MSPT占用.""", category = {CREATIVE, MTS, Spawner})
    public static boolean SpecialTickChunks = false;

    @Rule(desc = "烟花固定生命时长\n原版中,烟花的生命时长是随机的,无法控制\n这条规则会把烟花的生命时长固定成 烟花等级 * 这条规则的数值 + 6 的时长\n将这条规则设置为-1以启用原版随机烟花生命",
            category = {SURVIVAL, MTS})
    public static int FireworkLifeTime = -1;

    @Rule(desc = "冰永远都不会融化",
            category = {SURVIVAL, MTS})
    public static boolean IceNeverMelt = false;

    @Rule(desc = "冰的融化亮度",
            category = {SURVIVAL, MTS})
    public static int IceMeltLightLevel = 11;

    @Rule(desc = "冰不管怎么样都会融化成水",
            category = {SURVIVAL, MTS})
    public static boolean IceMeltAlwaysWater = false;

    @Rule(desc = "橡木树苗长大后分叉的概率\n设置为0以禁止",
            category = {SURVIVAL, MTS})
    public static int FancyOakTreeProbability = 10;

    @Rule(desc = "树叶永远不会消失",
            category = {SURVIVAL, MTS})
    public static boolean LeavesNeverDisappear = false;

    @Rule(desc = "树叶离原木多远会消失",
            category = {SURVIVAL, MTS})
    public static int LeavesDisappearLogDistance = 7;

    @Rule(desc = "给服务器增加MSPT,单位毫秒,要是大于200会当200处理",
            category = {CREATIVE, MTS})
    public static int AddMSPT = 0;

    @Rule(desc = "树苗接受随机刻后长大的概率,原版为1/7",
            category = {CREATIVE, MTS})
    public static int SaplingGrowProbability = 14;
}
