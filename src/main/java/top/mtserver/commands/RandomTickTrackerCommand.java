package top.mtserver.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.block.BlockState;
import net.minecraft.command.argument.BlockPosArgumentType;
import net.minecraft.command.argument.BlockStateArgument;
import net.minecraft.command.argument.BlockStateArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.math.BlockPos;
import top.mtserver.utils.CommandDatas;
import top.mtserver.utils.StringUtils.MessageUtil;
import top.mtserver.utils.StringUtils.ToString;

import static top.mtserver.utils.CommandDatas.AreaRandomTickTrackerDatas;

public class RandomTickTrackerCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        LiteralArgumentBuilder<ServerCommandSource> builder = CommandManager.literal("randomticktracker")
                .requires((serverCommandSource) -> serverCommandSource.hasPermissionLevel(2))
                .then(CommandManager.literal("block")
                        .then(CommandManager.literal("add")
                                .then(CommandManager.argument("block", BlockStateArgumentType.blockState())
                                .executes((c) -> AddBlockRandomTickBlock(c, BlockStateArgumentType.getBlockState(c, "block")))))
                        .then(CommandManager.literal("remove")
                                .then(CommandManager.argument("block", BlockStateArgumentType.blockState())
                                .executes((c) -> RemoveBlockRandomTickBlock(c, BlockStateArgumentType.getBlockState(c, "block")))))
                        .then(CommandManager.literal("list")
                                .executes(RandomTickTrackerCommand::ListBlockRandomTickBlock)))
                .then(CommandManager.literal("area")
                        .then(CommandManager.literal("add")
                                .then(CommandManager.argument("Start Pos", BlockPosArgumentType.blockPos())
                                .then(CommandManager.argument("End Pos", BlockPosArgumentType.blockPos())
                                .executes((c) -> AddAreaRandomTickBlock(c, BlockPosArgumentType.getBlockPos(c, "Start Pos"), BlockPosArgumentType.getBlockPos(c, "End Pos"))))))
                        .then(CommandManager.literal("remove")
                                .then(CommandManager.argument("Number", IntegerArgumentType.integer()))
                                .executes((c) -> RemoveAreaRandomTickBlock(c, IntegerArgumentType.getInteger(c, "Number")))
                        )
                        .then(CommandManager.literal("list")
                                .executes(RandomTickTrackerCommand::ListAreaRandomTickBlock)));

        dispatcher.register(builder);
    }

    public static int AddBlockRandomTickBlock(CommandContext<ServerCommandSource> source, BlockStateArgument blockState) {
        if (CommandDatas.BlockRandomTickLog.contains(blockState.getBlockState())) {
            MessageUtil.Out(source.getSource().getWorld(), "top.mtserver.command.Log.BlockRandomTick.ExistNoChange", true);
        } else if (!blockState.getBlockState().hasRandomTicks()) {
            MessageUtil.Out(source.getSource().getWorld(), "top.mtserver.command.Log.BlockRandomTick.NotRandomTickable", true);
        } else {
            CommandDatas.BlockRandomTickLog.add(blockState.getBlockState());
            MessageUtil.StringOut(source.getSource().getWorld(), MessageUtil.getLang("top.mtserver.command.Log.BlockRandomTick.Changed").replace("%block", ToString.BlockToString(blockState.getBlockState().getBlock())), true);
        }
        return 1;
    }

    public static int RemoveBlockRandomTickBlock(CommandContext<ServerCommandSource> source, BlockStateArgument blockState) {
        if (CommandDatas.BlockRandomTickLog.contains(blockState.getBlockState())) {
            CommandDatas.BlockRandomTickLog.remove(blockState.getBlockState());
            MessageUtil.StringOut(source.getSource().getWorld(), MessageUtil.getLang("top.mtserver.command.Log.BlockRandomTick.Removed").replace("%block", ToString.BlockToString(blockState.getBlockState().getBlock())), true);
        } else {
            MessageUtil.StringOut(source.getSource().getWorld(), MessageUtil.getLang("top.mtserver.command.Log.BlockRandomTick.NoBlock").replace("%block", ToString.BlockToString(blockState.getBlockState().getBlock())), true);
        }
        return 1;
    }

    public static int ListBlockRandomTickBlock(CommandContext<ServerCommandSource> source) {
        MessageUtil.Out(source.getSource().getWorld(), "top.mtserver.command.Log.BlockRandomTick.BlockList", true);
        for (BlockState blockState : CommandDatas.BlockRandomTickLog) {
            MessageUtil.StringOut(source.getSource().getWorld(), ToString.BlockToString(blockState.getBlock()), true);
        }
        return 1;
    }

    public static int AddAreaRandomTickBlock(CommandContext<ServerCommandSource> source, BlockPos StartPos, BlockPos EndPos) {
        int x = (StartPos.getX() - EndPos.getX()) * (StartPos.getY() - EndPos.getY()) * (StartPos.getZ() - EndPos.getZ());
        //x:选区大小
        if (x <= 1000) {
            for (int i = 0; i <= 100; i++) {
                if (!AreaRandomTickTrackerDatas.containsKey(i)) {
                    MessageUtil.StringOut(source.getSource().getWorld(), MessageUtil.getLang("top.mtserver.command.Log.AreaRandomTick.add.Success").replace("%StartPos", ToString.BlockPosToString(StartPos)).replace("%EndPos", ToString.BlockPosToString(EndPos)).replace("%Id", String.valueOf(i)), true);
                    AreaRandomTickTrackerDatas.put(i, new CommandDatas.AreaRandomTickTrackerData(source.getSource(), StartPos, EndPos));
                    break;
                }
            }
        } else {
            MessageUtil.Out(source.getSource().getWorld(), "top.mtserver.command.Log.AreaRandomTick.Failed", true);
        }
        return 1;
    }

    public static int RemoveAreaRandomTickBlock(CommandContext<ServerCommandSource> source, int id) {
        if (AreaRandomTickTrackerDatas.containsKey(id)) {
            CommandDatas.AreaRandomTickTrackerData data = AreaRandomTickTrackerDatas.get(id);
            AreaRandomTickTrackerDatas.remove(id);
            BlockPos StartPos = data.RealStartPos;
            BlockPos EndPos = data.RealEndPos;
            MessageUtil.StringOut(source.getSource().getWorld(), MessageUtil.getLang("top.mtserver.command.Log.AreaRandomTick.remove.Success").replace("%StartPos", ToString.BlockPosToString(StartPos)).replace("%EndPos", ToString.BlockPosToString(EndPos)).replace("%Id", String.valueOf(id)), true);
        } else {
            MessageUtil.StringOut(source.getSource().getWorld(), MessageUtil.getLang("top.mtserver.command.Log.AreaRandomTick.remove.Failed").replace("%Id", String.valueOf(id)), true);
        }
        return 1;
    }

    public static int ListAreaRandomTickBlock(CommandContext<ServerCommandSource> source) {
        MessageUtil.Out(source.getSource().getWorld(), "top.mtserver.command.Log.AreaRandomTick.list", true);
        MessageUtil.StringOut(source.getSource().getWorld(), "Start Pos || End Pos || Id", true);
        for (int i: AreaRandomTickTrackerDatas.keySet()){
            CommandDatas.AreaRandomTickTrackerData data = AreaRandomTickTrackerDatas.get(i);
            MessageUtil.StringOut(source.getSource().getWorld(), ToString.BlockPosToString(data.RealStartPos) + " || " + ToString.BlockPosToString(data.RealEndPos) + " || " + i, true);
        }
        return 1;
    }
}