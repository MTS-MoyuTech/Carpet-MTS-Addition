package top.mtserver.mixins.Block;

import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
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

    @Override
    public void afterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack stack) {
        super.afterBreak(world, player, pos, state, blockEntity, stack);
        if (EnchantmentHelper.getLevel(Enchantments.SILK_TOUCH, stack) == 0) {
            if (world.getDimension().isUltrawarm()) {
                world.removeBlock(pos, false);
                return;
            }

            Material material = world.getBlockState(pos.down()).getMaterial();
            if ((material.blocksMovement() || material.isLiquid() || MTSCarpetSettings.IceMeltAlwaysWater)) {
                world.setBlockState(pos, Blocks.WATER.getDefaultState());
            }
        }
    }
}
