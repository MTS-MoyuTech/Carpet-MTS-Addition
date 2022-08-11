package top.mtserver.utils;

import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.chunk.WorldChunk;
import top.mtserver.MTSCarpetSettings;

import java.util.HashMap;
import java.util.Map;

public class SpecialTickChunk {
    public static Map<ChunkPos, Integer> SpecialTickChunks = new HashMap<>();

    public static int get(WorldChunk chunk){
        if (MTSCarpetSettings.SpecialTickChunks && SpecialTickChunk.SpecialTickChunks.containsKey(chunk.getPos())) {
            return SpecialTickChunks.get(chunk.getPos());
        }
        return 3;
    }
}
