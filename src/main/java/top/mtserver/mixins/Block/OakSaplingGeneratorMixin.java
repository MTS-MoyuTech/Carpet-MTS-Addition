package top.mtserver.mixins.Block;

import net.minecraft.block.sapling.OakSaplingGenerator;
import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.ConfiguredFeatures;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import top.mtserver.MTSCarpetSettings;

import java.util.Random;

@Mixin(OakSaplingGenerator.class)
public class OakSaplingGeneratorMixin extends SaplingGenerator {
    public OakSaplingGeneratorMixin(){
    }

    @Override
    @Nullable
    public ConfiguredFeature<TreeFeatureConfig, ?> createTreeFeature(Random random, boolean bees) {
        if (random.nextInt(100) > 100 - MTSCarpetSettings.FancyOakTreeProbability) {
            return bees ? ConfiguredFeatures.FANCY_OAK_BEES_005 : ConfiguredFeatures.FANCY_OAK;
        } else {
            return bees ? ConfiguredFeatures.OAK_BEES_005 : ConfiguredFeatures.OAK;
        }
    }
}
