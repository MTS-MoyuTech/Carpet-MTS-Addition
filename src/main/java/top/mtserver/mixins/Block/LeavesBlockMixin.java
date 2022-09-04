package top.mtserver.mixins.Block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import top.mtserver.MTSCarpetSettings;

import java.util.Random;

@Mixin(LeavesBlock.class)
public class LeavesBlockMixin extends Block {
    private static final IntProperty DISTANCE;
    private static final BooleanProperty PERSISTENT;
    public LeavesBlockMixin(Settings settings) {
        super(settings);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if ((!(Boolean)state.get(PERSISTENT) && state.get(DISTANCE) == MTSCarpetSettings.LeavesDisappearLogDistance) && !MTSCarpetSettings.LeavesNeverDisappear) {
            dropStacks(state, world, pos);
            world.removeBlock(pos, false);
        }
    }
    static {
        DISTANCE = Properties.DISTANCE_1_7;
        PERSISTENT = Properties.PERSISTENT;
    }
}
