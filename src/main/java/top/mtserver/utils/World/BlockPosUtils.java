package top.mtserver.utils.World;

import net.minecraft.client.render.SkyProperties;
import net.minecraft.util.math.BlockPos;

public class BlockPosUtils {
    public static BlockPos[] FormatBlockPosToHHHLLL(BlockPos start, BlockPos end) {
        BlockPos RealStartPos = new BlockPos(Math.max(start.getX(), end.getX()), Math.max(start.getY(), end.getY()), Math.max(start.getZ(), end.getZ()));
        BlockPos RealEndPos = new BlockPos(Math.min(start.getX(), end.getX()), Math.min(start.getY(), end.getY()), Math.min(start.getZ(), end.getZ()));
        return new BlockPos[]{RealStartPos, RealEndPos};
    }
}
