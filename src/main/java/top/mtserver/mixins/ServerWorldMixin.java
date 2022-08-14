package top.mtserver.mixins;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.mob.SkeletonHorseEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.WorldGenerationProgressListener;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.GameRules;
import net.minecraft.world.Heightmap;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkSection;
import net.minecraft.world.chunk.WorldChunk;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.Spawner;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.level.ServerWorldProperties;
import net.minecraft.world.level.storage.LevelStorage;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Mixin;
import top.mtserver.utils.SpecialTickChunk;

import java.util.List;
import java.util.concurrent.Executor;

@Mixin(ServerWorld.class)
public class ServerWorldMixin extends ServerWorld{
    public ServerWorldMixin(MinecraftServer server, Executor workerExecutor, LevelStorage.Session session, ServerWorldProperties properties, RegistryKey<World> worldKey, DimensionType dimensionType, WorldGenerationProgressListener worldGenerationProgressListener, ChunkGenerator chunkGenerator, boolean debugWorld, long seed, List<Spawner> spawners, boolean shouldTickTime) {
        super(server, workerExecutor, session, properties, worldKey, dimensionType, worldGenerationProgressListener, chunkGenerator, debugWorld, seed, spawners, shouldTickTime);
    }

    /*
    因为不知道怎么写而摆烂的屑->
     */
    @Override
    public void tickChunk(WorldChunk chunk, int randomTickSpeed) {//Change randomTickSpeed
        System.out.println("That's OK      MTS Carpet Addition");
        randomTickSpeed = SpecialTickChunk.get(chunk);
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
                boolean bl2 = this.getGameRules().getBoolean(GameRules.DO_MOB_SPAWNING) && this.random.nextDouble() < (double)localDifficulty.getLocalDifficulty() * 0.01D;
                if (bl2) {
                    SkeletonHorseEntity skeletonHorseEntity = (SkeletonHorseEntity) EntityType.SKELETON_HORSE.create(this);
                    skeletonHorseEntity.setTrapped(true);
                    skeletonHorseEntity.setBreedingAge(0);
                    skeletonHorseEntity.setPosition((double)blockPos.getX(), (double)blockPos.getY(), (double)blockPos.getZ());
                    this.spawnEntity(skeletonHorseEntity);
                }

                LightningEntity lightningEntity = (LightningEntity)EntityType.LIGHTNING_BOLT.create(this);
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
            int var19 = var17.length;

            for(int var21 = 0; var21 < var19; ++var21) {
                ChunkSection chunkSection = var17[var21];
                if (chunkSection != WorldChunk.EMPTY_SECTION && chunkSection.hasRandomTicks()) {
                    int k = chunkSection.getYOffset();

                    for(int l = 0; l < randomTickSpeed; ++l) {
                        BlockPos blockPos3 = this.getRandomPosInChunk(i, k, j, 15);
                        profiler.push("randomTick");
                        BlockState blockState = chunkSection.getBlockState(blockPos3.getX() - i, blockPos3.getY() - k, blockPos3.getZ() - j);
                        if (blockState.hasRandomTicks()) {
                            blockState.randomTick(this, blockPos3, this.random);
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
}
