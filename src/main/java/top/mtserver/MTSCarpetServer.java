package top.mtserver;

import carpet.CarpetExtension;
import carpet.CarpetServer;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ServerCommandSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import top.mtserver.commands.SpecialTickChunks.SpecialTickChunksCommand;

public class MTSCarpetServer implements CarpetExtension {
	public static final String fancyName = "Carpet MTS Addition";
	public static final Logger LOGGER = LogManager.getLogger(fancyName);
	public static MinecraftServer minecraft_server;

	public static void noop() { }

	static {
		CarpetServer.manageExtension(new MTSCarpetServer());
	}

	@Override
	public void onGameStarted() {
		CarpetServer.settingsManager.parseSettingsClass(MTSCarpetSettings.class);
	}

	@Override
	public String version() {
		return "Carpet MTS Addition";
	}

	@Override
	public void registerCommands(CommandDispatcher<ServerCommandSource> dispatcher){
		//#else
		//$$ public void registerCommands(CommandDispatcher<CommandSourceStack> dispatcher) {
		//#endif
		SpecialTickChunksCommand.register(dispatcher);
	}
}
