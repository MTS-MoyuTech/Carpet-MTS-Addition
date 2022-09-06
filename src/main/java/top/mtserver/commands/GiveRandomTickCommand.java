package top.mtserver.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.command.argument.BlockPosArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import top.mtserver.utils.World.BlockPosUtils;

public class GiveRandomTickCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        LiteralArgumentBuilder<ServerCommandSource> builder = CommandManager.literal("giverandomtick")
                .requires((serverCommandSource) -> serverCommandSource.hasPermissionLevel(2))
                .then(CommandManager.argument("pos1", BlockPosArgumentType.blockPos())
                .then(CommandManager.argument("pos2", BlockPosArgumentType.blockPos())
                        .executes((c) -> execute(c.getSource(), BlockPosArgumentType.getBlockPos(c, "pos1"), BlockPosArgumentType.getBlockPos(c, "pos2")))));
        dispatcher.register(builder);
    }

    public static int execute(ServerCommandSource source, BlockPos start, BlockPos end) {
        ServerWorld serverWorld = source.getWorld().toServerWorld();
        BlockPos[] NiqiuNB = BlockPosUtils.FormatBlockPosToHHHLLL(start, end);
        BlockPos RealStart = NiqiuNB[0];
        BlockPos RealEnd = NiqiuNB[1];
        for (int x = RealEnd.getX(); x <= RealStart.getX(); x++) {
            for (int y = RealEnd.getX(); y <= RealStart.getX(); y++) {
                for (int z = RealEnd.getX(); z <= RealStart.getX(); z++) {
                    BlockPos blockPos = new BlockPos(x, y, z);
                    BlockState blockState = serverWorld.getBlockState(blockPos);
                    if (blockState.hasRandomTicks()) {
                        blockState.randomTick(serverWorld, blockPos, serverWorld.random);
                    }
                }
            }
        }
        return 1;
    }
}
