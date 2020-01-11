package io.github.lee0701.tcdiagrams;

import com.bergerkiller.bukkit.tc.properties.TrainProperties;

public class TrainRun {

    private final String timeTableName;
    private long startTimeOffset;

    private boolean started;

    public TrainRun(String timeTableName) {
        this.timeTableName = timeTableName;
    }

    public void start(TrainProperties train) {
        Train.of(train.getTrainName()).startTimeTable(TimeTable.TIME_TABLES.get(timeTableName));
        this.setStarted(true);
    }

    public String getTimeTableName() {
        return timeTableName;
    }

    public long getStartTimeOffset() {
        return startTimeOffset;
    }

    public void setStartTimeOffset(long startTimeOffset) {
        this.startTimeOffset = startTimeOffset;
    }

    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }
}
