package top.mtserver.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.math.ChunkPos;

import top.mtserver.MTSCarpetServer;
import top.mtserver.MTSCarpetSettings;
import top.mtserver.commands.Arguments.ChunkPosArgument;
import top.mtserver.utils.CommandDatas;
import top.mtserver.utils.StringUtils.MessageUtil;

import com.mojang.brigadier.arguments.IntegerArgumentType;

public class SpecialTickChunksCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        LiteralArgumentBuilder<ServerCommandSource> builder = CommandManager.literal("specialtickchunk").
                requires((serverCommandSource) -> serverCommandSource.hasPermissionLevel(2))
                        .then(CommandManager.literal("add")
                        .then(CommandManager.argument("Chunk", ChunkPosArgument.ChunkPos())
                        .then(CommandManager.argument("randomTickSpeed", IntegerArgumentType.integer(0, 65536))
                        .executes((c) -> AddChunkPos(c.getSource(), ChunkPosArgument.getLoadedChunkPos(c, "Chunk"), IntegerArgumentType.getInteger(c, "randomTickSpeed"))))))
                .then(CommandManager.literal("remove")
                        .then(CommandManager.argument("ChunkX", ChunkPosArgument.ChunkPos())
                        .executes((c) -> RemoveChunkPos(c.getSource(), ChunkPosArgument.getLoadedChunkPos(c, "Chunk")))))
                .then(CommandManager.literal("list")
                        .executes((c) -> ListChunkPos(c.getSource())));

        dispatcher.register(builder);
    }

    public static int AddChunkPos(ServerCommandSource source, ChunkPos chunkPos, int randomTickSpeed) {
        CommandDatas.SpecialTickChunkData.put(chunkPos, randomTickSpeed);
        int ChunkX = chunkPos.x;
        int ChunkZ = chunkPos.z;
        if (!MTSCarpetSettings.SpecialTickChunks) {
            MessageUtil.Out(source.getWorld(), "top.mtserver.command.SpecialTickChunks.warn", true);
        }
        MTSCarpetServer.Log("Add %x %z with speed %speed to STC".replace("%x", String.valueOf(ChunkX)).replace("%z", String.valueOf(ChunkZ)).replace("%speed", String.valueOf(randomTickSpeed)));
        MessageUtil.StringOut(source.getWorld(), MessageUtil.getLang("top.mtserver.command.SpecialTickChunks.AddChunkPos").replace("%ChunkX", String.valueOf(ChunkX)).replace("%ChunkZ", String.valueOf(ChunkZ)).replace("%randomTickSpeed", String.valueOf(randomTickSpeed)), true);
        return 1;
    }

    public static int RemoveChunkPos(ServerCommandSource source, ChunkPos chunkPos) {
        int ChunkX = chunkPos.x;
        int ChunkZ = chunkPos.z;
        MTSCarpetServer.Log("Remove %x %z on STC".replace("%x", String.valueOf(ChunkX)).replace("%z", String.valueOf(ChunkZ)));
        if (CommandDatas.SpecialTickChunkData.containsKey(chunkPos)) {
            CommandDatas.SpecialTickChunkData.remove(new ChunkPos(ChunkX, ChunkZ));
            MessageUtil.StringOut(source.getWorld(), MessageUtil.getLang("top.mtserver.command.SpecialTickChunks.RemoveChunkPosSuccess").replace("%ChunkX", String.valueOf(ChunkX)).replace("%ChunkZ", String.valueOf(ChunkZ)), true);
        } else {
            MessageUtil.StringOut(source.getWorld(), MessageUtil.getLang("top.mtserver.command.SpecialTickChunks.RemoveChunkPosFailed").replace("%ChunkX", String.valueOf(ChunkX)).replace("%ChunkZ", String.valueOf(ChunkZ)), true);
        }
        return 1;
    }

    public static int ListChunkPos(ServerCommandSource source) {
        MessageUtil.StringOut(source.getWorld(), MessageUtil.getLang("top.mtserver.command.SpecialTickChunks.list"), true);
        for (ChunkPos pos : CommandDatas.SpecialTickChunkData.keySet()) {
            MessageUtil.StringOut(source.getWorld(), "Chunk Pos " + pos.getRegionX() + "," + pos.getRegionZ() + " Speed:" + CommandDatas.SpecialTickChunkData.get(pos), true);
        }
        return 1;
    }
}