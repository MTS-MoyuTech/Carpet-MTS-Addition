package top.mtserver;

import carpet.CarpetExtension;
import carpet.CarpetServer;
import net.minecraft.server.MinecraftServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MTSCarpetServer implements CarpetExtension {
	public static final String fancyName = "Carpet NiQiu Addition";
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
		return "Carpet NiQiu Addition";
	}

}
