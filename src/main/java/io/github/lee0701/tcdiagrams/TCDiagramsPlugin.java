package io.github.lee0701.tcdiagrams;

import com.bergerkiller.bukkit.tc.signactions.SignAction;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public final class TCDiagramsPlugin extends JavaPlugin {

    public static TCDiagramsPlugin getInstance() {
        return getPlugin(TCDiagramsPlugin.class);
    }

    private File dataFile = new File(getDataFolder(), "data.yml");
    private YamlConfiguration dataConfiguration;

    public void reload() {
        dataConfiguration = YamlConfiguration.loadConfiguration(dataFile);
        if(dataConfiguration.isList("timeTables")) {
            dataConfiguration.getList("timeTables");
        }
    }

    private void save() {
        dataConfiguration.set("timeTables", new ArrayList<>(TimeTable.TIME_TABLES.values()));
        try {
            dataConfiguration.save(dataFile);
        } catch(IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onEnable() {
        getDataFolder().mkdirs();

        ConfigurationSerialization.registerClass(TimeTable.class);
        ConfigurationSerialization.registerClass(Stop.class);

        reload();

        getCommand("traintime").setExecutor(new CommandTrainTime());

        SignAction.register(new SignActionStop());

    }

    @Override
    public void onDisable() {
        save();
    }

}
