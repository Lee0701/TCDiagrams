package io.github.lee0701.tcdiagrams;

import com.bergerkiller.bukkit.tc.properties.TrainProperties;

import java.util.HashMap;
import java.util.Map;

public class Train {

    public static Map<String, Train> TRAINS = new HashMap<>();

    public static Train of(String trainName) {
        if(!TRAINS.containsKey(trainName)) TRAINS.put(trainName, new Train(trainName));
        return TRAINS.get(trainName);
    }

    private final String trainName;
    private String timeTableName;
    private long timeTableStart;
    private int stopIndex;

    public Train(String trainName) {
        this.trainName = trainName;
    }

    public void startTimeTable(TimeTable timeTable) {
        this.timeTableName = timeTable.getName();
        this.timeTableStart = System.currentTimeMillis();
        this.stopIndex = -1;
        this.nextStop();
    }

    public void nextStop() {
        TimeTable timeTable = TimeTable.TIME_TABLES.get(timeTableName);
        if(stopIndex == timeTable.getStops().size()-1) {
            TrainProperties.get(trainName).clearDestination();
            this.timeTableName = null;
        } else {
            TrainProperties.get(trainName).setDestination(timeTable.getStops().get(++stopIndex).getDestination());
        }
    }

    public String getTrainName() {
        return trainName;
    }

    public String getTimeTableName() {
        return timeTableName;
    }

    public void setTimeTableName(String timeTableName) {
        this.timeTableName = timeTableName;
    }

    public int getStopIndex() {
        return stopIndex;
    }

    public void setStopIndex(int stopIndex) {
        this.stopIndex = stopIndex;
    }

    public long getTimeTableStart() {
        return timeTableStart;
    }

    public void setTimeTableStart(long timeTableStart) {
        this.timeTableStart = timeTableStart;
    }
}
