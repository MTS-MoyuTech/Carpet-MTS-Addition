package top.mtserver.mixins;

import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.chunk.WorldChunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerWorld.class)
public abstract class ServerWorldMixin {
    @Shadow
    public abstract void tickChunk(WorldChunk chunk, int randomTickSpeed);

    @Inject(method = "tickChunk", at = @At("HEAD"), cancellable = true)
    void ChangeRTS(WorldChunk chunk, int randomTickSpeed, CallbackInfo ci) {//Change randomTickSpeed

    }
}
