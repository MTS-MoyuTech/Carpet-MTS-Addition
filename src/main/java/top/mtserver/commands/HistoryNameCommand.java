package top.mtserver.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import top.mtserver.MTSCarpetServer;
import top.mtserver.utils.NetWork.HistoryNameThread;

public class HistoryNameCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        LiteralArgumentBuilder<ServerCommandSource> builder = CommandManager.literal("historyname").
                requires((serverCommandSource) -> serverCommandSource.hasPermissionLevel(2))
                .then(CommandManager.argument("name", StringArgumentType.string())
                .executes((c) -> execute(c.getSource(),StringArgumentType.getString(c,"name"))));

        dispatcher.register(builder);
    }

    private static int execute(ServerCommandSource source, String name) {
        MTSCarpetServer.Log("Getting player %name's history name".replace("%name",name));
        Thread thread = new HistoryNameThread(source, name);
        thread.start();
        return 1;
    }
}
