package top.mtserver.utils;

import com.google.common.collect.Maps;
import net.minecraft.util.Util;
import net.minecraft.util.math.ChunkPos;

import java.util.Map;

public class SpecialTickChunk {
    public static Map<ChunkPos, Integer> SpecialTickChunks = Util.make(Maps.newHashMap(), hashMap -> {});
}
