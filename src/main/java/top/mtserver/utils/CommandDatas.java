package top.mtserver.utils;

import net.minecraft.block.BlockState;
import net.minecraft.command.argument.BlockStateArgument;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.chunk.WorldChunk;
import top.mtserver.MTSCarpetSettings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CommandDatas {
    public static Map<ChunkPos, Integer> SpecialTickChunkData = new HashMap<>();

    //STC
    public static int getSpecialTickChunks(WorldChunk chunk) {
        if (MTSCarpetSettings.SpecialTickChunks && CommandDatas.SpecialTickChunkData.containsKey(chunk.getPos())) {
            return SpecialTickChunkData.get(chunk.getPos());
        }
        return 3;
    }

    //SAT
    public static class SetBlockAfterTimeData {
        public final ServerCommandSource source;
        public final BlockPos blockPos;
        public final BlockStateArgument blockState;
        public int waitTime;

        public SetBlockAfterTimeData(ServerCommandSource source, BlockPos blockPos, BlockStateArgument blockState, int WaitTime) {
            this.source = source;
            this.blockPos = blockPos;
            this.blockState = blockState;
            this.waitTime = WaitTime;
        }

        public boolean tick() {
            if (waitTime <= 0) {
                if (blockState.setBlockState(source.getWorld(), blockPos, 2)) {
                    source.getWorld().updateNeighbors(blockPos, blockState.getBlockState().getBlock());
                    source.sendFeedback(new TranslatableText("commands.setblock.success", blockPos.getX(), blockPos.getY(), blockPos.getZ()), true);
                }
                return true;
            }
            return false;
        }
    }

    public static ArrayList<SetBlockAfterTimeData> SetBlockAfterTimeDatas = new ArrayList<>();

    public static void SetBlockAfterTime() {
        ArrayList<SetBlockAfterTimeData> F = new ArrayList<>();
        for (SetBlockAfterTimeData setBlockAfterTimeData : SetBlockAfterTimeDatas) {
            if (!setBlockAfterTimeData.tick()) {
                F.add(setBlockAfterTimeData);
            }
        }
        SetBlockAfterTimeDatas = F;
    }

    //BRT
    public static ArrayList<BlockState> BlockRandomTickLog = new ArrayList<>();

    /*
    每个区域随机刻监视都有一个编号
     */
    public static class AreaRandomTickTrackerData {
        public final ServerCommandSource source;
        public final BlockPos RealStartPos;
        public final BlockPos RealEndPos;

        public AreaRandomTickTrackerData(ServerCommandSource source, BlockPos start, BlockPos end) {
            this.source = source;
            this.RealStartPos = new BlockPos(Math.max(start.getX(), end.getX()), Math.max(start.getY(), end.getY()), Math.max(start.getZ(), end.getZ()));
            this.RealEndPos = new BlockPos(Math.min(start.getX(), end.getX()), Math.min(start.getY(), end.getY()), Math.min(start.getZ(), end.getZ()));
        }

        public boolean inInRange(BlockPos pos) {
            return pos.getX() <= RealStartPos.getX() && pos.getX() >= RealEndPos.getX() && pos.getY() <= RealStartPos.getY() && pos.getY() >= RealEndPos.getY() && pos.getZ() <= RealStartPos.getZ() && pos.getZ() >= RealEndPos.getZ();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            AreaRandomTickTrackerData data = (AreaRandomTickTrackerData) o;
            return Objects.equals(source, data.source) && Objects.equals(RealStartPos, data.RealStartPos) && Objects.equals(RealEndPos, data.RealEndPos);
        }

        @Override
        public int hashCode() {
            return Objects.hash(source, RealStartPos, RealEndPos);
        }
    }

    public static Map<Integer, AreaRandomTickTrackerData> AreaRandomTickTrackerDatas = new HashMap<>();
}
