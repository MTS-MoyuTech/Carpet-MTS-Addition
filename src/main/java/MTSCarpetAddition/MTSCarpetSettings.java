package MTSCarpetAddition;

import carpet.settings.Rule;

import static carpet.settings.RuleCategory.*;

public class MTSCarpetSettings {
    public static final String NiQiu = "泥鳅";
    public static final String Spawner = "生成器";

    @Rule(desc = "猫咪生成追踪器\n猫咪在一个拥有5张已被使用的床的村庄中,猫会每隔1200gt生成.生成时,会随机选择一位玩家(包含旁观者)," +
            "在X轴或Z轴正负8-32格中基于玩家的位置,随机选择一个位置,距离村庄2区块内的猫少于5只时就会在选定的位置生成1只猫." +
            "在满月时,猫在生成时会随机挑选11种外观;在非满月时,猫在生成时会随机挑选除黑猫外的10种外观.      ----Minecraft Wiki", category = {SURVIVAL, NiQiu, Spawner})
    public static boolean CatSpawnerTracker = false;

    @Rule(desc = "猫咪生成间隔修改,详情见 CatSpawnerTracker\n" +
            "注意,修改后游戏仍然会等待上一个生成器的时钟," +
            "这个设置将在下一生成器生效", category = {SURVIVAL, NiQiu, Spawner}, options = {"1200", "114514"})
    public static int CatSpawnerInterval = 1200;
}
