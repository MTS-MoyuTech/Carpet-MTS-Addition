package top.mtserver;

import carpet.settings.Rule;

import java.lang.annotation.Repeatable;

import static carpet.settings.RuleCategory.*;

public class MTSCarpetSettings {
    public static final String MTS = "MTS";
    public static final String Spawner = "Spawner";

    @Rule(name = "Cat spawn tracker",
            desc = "Cats are spawned every 1200gt in a village with 5 beds that have been used. When spawning, a player (including spectators) is randomly selected.\nIn X-axis or Z-axis plus-minus 8-32 tiles, a random location is selected, and 1 cat will be generated at the selected location when there are less than 5 cats in the village 2 block.\nAt full moon, cats randomly pick 11 appearances when spawning; At the time of the non-full moon, cats randomly pick 10 appearances other than black cats when spawning.\n      ----Minecraft Wiki",
            category = {SURVIVAL, MTS, Spawner})
    public static boolean CatSpawnerTracker = false;

    @Rule(name = "Cat spawn interval modification",
            desc = "See CatSpawnerTrackern Note that the modified game will still wait for the clock of the previous generator, and this setting will take effect in the next generator.",
            category = {CREATIVE, MTS, Spawner})
    public static int CatSpawnerInterval = 1200;

    @Rule(name = "Special tick chunks",
            desc = "The game randomly ticks 3 blocks in a block for each gt, and this value can be adjusted by modifying the randomk in gamemerule. But then the random ticking squares of each block will increase, resulting in MSPT++, which is not conducive to debugging the machine. This rule allows you to modify the number of random ticks in a single block, which can avoid unnecessary random ticks and reduce MSPT occupation.",
            category = {CREATIVE, MTS, Spawner})
    public static boolean SpecialTickChunks = false;

    @Rule(name = "Fireworks have a fixed life duration",
            desc = "In the vanilla version, the life duration of fireworks was random and uncontrollable\nThis rule will fix the life duration of the fireworks to the firework level * The value of this rule + the duration of 6\nSet this rule to -1 to enable the original random fireworks life",
            category = {SURVIVAL, MTS})
    public static int FireworkLifeTime = -1;

    @Rule(name = "Ice never melts",
            desc = "",
            category = {SURVIVAL, MTS})
    public static boolean IceNeverMelt = false;

    @Rule(name = "The brightness of the melting ice",
            desc = "The vanilla melts at brightness 11",
            category = {SURVIVAL, MTS})
    public static int IceMeltLightLevel = 11;

    @Rule(name = "Ice melts into water no matter what",
            desc = "In the vanilla version, if the ice is broken by the player and there is no block underneath, it will disappear directly",
            category = {SURVIVAL, MTS})
    public static boolean IceMeltAlwaysWater = false;

    @Rule(name = "The probability of an oak sapling splitting when it grows up",
            desc = "Set to 0 to prohibit",
            category = {SURVIVAL, MTS})
    public static int FancyOakTreeProbability = 10;

    @Rule(name = "Leaves never disappear",
            desc = "",
            category = {SURVIVAL, MTS})
    public static boolean LeavesNeverDisappear = false;

    @Rule(name = "How far away the leaves are from the log will disappear",
            desc = "",
            category = {SURVIVAL, MTS})
    public static int LeavesDisappearLogDistance = 7;

    @Rule(name = "Add MSPT to the server without burdening the server",
            desc = "In milliseconds, if it is greater than 200, it will be processed as 200",
            category = {CREATIVE, MTS})
    public static int AddMSPT = 0;

    @Rule(name = "The probability that the sapling will grow after receiving a random tick",
            desc = "The vanilla version is 1/7",
            category = {CREATIVE, MTS})
    public static int SaplingGrowProbability = 14;
}
