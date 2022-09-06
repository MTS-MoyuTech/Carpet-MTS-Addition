package top.mtserver.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.argument.BlockPosArgumentType;
import net.minecraft.command.argument.BlockStateArgument;
import net.minecraft.command.argument.BlockStateArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.math.BlockPos;
import top.mtserver.MTSCarpetServer;
import top.mtserver.utils.CommandDatas;
import top.mtserver.utils.StringUtils.MessageUtil;
import top.mtserver.utils.StringUtils.ToString;

public class SetBlockAfterTimeCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        LiteralArgumentBuilder<ServerCommandSource> builder = CommandManager.literal("setblockaftertime")
                .requires((serverCommandSource) -> serverCommandSource.hasPermissionLevel(2))
                .then(CommandManager.argument("pos", BlockPosArgumentType.blockPos())
                .then(CommandManager.argument("block", BlockStateArgumentType.blockState())
                .then(CommandManager.argument("time", IntegerArgumentType.integer())
                .executes((commandContext) -> AddSetblock(commandContext.getSource(), BlockPosArgumentType.getLoadedBlockPos(commandContext, "pos"), BlockStateArgumentType.getBlockState(commandContext, "block"), IntegerArgumentType.getInteger(commandContext,"time"))))));

        dispatcher.register(builder);
    }

    private static int AddSetblock(ServerCommandSource source, BlockPos pos, BlockStateArgument block, int WaitTime) {
        MTSCarpetServer.Log("Try to SetBlock %block in %pos after %WaitTime gt".replace("%block", block.getBlockState().getBlock().toString().replace("Block{","").replace("}","").replace("%pos",ToString.BlockPosToString(pos).replace("%WaitTime",String.valueOf(WaitTime)))));
        MessageUtil.StringOut(source.getWorld(),MessageUtil.getLang("top.mtserver.command.SetBlockAfterTimeCommand.SetBlockAfter").replace("%block",ToString.BlockToString(block.getBlockState().getBlock())).replace("%pos", ToString.BlockPosToString(pos)).replace("%time",String.valueOf(WaitTime)),true);
        CommandDatas.SetBlockAfterTimeDatas.add(new CommandDatas.SetBlockAfterTimeData(source, pos, block, WaitTime));
        return 1;
    }
}
