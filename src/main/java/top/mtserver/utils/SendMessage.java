package top.mtserver.utils;

import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.BaseText;
import net.minecraft.text.LiteralText;

import java.util.List;

public class SendMessage {
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

    public static void Out(ServerWorld world, String thing, boolean IsLooking) {
        List<ServerPlayerEntity> playerEntities = world.getPlayers();
        for (ServerPlayerEntity serverPlayer : playerEntities) {
            __tell(serverPlayer.getCommandSource(), s(thing), IsLooking);
        }
    }
}
