package MTSCarpetAddition.utils;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.system.CallbackI;

public class ToString {
    public static String BlockPosToString(BlockPos blockPos) {
        return "[" + blockPos.getX() + "," + blockPos.getY() + "," + blockPos.getZ() + "]";
    }

    public static String SpawnerStartRunning(String Target) {
        return Target + "生成器开始运行!";
    }

    public static String ResetSpawnClock(int Wait) {
        return ",计时器重置到" + Wait + "gt";
    }

    public static String NoPlayerToSpawn() {
        return "没有选择到玩家,停止生成";
    }

    public static String SpawnWithPlayerCenterOffset(PlayerEntity Player, BlockPos blockPos, int x, int y, int z) {
        if (y == 0) {
            return "生成器以玩家" + Player.getEntityName() + "为中心,在" + ToString.BlockPosToString(blockPos) + "(以玩家坐标向x偏移" + x + "格,z偏移" + z + "格)尝试生成";
        } else {
            return "生成器以玩家" + Player.getEntityName() + "为中心,在" + ToString.BlockPosToString(blockPos) + "(以玩家坐标向x偏移" + x + "格,y偏移" + y + "格,z偏移" + z + ")尝试生成";
        }
    }

    public static String AreaNotLoadingGiveUpSpawn() {
        return "区块未加载,放弃生成";
    }
}