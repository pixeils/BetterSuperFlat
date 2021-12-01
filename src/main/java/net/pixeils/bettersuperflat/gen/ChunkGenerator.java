package net.pixeils.bettersuperflat.gen;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Supplier;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.ChunkRegion;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.biome.source.BiomeAccess;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.WorldChunk;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGeneratorSettings;
import net.minecraft.world.gen.chunk.NoiseChunkGenerator;

public class ChunkGenerator extends NoiseChunkGenerator {

    private final long seed;

    public static final Codec<ChunkGenerator> CODEC =
            RecordCodecBuilder.create(
                    (instance) ->
                            instance
                                    .group(
                                            BiomeSource.CODEC
                                                    .fieldOf("biome_source")
                                                    .forGetter(ChunkGenerator::getBiomeSource),
                                            Codec.LONG
                                                    .fieldOf("seed")
                                                    .stable()
                                                    .forGetter(ChunkGenerator::getSeed),
                                            ChunkGeneratorSettings.REGISTRY_CODEC
                                                    .fieldOf("settings")
                                                    .forGetter(ChunkGenerator::getSettings))
                                    .apply(instance, instance.stable(ChunkGenerator::new)));

    public ChunkGenerator(
            BiomeSource biomeSource, long seed, Supplier<ChunkGeneratorSettings> settings) {

        super(biomeSource, seed, settings);
        this.seed = seed;
    }


    public long getSeed() {
        return this.seed;
    }

    public Supplier<ChunkGeneratorSettings> getSettings() {
        return this.settings;
    }

    @Override
    protected Codec<? extends net.minecraft.world.gen.chunk.ChunkGenerator> getCodec() {
        return CODEC;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public net.minecraft.world.gen.chunk.ChunkGenerator withSeed(long seed) {
        return new ChunkGenerator(this.biomeSource.withSeed(seed), seed, this.settings);
    }

    @Override
    public void buildSurface(ChunkRegion region, Chunk chunk) {
        Arrays.fill(chunk.getSectionArray(), WorldChunk.EMPTY_SECTION);

            BlockPos pos =
                    new BlockPos(
                    region.getCenterPos().getStartX(),
                    region.getHeight() - 256,
                    region.getCenterPos().getStartZ());
            generateChunkFloor(
                    region,
                    pos,
                    new BlockBox(
                            chunk.getPos().getStartX(),
                            0,
                            chunk.getPos().getStartZ(),
                            chunk.getPos().getStartX() + 15,
                            region.getHeight(),
                            chunk.getPos().getStartZ() + 15));
            }



    @Override
    public CompletableFuture<Chunk> populateNoise(
            Executor executor, StructureAccessor accessor, Chunk chunk) {
        return CompletableFuture.completedFuture(chunk);
    }

    @Override
    public void carve(long seed, BiomeAccess access, Chunk chunk, GenerationStep.Carver carver) {
    }

    @Override
    public void populateEntities(ChunkRegion region) {
    }


    @Override
    public void generateFeatures(ChunkRegion region, StructureAccessor accessor) {
        ChunkPos chunkPos = region.getCenterPos();
        BlockPos pos = new BlockPos(chunkPos.getStartX(), region.getBottomY(), chunkPos.getStartZ());
        int startX = chunkPos.getStartX();
        int startZ = chunkPos.getStartZ();
        BlockBox box = new BlockBox(startX, 0, startZ, startX + 15, region.getHeight(), startZ + 15);
    }

    protected static void placeRelativeBlockInBox(
            WorldAccess world,
            BlockState block,
            BlockPos referencePos,
            int x,
            int y,
            int z,
            BlockBox box) {
        BlockPos blockPos =
                new BlockPos(referencePos.getX() + x, referencePos.getY() + y, referencePos.getZ() + z);
        if (box.contains(blockPos)) {
            world.setBlockState(blockPos, block, 2);
        }
    }

    protected static void fillRelativeBlockInBox(
            WorldAccess world,
            BlockState block,
            BlockPos referencePos,
            int startX,
            int startY,
            int startZ,
            int endX,
            int endY,
            int endZ,
            BlockBox box) {
        for (int x = startX; x <= endX; x++) {
            for (int y = startY; y <= endY; y++) {
                for (int z = startZ; z <= endZ; z++) {
                    placeRelativeBlockInBox(world, block, referencePos, x, y, z, box);
                }
            }
        }
    }

    protected static void generateChunkFloor(ServerWorldAccess world, BlockPos pos, BlockBox box) {
        fillRelativeBlockInBox(
                world, Blocks.WHITE_CONCRETE.getDefaultState(), pos, 1,0,1,14,0,14,box);
        fillRelativeBlockInBox(
                world, Blocks.WHITE_WOOL.getDefaultState(), pos, 1,0,0,15,0,0,box);
        fillRelativeBlockInBox(
                world, Blocks.WHITE_WOOL.getDefaultState(), pos, 0,0,0,0,0,15,box);
        fillRelativeBlockInBox(
                world, Blocks.WHITE_WOOL.getDefaultState(), pos, 15,0,1,15,0,15,box);
        fillRelativeBlockInBox(
                world, Blocks.WHITE_WOOL.getDefaultState(), pos, 1,0,15,14,0,15,box);
        fillRelativeBlockInBox(
                world, Blocks.BARRIER.getDefaultState(), pos, 0,1,0,15,1,15,box);

    }
}
