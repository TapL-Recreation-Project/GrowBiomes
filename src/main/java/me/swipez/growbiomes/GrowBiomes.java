package me.swipez.growbiomes;

import org.bukkit.plugin.java.JavaPlugin;

public final class GrowBiomes extends JavaPlugin {

    private static GrowBiomes plugin;

    @Override
    public void onEnable() {
        plugin = this;
        GrowBiomeRegistry.initBiomes();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static GrowBiomes getPlugin() {
        return plugin;
    }
}
