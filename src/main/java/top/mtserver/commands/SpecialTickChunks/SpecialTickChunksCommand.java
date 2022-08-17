package top.mtserver.commands.SpecialTickChunks;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.ChunkPos;

import net.minecraft.world.chunk.Chunk;
import top.mtserver.MTSCarpetSettings;
import top.mtserver.utils.SpecialTickChunk;
import top.mtserver.utils.MessageUtil;

import static com.mojang.brigadier.arguments.IntegerArgumentType.getInteger;
import static com.mojang.brigadier.arguments.IntegerArgumentType.integer;

public class SpecialTickChunksCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        LiteralArgumentBuilder<ServerCommandSource> literalargumentbuilder = CommandManager.literal("specialtickchunk").
                requires((serverCommandSource) -> serverCommandSource.hasPermissionLevel(0)).
                //To Do: Add suggestion to this command
                then(CommandManager.literal("add").
                        then(CommandManager.argument("ChunkX", integer()).then(CommandManager.argument("ChunkZ", integer()).then(CommandManager.argument("randomTickSpeed", integer()).
                                executes((c) -> AddChunkPos(c.getSource(), getInteger(c, "ChunkX"), getInteger(c, "ChunkZ"), getInteger(c, "randomTickSpeed")))))))
                .then(CommandManager.literal("remove").
                        then(CommandManager.argument("ChunkX", integer()).then(CommandManager.argument("ChunkZ", integer())).
                                executes((c) -> RemoveChunkPos(c.getSource(), getInteger(c, "ChunkX"), getInteger(c, "ChunkZ")))))
                .then(CommandManager.literal("list")).executes((c) -> ListCHunkPos(c.getSource()));

        dispatcher.register(literalargumentbuilder);
    }

    public static int AddChunkPos(ServerCommandSource source, int ChunkX, int ChunkZ, int randomTickSpeed) throws CommandSyntaxException {
        SpecialTickChunk.SpecialTickChunks.put(new ChunkPos(ChunkX, ChunkZ), randomTickSpeed);
        System.out.println(SpecialTickChunk.SpecialTickChunks.get(new ChunkPos(ChunkX, ChunkZ)));
        if (!MTSCarpetSettings.SpecialTickChunks){
            MessageUtil.Out(source.getWorld(),"top.mtserver.command.SpecialTickChunks.warn",true);
        }
        MessageUtil.StringOut(source.getWorld(), MessageUtil.getLang("top.mtserver.command.SpecialTickChunks.AddChunkPos").replace("%ChunkX", String.valueOf(ChunkX)).replace("%ChunkZ", String.valueOf(ChunkZ)).replace("%randomTickSpeed", String.valueOf(randomTickSpeed)), true);
        return 1;
    }

    public static int RemoveChunkPos(ServerCommandSource source, int ChunkX, int ChunkZ) {
        ChunkPos chunkPos = new ChunkPos(ChunkX, ChunkZ);
        if (!SpecialTickChunk.SpecialTickChunks.containsKey(chunkPos)) {
            MessageUtil.StringOut(source.getWorld(),  MessageUtil.getLang("top.mtserver.command.SpecialTickChunks.RemoveChunkPosSuccess").replace("ChunkX",String.valueOf(ChunkX)).replace("ChunkZ",String.valueOf(ChunkZ)), true);
        } else {
            SpecialTickChunk.SpecialTickChunks.remove(new ChunkPos(ChunkX, ChunkZ));
            MessageUtil.StringOut(source.getWorld(), MessageUtil.getLang("top.mtserver.command.SpecialTickChunks.RemoveChunkPosFailed").replace("ChunkX",String.valueOf(ChunkX)).replace("ChunkZ",String.valueOf(ChunkZ)), true);
        }
        return 1;
    }

    public static int ListCHunkPos(ServerCommandSource source){
        MessageUtil.StringOut(source.getWorld(),MessageUtil.getLang("top.mtserver.command.SpecialTickChunks.list"),true);
        for (ChunkPos pos: SpecialTickChunk.SpecialTickChunks.keySet()){
           MessageUtil.StringOut(source.getWorld(),"Chunk Pos" + pos.getRegionX() + "," + pos.getRegionZ() + "   Speed:" + SpecialTickChunk.SpecialTickChunks.get(pos),true);
        }
        return 1;
    }
}