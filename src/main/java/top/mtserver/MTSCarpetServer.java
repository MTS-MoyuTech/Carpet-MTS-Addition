package top.mtserver;

import carpet.CarpetExtension;
import carpet.CarpetServer;
import carpet.utils.Translations;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.api.ModInitializer;
import net.minecraft.server.command.ServerCommandSource;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import top.mtserver.commands.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;

@SuppressWarnings("UnstableApiUsage")
public class MTSCarpetServer implements CarpetExtension, ModInitializer {
	public static final String fancyName = "Carpet MTS Addition";
	public static final Logger LOGGER = LogManager.getLogger(fancyName);

	public static void Log(String CrazyXinQi4Vme50){
		if (MTSCarpetSettings.MTSDebugMod){
			LOGGER.info(CrazyXinQi4Vme50);
		}
	}

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
		SpecialTickChunksCommand.register(dispatcher);
		SetBlockAfterTimeCommand.register(dispatcher);
		HistoryNameCommand.register(dispatcher);
		RandomTickTrackerCommand.register(dispatcher);
		GiveRandomTickCommand.register(dispatcher);
	}

	// 语言模块
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
		return gson.fromJson(dataJSON, new TypeToken<Map<String, String>>(){}.getType());
	}

	@Override
	public void onInitialize() {

	}
}
