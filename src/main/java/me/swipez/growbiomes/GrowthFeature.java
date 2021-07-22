package me.swipez.growbiomes;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.EntityType;

import java.util.Random;

public class GrowthFeature {

    Random random = new Random();

    private final int maxSize;
    private final int minSize;
    private final Material requirementBlock;
    private final double chance;
    private final Material[] materials;
    private boolean isBlockType = true;
    private EntityType spawnEntity;
    private boolean spawnDirectly = false;

    public GrowthFeature(int maxSize, int minSize, Material requirementBlock, double chance, Material... materials) {
        this.maxSize = maxSize;
        this.minSize = minSize;
        this.requirementBlock = requirementBlock;
        this.chance = chance;
        this.materials = materials;
    }

    public GrowthFeature(int maxSize, int minSize, Material requirementBlock, double chance, boolean spawnDirectly, Material... materials) {
        this.maxSize = maxSize;
        this.minSize = minSize;
        this.requirementBlock = requirementBlock;
        this.chance = chance;
        this.materials = materials;
        this.spawnDirectly = spawnDirectly;
    }

    public GrowthFeature(double chance, EntityType mobType, Material requirementBlock){
        this.maxSize = 0;
        this.minSize = 0;
        this.requirementBlock = requirementBlock;
        materials = null;
        this.chance = chance;
        isBlockType = false;

        spawnEntity = mobType;
    }

    public GrowthFeature(double chance, EntityType mobType, Material requirementBlock, boolean spawnDirectly){
        this.maxSize = 0;
        this.minSize = 0;
        this.requirementBlock = requirementBlock;
        materials = null;
        this.chance = chance;
        isBlockType = false;
        this.spawnDirectly = spawnDirectly;

        spawnEntity = mobType;
    }

    public boolean isBlockType(){
        return isBlockType;
    }

    public boolean isMobType(){
        return !isBlockType;
    }

    public boolean attemptGenerateEntity(Block origin){
        double randomChance = random.nextDouble();
        if (chance > randomChance) {
            if (origin.getRelative(BlockFace.UP).getType().isAir()) {
                if (origin.getType().equals(requirementBlock)){
                    origin.getWorld().spawnEntity(origin.getLocation().add(0,1,0), spawnEntity);
                }
            }
        }
        return false;
    }

    public boolean attemptGenerateBlock(Block origin){
        double randomChance = random.nextDouble();
        if (chance > randomChance) {
            if (!spawnDirectly){
                if (origin.getRelative(BlockFace.UP).getType().isAir()) {
                    if (origin.getType().equals(requirementBlock)) {
                        int sizeOfBlock = 1;
                        if (maxSize != minSize){
                            sizeOfBlock = random.nextInt(maxSize - minSize) + minSize;
                        }
                        Material material = materials[random.nextInt(materials.length)];
                        for (int i = 0; i < sizeOfBlock; i++) {
                            Block block = origin.getWorld().getBlockAt(origin.getLocation().add(0, i + 1, 0));
                            block.setType(material, true);
                        }
                        return true;
                    }
                }
            }
            else {
                if (origin.getType().equals(requirementBlock)) {
                    int sizeOfBlock = 1;
                    if (maxSize != minSize){
                        sizeOfBlock = random.nextInt(maxSize - minSize) + minSize;
                    }
                    Material material = materials[random.nextInt(materials.length)];
                    for (int i = 0; i < sizeOfBlock; i++) {
                        Block block = origin.getWorld().getBlockAt(origin.getLocation().add(0, i, 0));
                        block.setType(material, true);
                    }
                    return true;
                }
            }
        }
        return false;
    }
}
