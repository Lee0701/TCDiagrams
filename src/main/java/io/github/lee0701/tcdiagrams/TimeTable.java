package io.github.lee0701.tcdiagrams;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TimeTable {

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
}
