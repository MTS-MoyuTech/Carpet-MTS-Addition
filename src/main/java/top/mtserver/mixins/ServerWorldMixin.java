package top.mtserver.mixins;

import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.chunk.WorldChunk;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import top.mtserver.utils.SpecialTickChunk;

@Mixin(ServerWorld.class)
public abstract class ServerWorldMixin {
    //    @Inject(method = "tickChunk",
//            at = @At("HEAD"),
//            cancellable = true
//    )
//    @Inject(
//            method = "tickChunk",
//            at = @At(value = "HEAD"),
//            cancellable = true
//    )
    public void tickChunk(WorldChunk chunk, int randomTickSpeed, CallbackInfo callbackInfo) {//Change randomTickSpeed
        // System.out.println("That's OK      MTS Carpet Addition");
        randomTickSpeed = SpecialTickChunk.get(chunk);
    }
}
