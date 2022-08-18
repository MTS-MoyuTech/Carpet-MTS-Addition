package top.mtserver.utils.StringUtils;

import carpet.CarpetSettings;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.BaseText;
import net.minecraft.text.LiteralText;
import top.mtserver.MTSCarpetServer;

import java.util.List;
import java.util.Map;

public class MessageUtil {
    public static BaseText s(Object text) {
        return
                //#if MC >= 11900
                //$$ Text.literal
                //#else
                new LiteralText
                        //#endif
                        (text.toString());
    }

    private static void __tell(ServerCommandSource source, BaseText text, boolean IsLooking) {
        // translation logic is handled in carpettisaddition.mixins.translations.ServerPlayerEntityMixin
        if(IsLooking){
            source.sendFeedback(text, false);
        }
    }

    public static void Out(ServerWorld world, String key, boolean IsLooking) {
        /*
        v0.1.0 lang update
        v0.0.5-:thing is the message
        v0.1.0+:the message will be read from %lang%.json
         */
        if(IsLooking) {
            List<ServerPlayerEntity> playerEntities = world.getPlayers();
            for (ServerPlayerEntity serverPlayer : playerEntities) {
                __tell(serverPlayer.getCommandSource(), s(getLang(key)), IsLooking);
            }
        }
    }

    public static void StringOut(ServerWorld world, String thing, boolean IsLooking) {
        if(IsLooking) {
            List<ServerPlayerEntity> playerEntities = world.getPlayers();
            for (ServerPlayerEntity serverPlayer : playerEntities) {
                __tell(serverPlayer.getCommandSource(), s(thing), IsLooking);
            }
        }
    }

    public static String getLang(String key){
        if (new MTSCarpetServer().canHasTranslations(CarpetSettings.language).containsKey(key)){
            MTSCarpetServer mtsCarpetServer = new MTSCarpetServer();
            Map<String,String> Vme50 = mtsCarpetServer.canHasTranslations(CarpetSettings.language);
            if (Vme50 == null){
                return "";
            } else {
                return mtsCarpetServer.canHasTranslations(CarpetSettings.language).get(key);
            }
        }
        MTSCarpetServer.LOGGER.error("key: " + key + " doesn't exist in " + CarpetSettings.language + ".json");
        return "";
    }
}
