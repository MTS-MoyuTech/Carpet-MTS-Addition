package top.mtserver.mixins.Block;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.IceBlock;
import net.minecraft.block.TransparentBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import top.mtserver.MTSCarpetSettings;

import java.util.Random;

@Mixin(IceBlock.class)
public class IceBlockMixin extends TransparentBlock {
    protected IceBlockMixin(Settings settings) {
        super(settings);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (world.getLightLevel(LightType.BLOCK, pos) > MTSCarpetSettings.IceMeltLightLevel - state.getOpacity(world, pos) && !MTSCarpetSettings.IceNeverMelt) {
            this.melt(state, world, pos);
        }
    }

    public void melt(BlockState state, World world, BlockPos pos) {
        if (world.getDimension().isUltrawarm() && MTSCarpetSettings.IceMeltAlwaysWater) {
            world.removeBlock(pos, false);
        } else {
            world.setBlockState(pos, Blocks.WATER.getDefaultState());
            world.updateNeighbor(pos, Blocks.WATER, pos);
        }
    }

}
