package top.mtserver.utils;

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

public class CommandDatas {
    public static Map<ChunkPos, Integer> SpecialTickChunkData = new HashMap<>();

    public static int getSpecialTickChunks(WorldChunk chunk) {
        if (MTSCarpetSettings.SpecialTickChunks && CommandDatas.SpecialTickChunkData.containsKey(chunk.getPos())) {
            return SpecialTickChunkData.get(chunk.getPos());
        }
        return 3;
    }

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
            --waitTime;
            if (waitTime <= 0) {
                if (blockState.setBlockState(source.getWorld(), blockPos, 2)) {
                    source.getWorld().updateNeighbors(blockPos, blockState.getBlockState().getBlock());
                    source.sendFeedback(new TranslatableText("commands.setblock.success", blockPos.getX(), blockPos.getY(), blockPos.getZ()), true);
                }
                return true;
            }
            return false;
        }

        public SetBlockAfterTimeData WaitTimeMin(){
            --waitTime;
            return this;
        }
    }

    public static ArrayList<SetBlockAfterTimeData> SetBlockAfterTimeDatas = new ArrayList<>();

    public static void SetBlockAfterTime(){
        ArrayList<SetBlockAfterTimeData> F = new ArrayList<>();
        for (SetBlockAfterTimeData setBlockAfterTimeData:SetBlockAfterTimeDatas){
            if (!setBlockAfterTimeData.tick()){
                F.add(setBlockAfterTimeData.WaitTimeMin());
            }
        }
        SetBlockAfterTimeDatas = F;
    }
}
