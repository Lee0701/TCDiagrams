package io.github.lee0701.tcdiagrams;

import com.bergerkiller.bukkit.tc.signactions.SignAction;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public final class TCDiagramsPlugin extends JavaPlugin {

    public static TCDiagramsPlugin getInstance() {
        return getPlugin(TCDiagramsPlugin.class);
    }

    private File dataFile = new File(getDataFolder(), "data.yml");
    private YamlConfiguration dataConfiguration;

    public void reload() {
        dataConfiguration = YamlConfiguration.loadConfiguration(dataFile);
    }

    private void save() {
        try {
            dataConfiguration.save(dataFile);
        } catch(IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onEnable() {
        getDataFolder().mkdirs();

        reload();

        getCommand("traintime").setExecutor(new CommandTrainTime());

        SignAction.register(new SignActionStop());

    }

    @Override
    public void onDisable() {
        save();
    }

}
