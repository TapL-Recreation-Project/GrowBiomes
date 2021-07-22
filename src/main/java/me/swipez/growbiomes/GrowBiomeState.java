package me.swipez.growbiomes;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public abstract class GrowBiomeState implements Listener {

    Random random = new Random();

    private final HashMap<Material, Material> blockReplacements = new HashMap<>();
    private final JavaPlugin plugin;
    private List<GrowthFeature> allGrowthFeatures = new ArrayList<>();
    private List<Material> squareMaterials = new ArrayList<>();
    private Material leafReplacement = null;
    private Material logReplacement = null;

    private int width = 30;
    private int height = 30;

    public GrowBiomeState(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void register(){
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public GrowBiomeState setWidth(int width){
        this.width = width;
        return this;
    }

    public GrowBiomeState setHeight(int height){
        this.height = height;
        return this;
    }

    public GrowBiomeState setLeafReplacement(Material replacement){
        leafReplacement = replacement;
        return this;
    }

    public GrowBiomeState setLogReplacement(Material replacement){
        logReplacement = replacement;
        return this;
    }

    public GrowBiomeState addReplacementBlock(Material materialToReplace, Material replacement){
        blockReplacements.put(materialToReplace, replacement);
        return this;
    }

    public GrowBiomeState addSquareMaterial(Material squareMaterial){
        squareMaterials.add(squareMaterial);
        return this;
    }

    public GrowBiomeState addGrowthFeature(GrowthFeature growthFeature){
        allGrowthFeatures.add(growthFeature);
        return this;
    }

    private boolean hasGrowthFeatures(){
        return !allGrowthFeatures.isEmpty();
    }

    private GrowthFeature getRandomFeature(){
        return allGrowthFeatures.get(random.nextInt(allGrowthFeatures.size()));
    }


    @EventHandler
    public void onPlayerInteractsWithBlock(PlayerInteractEvent event){
        if (!event.hasBlock()){
            return;
        }
        if (!event.hasItem()){
            return;
        }
        ItemStack itemStack = event.getItem();
        if (!itemStack.getType().equals(Material.BONE_MEAL)){
            return;
        }
        if (!event.getAction().toString().toLowerCase().contains("right")){
            return;
        }
        Material blockType = event.getClickedBlock().getType();
        if (squareMaterials.contains(blockType)){
            if (checkForSquare(event.getClickedBlock())){
                event.setCancelled(true);
                itemStack.setAmount(itemStack.getAmount()-1);
                generate(event.getClickedBlock(), width, height);
                deleteSquare(event.getClickedBlock());
                afterGeneration(event);
                event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ENTITY_CHICKEN_EGG, 1, 1);
            }
        }
    }

    public void generate(Block block, int width, int height) {

        int firstx = block.getLocation().getBlockX() - width;
        int firsty = block.getLocation().getBlockY() - height;
        int firstz = block.getLocation().getBlockZ() - width;

        int secondx = block.getLocation().getBlockX() + width;
        int secondy = block.getLocation().getBlockY() + height;
        int secondz = block.getLocation().getBlockZ() + width;

        for (int x = firstx; x < secondx; x++) {
            for (int y = firsty; y < secondy; y++) {
                for (int z = firstz; z < secondz; z++) {
                    Block testBlock = block.getWorld().getBlockAt(x,y,z);
                    Material material = testBlock.getType();
                    if (!material.isAir()){
                        if (blockReplacements.containsKey(material)){
                            testBlock.setType(blockReplacements.get(material), true);
                            if (testBlock.getRelative(BlockFace.UP).getType().equals(Material.GRASS)){
                                testBlock.getRelative(BlockFace.UP).setType(Material.AIR);
                            }
                        }
                        if (material.toString().contains("LOG")){
                            if (logReplacement != null){
                                testBlock.setType(logReplacement, true);
                            }

                        }
                        if (material.toString().contains("LEAVES")){
                            if (leafReplacement != null){
                                testBlock.setType(leafReplacement, true);
                            }
                        }
                        if (hasGrowthFeatures()){
                            GrowthFeature growthFeature = getRandomFeature();
                            if (growthFeature.isBlockType()){
                                growthFeature.attemptGenerateBlock(testBlock);
                            }
                            else {
                                growthFeature.attemptGenerateEntity(testBlock);
                            }
                        }
                    }
                }
            }
        }
    }

    public abstract void afterGeneration(PlayerInteractEvent event);

    public boolean checkForSquare(Block origin){
        Location firstCheck = origin.getLocation().subtract(2,0,-2);
        Location secondCheck = origin.getLocation().subtract(-2,0,2);

        int yLevel = origin.getY();

        int firstX = firstCheck.getBlockX();
        int secondX = secondCheck.getBlockX();

        int firstZ = firstCheck.getBlockZ();
        int secondZ = secondCheck.getBlockZ();

        int timesMaterialFound = 0;

        for (int x = firstX; x < secondX+1; x++){
            for (int z = firstZ; z > secondZ-1; z--){
                Location location = new Location(origin.getWorld(), x,yLevel,z);
                if (location.getBlock().getType().equals(origin.getType())){
                    timesMaterialFound++;
                }
            }
        }
        return timesMaterialFound == 25;
    }

    private void deleteSquare(Block origin){
        Location firstCheck = origin.getLocation().subtract(2,0,-2);
        Location secondCheck = origin.getLocation().subtract(-2,0,2);

        int yLevel = origin.getY();

        int firstX = firstCheck.getBlockX();
        int secondX = secondCheck.getBlockX();

        int firstZ = firstCheck.getBlockZ();
        int secondZ = secondCheck.getBlockZ();

        for (int x = firstX; x < secondX+1; x++){
            for (int z = firstZ; z > secondZ-1; z--){
                Location location = new Location(origin.getWorld(), x,yLevel,z);
                location.getBlock().setType(Material.AIR);
            }
        }
    }
}
