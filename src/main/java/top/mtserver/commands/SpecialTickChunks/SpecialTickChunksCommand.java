package top.mtserver.commands.SpecialTickChunks;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.math.ChunkPos;

import top.mtserver.MTSCarpetServer;
import top.mtserver.MTSCarpetSettings;
import top.mtserver.utils.CommandDatas;
import top.mtserver.utils.StringUtils.MessageUtil;

import static com.mojang.brigadier.arguments.IntegerArgumentType.getInteger;
import static com.mojang.brigadier.arguments.IntegerArgumentType.integer;

public class SpecialTickChunksCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        LiteralArgumentBuilder<ServerCommandSource> literalargumentbuilder = CommandManager.literal("specialtickchunk").
                requires((serverCommandSource) -> serverCommandSource.hasPermissionLevel(2)).
                //To Do: Add suggestion to this command
                then(CommandManager.literal("add")
                        .then(CommandManager.argument("ChunkX", integer())
                        .then(CommandManager.argument("ChunkZ", integer())
                        .then(CommandManager.argument("randomTickSpeed", integer(0,65536))
                        .executes((c) -> AddChunkPos(c.getSource(), getInteger(c, "ChunkX"), getInteger(c, "ChunkZ"), getInteger(c, "randomTickSpeed")))))))
                .then(CommandManager.literal("remove")
                        .then(CommandManager.argument("ChunkX", integer())
                        .then(CommandManager.argument("ChunkZ", integer()))
                        .executes((c) -> RemoveChunkPos(c.getSource(), getInteger(c, "ChunkX"), getInteger(c, "ChunkZ")))))
                .then(CommandManager.literal("list")
                        .executes((c) -> ListCHunkPos(c.getSource())));

        dispatcher.register(literalargumentbuilder);
    }

    public static int AddChunkPos(ServerCommandSource source, int ChunkX, int ChunkZ, int randomTickSpeed) throws CommandSyntaxException {
        CommandDatas.SpecialTickChunkData.put(new ChunkPos(ChunkX, ChunkZ), randomTickSpeed);
        System.out.println(CommandDatas.SpecialTickChunkData.get(new ChunkPos(ChunkX, ChunkZ)));
        if (!MTSCarpetSettings.SpecialTickChunks){
            MessageUtil.Out(source.getWorld(),"top.mtserver.command.SpecialTickChunks.warn",true);
        }
        MTSCarpetServer.Log("Add %x %z with speed %speed to STC".replace("%x",String.valueOf(ChunkX)).replace("%z",String.valueOf(ChunkZ)).replace("%speed",String.valueOf(randomTickSpeed)));
        MessageUtil.StringOut(source.getWorld(), MessageUtil.getLang("top.mtserver.command.SpecialTickChunks.AddChunkPos").replace("%ChunkX", String.valueOf(ChunkX)).replace("%ChunkZ", String.valueOf(ChunkZ)).replace("%randomTickSpeed", String.valueOf(randomTickSpeed)), true);
        return 1;
    }

    public static int RemoveChunkPos(ServerCommandSource source, int ChunkX, int ChunkZ) {
        ChunkPos chunkPos = new ChunkPos(ChunkX, ChunkZ);
        MTSCarpetServer.Log("Remove %x %z on STC".replace("%x",String.valueOf(ChunkX)).replace("%z",String.valueOf(ChunkZ)));
        if (!CommandDatas.SpecialTickChunkData.containsKey(chunkPos)) {
            MessageUtil.StringOut(source.getWorld(),  MessageUtil.getLang("top.mtserver.command.SpecialTickChunks.RemoveChunkPosSuccess").replace("ChunkX",String.valueOf(ChunkX)).replace("ChunkZ",String.valueOf(ChunkZ)), true);
        } else {
            CommandDatas.SpecialTickChunkData.remove(new ChunkPos(ChunkX, ChunkZ));
            MessageUtil.StringOut(source.getWorld(), MessageUtil.getLang("top.mtserver.command.SpecialTickChunks.RemoveChunkPosFailed").replace("ChunkX",String.valueOf(ChunkX)).replace("ChunkZ",String.valueOf(ChunkZ)), true);
        }
        return 1;
    }

    public static int ListCHunkPos(ServerCommandSource source){
        MessageUtil.StringOut(source.getWorld(),MessageUtil.getLang("top.mtserver.command.SpecialTickChunks.list"),true);
        for (ChunkPos pos: CommandDatas.SpecialTickChunkData.keySet()){
           MessageUtil.StringOut(source.getWorld(),"Chunk Pos" + pos.getRegionX() + "," + pos.getRegionZ() + "   Speed:" + CommandDatas.SpecialTickChunkData.get(pos),true);
        }
        return 1;
    }
}