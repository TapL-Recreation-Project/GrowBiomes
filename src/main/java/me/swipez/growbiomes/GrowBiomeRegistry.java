package me.swipez.growbiomes;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class GrowBiomeRegistry {

        private static GrowBiomeState MOOSHROOM_BIOME = new GrowBiomeState(GrowBiomes.getPlugin()) {
            @Override
            public void afterGeneration(PlayerInteractEvent event) {

            }
        }
                .addSquareMaterial(Material.BROWN_MUSHROOM)
                .addSquareMaterial(Material.RED_MUSHROOM)
                .addReplacementBlock(Material.GRASS_BLOCK, Material.MYCELIUM)
                .addReplacementBlock(Material.DIRT, Material.MYCELIUM)
                .setLeafReplacement(Material.RED_MUSHROOM_BLOCK)
                .setLogReplacement(Material.MUSHROOM_STEM)
                .addGrowthFeature(new GrowthFeature(1, 1, Material.MYCELIUM, 0.1, Material.BROWN_MUSHROOM, Material.RED_MUSHROOM));

        private static GrowBiomeState FLOWER_BIOME = new GrowBiomeState(GrowBiomes.getPlugin()) {
            @Override
            public void afterGeneration(PlayerInteractEvent event) {

            }
        }
                .addSquareMaterial(Material.DANDELION)
                .addGrowthFeature(new GrowthFeature(1, 1, Material.GRASS_BLOCK, 1, Material.DANDELION,
                    Material.CORNFLOWER, Material.POPPY, Material.BLUE_ORCHID, Material.ALLIUM, Material.AZURE_BLUET, Material.RED_TULIP,
                        Material.ORANGE_TULIP, Material.WHITE_TULIP, Material.PINK_TULIP, Material.OXEYE_DAISY, Material.CORNFLOWER, Material.LILY_OF_THE_VALLEY,
                            Material.WITHER_ROSE))
                .setWidth(200)
                .setHeight(20);

    private static GrowBiomeState DESERT_BIOME = new GrowBiomeState(GrowBiomes.getPlugin()) {
        @Override
        public void afterGeneration(PlayerInteractEvent event) {

        }
    }
            .addSquareMaterial(Material.SAND)
            .addReplacementBlock(Material.GRASS_BLOCK, Material.SAND)
            .addReplacementBlock(Material.DIRT, Material.SAND)
            .addGrowthFeature(new GrowthFeature(1, 1, Material.SAND, 0.05, Material.DEAD_BUSH))
            .addGrowthFeature(new GrowthFeature(3, 1, Material.SAND, 0.05, Material.CACTUS));

    private static GrowBiomeState MESA_BIOME = new GrowBiomeState(GrowBiomes.getPlugin()) {
        @Override
        public void afterGeneration(PlayerInteractEvent event) {

        }
    }
            .addSquareMaterial(Material.BRICKS)
            .addReplacementBlock(Material.GRASS_BLOCK, Material.RED_TERRACOTTA)
            .addReplacementBlock(Material.DIRT, Material.WHITE_TERRACOTTA)
            .addReplacementBlock(Material.STONE, Material.BLACK_TERRACOTTA)
            .setLeafReplacement(Material.YELLOW_TERRACOTTA)
            .setLogReplacement(Material.BROWN_TERRACOTTA)
            .addGrowthFeature(new GrowthFeature(1, 1, Material.RED_TERRACOTTA, 0.05, Material.DEAD_BUSH));

    private static GrowBiomeState NETHER_BIOME = new GrowBiomeState(GrowBiomes.getPlugin()) {
        @Override
        public void afterGeneration(PlayerInteractEvent event) {

        }
    }
            .addSquareMaterial(Material.REDSTONE_BLOCK)
            .addReplacementBlock(Material.GRASS_BLOCK, Material.CRIMSON_NYLIUM)
            .addReplacementBlock(Material.DIRT, Material.CRIMSON_NYLIUM)
            .setLogReplacement(Material.CRIMSON_STEM)
            .setLeafReplacement(Material.NETHER_WART_BLOCK)
            .addGrowthFeature(new GrowthFeature(1, 1, Material.CRIMSON_NYLIUM, 0.4, Material.FIRE))
            .addGrowthFeature(new GrowthFeature(1, 1, Material.CRIMSON_NYLIUM, 0.4, Material.CRIMSON_ROOTS))
            .addGrowthFeature(new GrowthFeature(0.1, EntityType.PIGLIN, Material.CRIMSON_NYLIUM))
            .addGrowthFeature(new GrowthFeature(0.05, EntityType.HOGLIN, Material.CRIMSON_NYLIUM));

    private static GrowBiomeState BASALT_BIOME = new GrowBiomeState(GrowBiomes.getPlugin()) {
        @Override
        public void afterGeneration(PlayerInteractEvent event) {

        }
    }
            .addSquareMaterial(Material.DEEPSLATE)
            .addReplacementBlock(Material.GRASS_BLOCK, Material.BASALT)
            .addReplacementBlock(Material.DIRT, Material.BASALT)
            .addGrowthFeature(new GrowthFeature(1, 1, Material.BASALT, 0.3, true, Material.MAGMA_BLOCK, Material.GRAVEL, Material.LAVA))
            .addGrowthFeature(new GrowthFeature(4, 1, Material.BASALT, 0.7, true, Material.BASALT))
            .addGrowthFeature(new GrowthFeature(0.01, EntityType.MAGMA_CUBE, Material.BASALT));

    private static GrowBiomeState OCEAN_BIOME = new GrowBiomeState(GrowBiomes.getPlugin()) {
        @Override
        public void afterGeneration(PlayerInteractEvent event) {
            Chunk chunk = event.getClickedBlock().getChunk();
            int yAddition = event.getClickedBlock().getY();
            int loopCounts = 0;
            for (int x = 0; x < 16; x++){
                for (int z = 0; z < 16; z++){
                    for (int y = 255; y > 0; y--){
                        loopCounts++;
                        int tempLoopCount = loopCounts;
                        int tempX = x;
                        int tempY = y;
                        int tempZ = z;
                        BukkitTask task = new BukkitRunnable() {
                            @Override
                            public void run() {
                                Block block = chunk.getBlock(tempX,tempY,tempZ);
                                if (block.getY() <= yAddition){
                                    block.setType(Material.WATER, false);
                                }
                            }
                        }.runTaskLater(GrowBiomes.getPlugin(), (long) (0.0005*loopCounts));
                    }
                }
            }
        }
    }
            .addSquareMaterial(Material.WATER_CAULDRON);

    private static GrowBiomeState JUNGLE_BIOME = new GrowBiomeState(GrowBiomes.getPlugin()) {
        @Override
        public void afterGeneration(PlayerInteractEvent event) {

        }
    }
            .addSquareMaterial(Material.OAK_LEAVES)
            .addSquareMaterial(Material.ACACIA_LEAVES)
            .addSquareMaterial(Material.JUNGLE_LEAVES)
            .addSquareMaterial(Material.AZALEA_LEAVES)
            .addSquareMaterial(Material.BIRCH_LEAVES)
            .addSquareMaterial(Material.DARK_OAK_LEAVES)
            .addSquareMaterial(Material.SPRUCE_LEAVES)
            .setLeafReplacement(Material.JUNGLE_LEAVES)
            .setLogReplacement(Material.JUNGLE_LOG)
            .setWidth(200)
            .setHeight(20)
            .addGrowthFeature(new GrowthFeature(1, 1, Material.GRASS_BLOCK, 0.2, Material.MELON))
            .addGrowthFeature(new GrowthFeature(1, 1, Material.GRASS_BLOCK, 0.7,  Material.FERN));

    private static GrowBiomeState SAVANNAH_BIOME = new GrowBiomeState(GrowBiomes.getPlugin()) {
        @Override
        public void afterGeneration(PlayerInteractEvent event) {
            Chunk chunk = event.getClickedBlock().getChunk();
            int yAddition = event.getClickedBlock().getY()-3;
            int loopCounts = 0;
            for (int x = 0; x < 16; x++){
                for (int z = 0; z < 16; z++){
                    for (int y = 255; y > 0; y--){
                        loopCounts++;
                        int tempLoopCount = loopCounts;
                        int tempX = x;
                        int tempY = y;
                        int tempZ = z;
                        BukkitTask task = new BukkitRunnable() {
                            @Override
                            public void run() {
                                Block block = chunk.getBlock(tempX,tempY,tempZ);
                                if (!block.getType().isAir()){
                                    block.getWorld().getBlockAt(block.getLocation().add(0,yAddition,0)).setType(block.getType());
                                    block.setType(Material.AIR);
                                }
                                if (tempLoopCount == 255*16*16){
                                    event.getPlayer().teleport(event.getPlayer().getLocation().add(0,yAddition,0));
                                }
                            }
                        }.runTaskLater(GrowBiomes.getPlugin(), (long) (0.0005*loopCounts));
                    }
                }
            }
        }
    }
            .addSquareMaterial(Material.STONE)
            .addReplacementBlock(Material.GRASS_BLOCK, Material.COARSE_DIRT)
            .setLeafReplacement(Material.ACACIA_LEAVES)
            .setLogReplacement(Material.ACACIA_LOG)
            .addGrowthFeature(new GrowthFeature(1, 1, Material.COARSE_DIRT, 0.3, Material.GRASS))
            .addGrowthFeature(new GrowthFeature(1, 1, Material.COARSE_DIRT, 0.005, Material.RED_TULIP));

        public static void initBiomes(){
            OCEAN_BIOME.register();
            MESA_BIOME.register();
            JUNGLE_BIOME.register();
            BASALT_BIOME.register();
            NETHER_BIOME.register();
            SAVANNAH_BIOME.register();
            MOOSHROOM_BIOME.register();
            FLOWER_BIOME.register();
            DESERT_BIOME.register();
        }

}
