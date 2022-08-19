package top.mtserver.mixins;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.SkeletonHorseEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.*;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.*;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkSection;
import net.minecraft.world.chunk.WorldChunk;
import net.minecraft.world.dimension.DimensionType;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.mtserver.utils.CommandDatas;

import java.util.List;
import java.util.function.Supplier;

@Mixin(ServerWorld.class)
public abstract class ServerWorldMixin extends World implements StructureWorldAccess {

    public ServerWorldMixin(MutableWorldProperties properties, RegistryKey<World> registryRef, DimensionType dimensionType, Supplier<Profiler> profiler, boolean isClient, boolean debugWorld, long seed) {
        super(properties, registryRef, dimensionType, profiler, isClient, debugWorld, seed);
    }

    @Inject(method = "tickChunk", //要注入的方法sendChatMessage
            at = @At("HEAD"), //表明插入的位置在方法的头部
            cancellable = true //表明我们可以中途取消(return)这个方法
    )
    public void tickChunk(WorldChunk chunk, int randomTickSpeed, CallbackInfo ci) {//Change randomTickSpeed
        randomTickSpeed = CommandDatas.getSpecialTickChunks(chunk);
        ChunkPos chunkPos = chunk.getPos();
        boolean bl = this.isRaining();
        int i = chunkPos.getStartX();
        int j = chunkPos.getStartZ();
        Profiler profiler = this.getProfiler();
        profiler.push("thunder");
        BlockPos blockPos;
        if (bl && this.isThundering() && this.random.nextInt(100000) == 0) {
            blockPos = this.getSurface(this.getRandomPosInChunk(i, 0, j, 15));
            if (this.hasRain(blockPos)) {
                LocalDifficulty localDifficulty = this.getLocalDifficulty(blockPos);
                boolean bl2 = this.getGameRules().getBoolean(GameRules.DO_MOB_SPAWNING) && this.random.nextDouble() < (double) localDifficulty.getLocalDifficulty() * 0.01D;
                if (bl2) {
                    SkeletonHorseEntity skeletonHorseEntity = EntityType.SKELETON_HORSE.create(this);
                    skeletonHorseEntity.setTrapped(true);
                    skeletonHorseEntity.setBreedingAge(0);
                    skeletonHorseEntity.setPosition(blockPos.getX(), blockPos.getY(), blockPos.getZ());
                    this.spawnEntity(skeletonHorseEntity);
                }

                LightningEntity lightningEntity = EntityType.LIGHTNING_BOLT.create(this);
                lightningEntity.refreshPositionAfterTeleport(Vec3d.ofBottomCenter(blockPos));
                lightningEntity.setCosmetic(bl2);
                this.spawnEntity(lightningEntity);
            }
        }

        profiler.swap("iceandsnow");
        if (this.random.nextInt(16) == 0) {
            blockPos = this.getTopPosition(Heightmap.Type.MOTION_BLOCKING, this.getRandomPosInChunk(i, 0, j, 15));
            BlockPos blockPos2 = blockPos.down();
            Biome biome = this.getBiome(blockPos);
            if (biome.canSetIce(this, blockPos2)) {
                this.setBlockState(blockPos2, Blocks.ICE.getDefaultState());
            }

            if (bl && biome.canSetSnow(this, blockPos)) {
                this.setBlockState(blockPos, Blocks.SNOW.getDefaultState());
            }

            if (bl && this.getBiome(blockPos2).getPrecipitation() == Biome.Precipitation.RAIN) {
                this.getBlockState(blockPos2).getBlock().rainTick(this, blockPos2);
            }
        }

        profiler.swap("tickBlocks");
        if (randomTickSpeed > 0) {
            ChunkSection[] var17 = chunk.getSectionArray();

            for (ChunkSection chunkSection : var17) {
                if (chunkSection != WorldChunk.EMPTY_SECTION && chunkSection.hasRandomTicks()) {
                    int k = chunkSection.getYOffset();

                    for (int l = 0; l < randomTickSpeed; ++l) {
                        BlockPos blockPos3 = this.getRandomPosInChunk(i, k, j, 15);
                        profiler.push("randomTick");
                        BlockState blockState = chunkSection.getBlockState(blockPos3.getX() - i, blockPos3.getY() - k, blockPos3.getZ() - j);
                        if (blockState.hasRandomTicks()) {
                            blockState.randomTick(this.toServerWorld(), blockPos3, this.random);
                        }

                        FluidState fluidState = blockState.getFluidState();
                        if (fluidState.hasRandomTicks()) {
                            fluidState.onRandomTick(this, blockPos3, this.random);
                        }

                        profiler.pop();
                    }
                }
            }
        }

        profiler.pop();
    }

    public BlockPos getSurface(BlockPos pos) {
        BlockPos blockPos = this.getTopPosition(Heightmap.Type.MOTION_BLOCKING, pos);
        Box box = (new Box(blockPos, new BlockPos(blockPos.getX(), this.getHeight(), blockPos.getZ()))).expand(3.0D);
        List<LivingEntity> list = this.getEntitiesByClass(LivingEntity.class, box, (entity) -> entity != null && entity.isAlive() && this.isSkyVisible(entity.getBlockPos()));
        if (!list.isEmpty()) {
            return list.get(this.random.nextInt(list.size())).getBlockPos();
        } else {
            if (blockPos.getY() == -1) {
                blockPos = blockPos.up(2);
            }

            return blockPos;
        }
    }
}