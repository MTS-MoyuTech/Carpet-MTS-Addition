package top.mtserver.utils;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.system.CallbackI;

public class ToString {
    public static String BlockPosToString(BlockPos blockPos) {
        return "[" + blockPos.getX() + "," + blockPos.getY() + "," + blockPos.getZ() + "]";
    }

    public static String SpawnerStartRunning(String Target) {
        return Target + MessageUtil.getLang("top.mtserver.string.SpawnerStart");//生成器开始运行
    }

    public static String ResetSpawnClock(int Wait) {
        return "," + MessageUtil.getLang("top.mtserver.string.ResetClock") + Wait + "gt";
    }//计时器重置到

    public static String NoPlayerToSpawn() {
        return MessageUtil.getLang("top.mtserver.string.NoPlayerToSpawn");
    }

    public static String SpawnWithPlayerCenterOffset(PlayerEntity Player, BlockPos blockPos, int x, int y, int z) {
        if (y == 0) {//return "生成器以玩家" + Player.getEntityName() + "为中心,在" + ToString.BlockPosToString(blockPos) + "(以玩家坐标向x偏移" + x + "格,z偏移" + z + "格)尝试生成";
            return MessageUtil.getLang("top.mtserver.string.SpawnChoosePlayer").replaceAll("%name",Player.getEntityName()).replaceAll("%block",ToString.BlockPosToString(blockPos)).replaceAll("%offsetX",String.valueOf(x)).replaceAll("%offsetZ",String.valueOf(z));
        }
        return "";
    }

    public static String AreaNotLoadingGiveUpSpawn() {
        return MessageUtil.getLang("top.mtserver.AreaNotLoadingGiveUpSpawn");
    }

    public static String Cat = MessageUtil.getLang("top.mtserver.string.cat");
}