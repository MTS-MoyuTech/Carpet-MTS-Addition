package top.mtserver.mixins.Entity;

import net.minecraft.entity.projectile.FireworkRocketEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.mtserver.MTSCarpetServer;
import top.mtserver.MTSCarpetSettings;

import java.util.Random;

@Mixin(FireworkRocketEntity.class)
public abstract class FireworkRocketEntityMixin {
    @Shadow
    private int lifeTime;

    @Inject(method = "<init>(Lnet/minecraft/world/World;DDDLnet/minecraft/item/ItemStack;)V", at = @At("RETURN"))
    private void NoRandomLifetime(World world, double x, double y, double z, ItemStack stack, CallbackInfo ci) {
        Random random = new Random();
        int i = 1;

        if (!stack.isEmpty() && stack.hasTag()) {
            i += stack.getOrCreateSubTag("Fireworks").getByte("Flight");
        }

        if (MTSCarpetSettings.FireworkLifeTime == -1){
            this.lifeTime = 10 * i + random.nextInt(6) + random.nextInt(7);
        } else {
            this.lifeTime = MTSCarpetSettings.FireworkLifeTime * i + 6;
            MTSCarpetServer.Log("Firework at [%x %y %z] spawned with level %level and lifetime %lifetime".replace("%x", String.valueOf(x)).replace("%y", String.valueOf(y)).replace("%z", String.valueOf(z)).replace("%level", String.valueOf(i)).replace("%lifetime", String.valueOf(lifeTime + 1)));
        }
    }
}
