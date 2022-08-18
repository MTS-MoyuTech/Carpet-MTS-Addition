package top.mtserver.commands.SetBlockAfterTime;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.argument.BlockPosArgumentType;
import net.minecraft.command.argument.BlockStateArgument;
import net.minecraft.command.argument.BlockStateArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.math.BlockPos;
import top.mtserver.utils.CommandDatas;
import top.mtserver.utils.StringUtils.MessageUtil;
import top.mtserver.utils.StringUtils.ToString;

public class SetBlockAfterTimeCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        LiteralArgumentBuilder<ServerCommandSource> literalargumentbuilder = CommandManager.literal("setblockaftertime").
                requires((serverCommandSource) -> serverCommandSource.hasPermissionLevel(2))
                //To Do: Add suggestion to this command
                .then(CommandManager.argument("pos", BlockPosArgumentType.blockPos())
                .then(CommandManager.argument("block", BlockStateArgumentType.blockState())
                .then(CommandManager.argument("time", IntegerArgumentType.integer())
                .executes((commandContext) -> AddSetblock(commandContext.getSource(), BlockPosArgumentType.getLoadedBlockPos(commandContext, "pos"), BlockStateArgumentType.getBlockState(commandContext, "block"), IntegerArgumentType.getInteger(commandContext,"time"))))));

        dispatcher.register(literalargumentbuilder);
    }

    private static int AddSetblock(ServerCommandSource source, BlockPos pos, BlockStateArgument block, int WaitTime) {
        MessageUtil.StringOut(source.getWorld(),MessageUtil.getLang("top.mtserver.command.SetBlockAfterTimeCommand.SetBlockAfter").replace("%block",block.getBlockState().getBlock().toString()).replace("%pos", ToString.BlockPosToString(pos)).replace("%time",String.valueOf(WaitTime)).replace("Block{","").replace("}",""),true);
        CommandDatas.SetBlockAfterTimeDatas.add(new CommandDatas.SetBlockAfterTimeData(source, pos, block, WaitTime));
        return 1;
    }
}
