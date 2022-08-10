package top.mtserver.mixins;

import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.chunk.WorldChunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import top.mtserver.MTSCarpetSettings;
import top.mtserver.utils.SpecialTickChunk;

@Mixin(ServerWorld.class)
public abstract class ServerWorldMixin{
    @Inject(
            method = "tick",
            at = @At(value = "HEAD")
    )
    public void tickChunk(WorldChunk chunk, int randomTickSpeed) {//Change randomTickSpeed
        System.out.println("That's OK      MTS Carpet Addition");
        if (MTSCarpetSettings.SpecialTickChunks && SpecialTickChunk.SpecialTickChunks.containsKey(chunk.getPos())){
            randomTickSpeed = SpecialTickChunk.SpecialTickChunks.get(chunk.getPos());
        }
    }
}
