package top.mtserver.commands.SpecialTickChunks;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.math.ChunkPos;

import top.mtserver.utils.SpecialTickChunk;
import top.mtserver.utils.SendMessage;

import static com.mojang.brigadier.arguments.IntegerArgumentType.getInteger;
import static com.mojang.brigadier.arguments.IntegerArgumentType.integer;

public class SpecialTickChunksCommand{
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        LiteralArgumentBuilder<ServerCommandSource> literalargumentbuilder = CommandManager.literal("specialtickchunk").
                requires((serverCommandSource) -> serverCommandSource.hasPermissionLevel(0)).
                //To Do: Add suggestion to this command
                then(CommandManager.literal("add").
                        then(CommandManager.argument("ChunkX", integer()).then(CommandManager.argument("ChunkZ", integer()).then(CommandManager.argument("randomTickSpeed", integer()).
                                executes((c) -> AddChunkPos(c.getSource(), getInteger(c, "ChunkX"), getInteger(c,"ChunkZ"),getInteger(c,"randomTickSpeed")))))))
                .then(CommandManager.literal("remove").
                        then(CommandManager.argument("ChunkX", integer()).then(CommandManager.argument("ChunkZ", integer())).
                                executes((c) -> RemoveChunkPos(c.getSource(),getInteger(c, "ChunkX"), getInteger(c,"ChunkZ")))));

        dispatcher.register(literalargumentbuilder);
    }

    public static int AddChunkPos(ServerCommandSource source, int ChunkX, int ChunkZ, int randomTickSpeed) throws CommandSyntaxException {
        SpecialTickChunk.SpecialTickChunks.put(new ChunkPos(ChunkX, ChunkZ), randomTickSpeed);
        System.out.println(SpecialTickChunk.SpecialTickChunks.get(new ChunkPos(ChunkX, ChunkZ)));
        SendMessage.Out(source.getWorld(), source.getPlayer().getPlayerListName().toString(),true);
        SendMessage.Out(source.getWorld(), "在坐标" + ChunkX + "," + ChunkZ + "的区块以" + randomTickSpeed + "的数值加入特殊区块刻!", true);
        return 1;
    }

    public static int RemoveChunkPos(ServerCommandSource source, int ChunkX, int ChunkZ) {
        ChunkPos chunkPos = new ChunkPos(ChunkX, ChunkZ);
        if (!SpecialTickChunk.SpecialTickChunks.containsKey(chunkPos)) {
            SendMessage.Out(source.getWorld(), "在坐标" + ChunkX + "," + ChunkZ + "的区块未列入特殊区块刻!", true);
        } else {
            SpecialTickChunk.SpecialTickChunks.remove(new ChunkPos(ChunkX, ChunkZ));
            SendMessage.Out(source.getWorld(), "在坐标" + ChunkX + "," + ChunkZ + "的区块已移出特殊区块刻!", true);
        }
        return 1;
    }
}