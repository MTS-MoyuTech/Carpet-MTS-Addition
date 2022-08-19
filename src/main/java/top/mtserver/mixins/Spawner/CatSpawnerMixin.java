package top.mtserver.mixins.Spawner;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.GameRules;
import net.minecraft.world.SpawnHelper;
import net.minecraft.world.gen.CatSpawner;
import net.minecraft.world.gen.Spawner;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.poi.PointOfInterestStorage;
import net.minecraft.world.poi.PointOfInterestType;
import org.spongepowered.asm.mixin.Mixin;
import top.mtserver.MTSCarpetSettings;
import top.mtserver.utils.StringUtils.MessageUtil;
import top.mtserver.utils.StringUtils.ToString;
import top.mtserver.utils.World.CatType;
import top.mtserver.utils.World.MTSWorldLoop;

import java.util.List;
import java.util.Random;

@Mixin(CatSpawner.class)
public class CatSpawnerMixin implements Spawner {
    private int ticksUntilNextSpawn;

    @Override
    public int spawn(ServerWorld world, boolean spawnMonsters, boolean spawnAnimals) {
        /*
        这里是AddMSPT方法的实现的地方
        啥?你问我这么偏僻?
        这个地方是世界主循环的一部分,就加这里了
        毕竟我也不想多添加几个mixin

        ---v0.1.4的话
        由于这里太方便了
        我决定把整个Carpet MTS Addition的世界循环防这里
         */
        //World Loop On
        MTSWorldLoop.loop();
        //World Loop Off
        if (spawnAnimals && world.getGameRules().getBoolean(GameRules.DO_MOB_SPAWNING)) {
            --this.ticksUntilNextSpawn;
            if (this.ticksUntilNextSpawn <= 0) {
                boolean IsLooking = MTSCarpetSettings.CatSpawnerTracker;
                this.ticksUntilNextSpawn = MTSCarpetSettings.CatSpawnerInterval;
                MessageUtil.StringOut(world, ToString.SpawnerStartRunning(ToString.Cat) + ToString.ResetSpawnClock(this.ticksUntilNextSpawn), IsLooking);
                PlayerEntity playerEntity = world.getRandomAlivePlayer();
                if (playerEntity == null) {
                    MessageUtil.Out(world, ToString.NoPlayerToSpawn, IsLooking);
                } else {
                    Random random = world.random;
                    int i = (8 + random.nextInt(24)) * (random.nextBoolean() ? -1 : 1);
                    int j = (8 + random.nextInt(24)) * (random.nextBoolean() ? -1 : 1);
                    BlockPos blockPos = playerEntity.getBlockPos().add(i, 0, j);
                    MessageUtil.StringOut(world, ToString.SpawnWithPlayerCenterOffset(playerEntity, blockPos, i, 0, j), IsLooking);
                    if (!world.isRegionLoaded(blockPos.getX() - 10, blockPos.getY() - 10, blockPos.getZ() - 10, blockPos.getX() + 10, blockPos.getY() + 10, blockPos.getZ() + 10)) {
                        MessageUtil.Out(world, ToString.AreaNotLoadingGiveUpSpawn, IsLooking);
                    } else {
                        if (SpawnHelper.canSpawn(SpawnRestriction.Location.ON_GROUND, world, blockPos, EntityType.CAT)) {
                            if (world.isNearOccupiedPointOfInterest(blockPos, 2)) {
                                MessageUtil.Out(world, "top.mtserver.cat.tryToSpawnInVillage", IsLooking);//尝试生成在村庄
                                return this.spawnInHouse(world, blockPos);
                            } else {
                                MessageUtil.Out(world, "top.mtserver.cat.VillageCheckFailed", IsLooking);//村庄检测失败
                            }

                            if (world.getStructureAccessor().getStructureAt(blockPos, true, StructureFeature.SWAMP_HUT).hasChildren()) {
                                return this.spawnInSwampHut(world, blockPos);
                            } else {
                                MessageUtil.Out(world, "top.mtserver.cat.SwampCheckFailed", IsLooking);
                            }
                        } else {
                            MessageUtil.Out(world, "top.mtserver.cat.NoHVGiveUPSpawn", IsLooking);
                        }

                    }
                }
            }
        }
        return 0;
    }

    private int spawnInHouse(ServerWorld world, BlockPos pos) {

        boolean IsLooking = MTSCarpetSettings.CatSpawnerTracker;
        if (world.getPointOfInterestStorage().count(PointOfInterestType.HOME.getCompletionCondition(), pos, 48, PointOfInterestStorage.OccupationStatus.IS_OCCUPIED) > 4L) {
            List<CatEntity> list = world.getNonSpectatingEntities(CatEntity.class, (new Box(pos)).expand(48.0D, 8.0D, 48.0D));
            if (list.size() < 5) {
                MessageUtil.Out(world, "top.mtserver.cat.VillageSpawnSuccess", IsLooking);
                return this.spawn(pos, world);
            } else {
                MessageUtil.Out(world, "top.mtserver.cat.VillageSpawnFailedBecauseMax", IsLooking);
            }
        } else {
            MessageUtil.Out(world, "top.mtserver.cat.VillageSpawnFailedNotEnoughPoint", IsLooking);
        }

        return 0;
    }

    private int spawnInSwampHut(ServerWorld world, BlockPos pos) {
        boolean IsLooking = MTSCarpetSettings.CatSpawnerTracker;
        List<CatEntity> list = world.getNonSpectatingEntities(CatEntity.class, (new Box(pos)).expand(16.0D, 8.0D, 16.0D));
        int a = this.spawn(pos, world);
        if (a == 1) {
            MessageUtil.Out(world, "top.mtserver.cat.SwampSpawnSuccess", IsLooking);
        }
        return list.size() < 1 ? a : 0;
    }

    private int spawn(BlockPos pos, ServerWorld world) {
        CatEntity catEntity = EntityType.CAT.create(world);
        if (catEntity == null) {
            return 0;
        } else {
            catEntity.getCollarColor();
            boolean IsLooking = MTSCarpetSettings.CatSpawnerTracker;
            catEntity.initialize(world, world.getLocalDifficulty(pos), SpawnReason.NATURAL, null, null);
            catEntity.refreshPositionAndAngles(pos, 0.0F, 0.0F);
            world.spawnEntityAndPassengers(catEntity);

            MessageUtil.StringOut(world, MessageUtil.getLang("top.mtserver.cat.newCat").replace("%cat", CatType.getCatType(catEntity.getCatType())).replace("%block", ToString.BlockPosToString(pos)), IsLooking);
            //"有新" + CatType.getCatType(catEntity.getCatType()) + "在" + ToString.BlockPosToString(pos) + "生成!"
            return 1;
        }
    }
}
