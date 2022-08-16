package top.mtserver;

import carpet.CarpetExtension;
import carpet.CarpetServer;
import carpet.utils.Translations;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ServerCommandSource;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import top.mtserver.commands.SpecialTickChunks.SpecialTickChunksCommand;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;

@SuppressWarnings("UnstableApiUsage")
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

	@Override
	public Map<String, String> canHasTranslations(String lang) {
		String dataJSON;
		try {
			dataJSON = IOUtils.toString(
					Objects.requireNonNull(Translations.class.getClassLoader().getResourceAsStream(
							String.format("assets/carpet-mts-addition/lang/%s.json", lang))),
					StandardCharsets.UTF_8);
		} catch (NullPointerException | IOException e) {
			return null;
		}
		Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
		return gson.fromJson(dataJSON, new TypeToken<Map<String, String>>() {
		}.getType());
	}
}
