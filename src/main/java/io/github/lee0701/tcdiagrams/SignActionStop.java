package io.github.lee0701.tcdiagrams;

import com.bergerkiller.bukkit.tc.Permission;
import com.bergerkiller.bukkit.tc.events.SignActionEvent;
import com.bergerkiller.bukkit.tc.events.SignChangeActionEvent;
import com.bergerkiller.bukkit.tc.signactions.SignActionStation;
import com.bergerkiller.bukkit.tc.signactions.SignActionType;

public class SignActionStop extends SignActionStation {

    private static String[] TYPES = new String[] {"stop"};

    @Override
    public boolean match(SignActionEvent event) {
        return event.isType(TYPES);
    }

    @Override
    public void execute(SignActionEvent info) {
        if(info.isTrainSign() && info.hasRails() && info.hasGroup() && info.getGroup() != null) {
            Train train = Train.of(info.getGroup().getProperties().getTrainName());
            if(train.getTimeTableName() == null) return;

            TimeTable timeTable = TimeTable.TIME_TABLES.get(train.getTimeTableName());
            if(timeTable == null) return;

            if(train.getStopIndex() >= timeTable.getStops().size()) return;
            Stop currentStop = timeTable.getStops().get(train.getStopIndex());

            if(currentStop.getDestination().equals(info.getLine(2))) {
                long currentTime = System.currentTimeMillis();

                long stopDuration = (train.getTimeTableStart() + currentStop.getDepartTime()) - currentTime;
                if(stopDuration < currentStop.getStopDuration()) stopDuration = currentStop.getStopDuration();
                double stopSeconds = stopDuration / 1000.0;

                info.setLine(1, "station");
                info.setLine(2, String.format("%f", stopSeconds));
                info.setLine(3, "continue");
                super.execute(info);
                info.setLine(1, "stop");
                info.setLine(2, currentStop.getDestination());
                info.setLine(3, "");

                 if(info.isAction(SignActionType.GROUP_LEAVE)) {
                    train.nextStop();
                }
            }

        }
    }

    @Override
    public String getRailDestinationName(SignActionEvent info) {
        return info.getLine(2);
    }

    @Override
    public boolean build(SignChangeActionEvent event) {
        return handleBuild(event, Permission.BUILD_STATION, "train stop", "mark stop for train time tables");
    }

}
