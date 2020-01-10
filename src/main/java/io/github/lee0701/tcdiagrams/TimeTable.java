package io.github.lee0701.tcdiagrams;

import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TimeTable implements ConfigurationSerializable {

    public static Map<String, TimeTable> TIME_TABLES = new HashMap<>();

    private final String name;

    private final List<Stop> stops = new ArrayList<>();

    public TimeTable(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<Stop> getStops() {
        return stops;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> result = new HashMap<>();
        result.put("name", name);
        result.put("stops", stops);
        return result;
    }

    public static TimeTable deserialize(Map<String, Object> args) {
        try {
            String name = (String) args.get("name");
            TimeTable timeTable = new TimeTable(name);
            timeTable.getStops().addAll((List<Stop>) args.get("stops"));
            TimeTable.TIME_TABLES.put(name, timeTable);
            return timeTable;
        } catch(NullPointerException | ClassCastException ex) {
            ex.printStackTrace();
            return null;
        }
    }

}
