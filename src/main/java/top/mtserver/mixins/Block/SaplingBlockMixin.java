package top.mtserver.mixins.Block;

import net.minecraft.block.*;

import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import top.mtserver.MTSCarpetSettings;

import java.util.Random;


@Mixin(SaplingBlock.class)
public abstract class SaplingBlockMixin extends PlantBlock implements Fertilizable {
    public static final IntProperty STAGE;
    public final  SaplingGenerator generate;
    private SaplingBlockMixin(SaplingGenerator generator, Settings settings) {
        super(settings);
        this.generate = generator;
    }

    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (world.getLightLevel(pos.up()) >= 9 && random.nextInt(100) >= 100 - MTSCarpetSettings.SaplingGrowProbability) {
            this.generate(world, pos, state, random);
        }
    }

    public void generate(ServerWorld world, BlockPos pos, BlockState state, Random random) {
        if ((Integer)state.get(STAGE) == 0) {
            world.setBlockState(pos, (BlockState)state.cycle(STAGE), 4);
        } else {
            this.generate.generate(world, world.getChunkManager().getChunkGenerator(), pos, state, random);
        }
    }

    static {
        STAGE = Properties.STAGE;
    }
}
