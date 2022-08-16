package top.mtserver;

import carpet.settings.Rule;

import java.lang.annotation.Repeatable;

import static carpet.settings.RuleCategory.*;

public class MTSCarpetSettings {
    public static final String MTS = "MTS";
    public static final String Spawner = "Spawner";

    @Rule(desc = "rule.CatSpawnerTracker.desc",
            category = {SURVIVAL, MTS, Spawner})
    public static boolean CatSpawnerTracker = false;

    @Rule(desc = "rule.CatSpawnerInterval.desc",
            category = {CREATIVE, MTS, Spawner})
    public static int CatSpawnerInterval = 1200;

    @Rule(desc = "rule.SpecialTickChunks.desc", category = {CREATIVE, MTS, Spawner})
    public static boolean SpecialTickChunks = false;

    @Rule(desc = "rule.FireworkLifeTime.desc",
            category = {SURVIVAL, MTS})
    public static int FireworkLifeTime = -1;

    @Rule(desc = "rule.IceNeverMelt.desc",
            category = {SURVIVAL, MTS})
    public static boolean IceNeverMelt = false;

    @Rule(desc = "rule.IceMeltAlwaysWater.desc",
            category = {SURVIVAL, MTS})
    public static int IceMeltLightLevel = 11;

    @Rule(desc = "rule.IceMeltAlwaysWater.desc",
            category = {SURVIVAL, MTS})
    public static boolean IceMeltAlwaysWater = false;

    @Rule(desc = "rule.FancyOakTreeProbability.desc",
            category = {SURVIVAL, MTS})
    public static int FancyOakTreeProbability = 10;

    @Rule(desc = "rule.LeavesNeverDisappear.desc",
            category = {SURVIVAL, MTS})
    public static boolean LeavesNeverDisappear = false;

    @Rule(desc = "rule.LeavesDisappearLogDistance.desc",
            category = {SURVIVAL, MTS})
    public static int LeavesDisappearLogDistance = 7;

    @Rule(desc = "rule.AddMSPT.desc",
            category = {CREATIVE, MTS})
    public static int AddMSPT = 0;

    @Rule(desc = "rule.SaplingGrowProbability.desc",
            category = {CREATIVE, MTS})
    public static int SaplingGrowProbability = 14;
}
