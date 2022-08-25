package top.mtserver.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import top.mtserver.utils.NetWork.NetWorkThread;

public class HistoryNameCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        LiteralArgumentBuilder<ServerCommandSource> literalargumentbuilder = CommandManager.literal("historyname").
                requires((serverCommandSource) -> serverCommandSource.hasPermissionLevel(2))
                //To Do: Add suggestion to this command
                .then(CommandManager.argument("name", StringArgumentType.string())
                .executes((c) -> execute(c.getSource(),StringArgumentType.getString(c,"name"))));

        dispatcher.register(literalargumentbuilder);
    }

    private static int execute(ServerCommandSource source, String name) {
        Thread thread = new NetWorkThread(source,name);
        thread.start();
        return 1;
    }
}
