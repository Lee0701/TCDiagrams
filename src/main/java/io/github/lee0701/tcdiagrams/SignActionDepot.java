package io.github.lee0701.tcdiagrams;

import com.bergerkiller.bukkit.tc.Permission;
import com.bergerkiller.bukkit.tc.actions.MemberActionLaunch;
import com.bergerkiller.bukkit.tc.events.SignActionEvent;
import com.bergerkiller.bukkit.tc.events.SignChangeActionEvent;
import com.bergerkiller.bukkit.tc.signactions.SignAction;
import com.bergerkiller.bukkit.tc.signactions.SignActionType;

public class SignActionDepot extends SignAction {

    private static String[] TYPES = new String[] {"depot"};

    @Override
    public boolean match(SignActionEvent info) {
        return info.isType(TYPES);
    }

    @Override
    public void execute(SignActionEvent info) {
        if(info.isTrainSign() && info.hasRails() && info.hasGroup() && info.getGroup() != null) {
            Train train = Train.of(info.getGroup().getProperties().getTrainName());
            if(train.getTimeTableName() == null) {
                info.getGroup().getActions().addActionWaitForever();
                train.setSystemName(info.getLine(3));
            } else {
                info.getGroup().getActions().addAction(new MemberActionLaunch());
            }
        }
    }

    @Override
    public String getRailDestinationName(SignActionEvent info) {
        return info.getLine(2);
    }

    @Override
    public boolean build(SignChangeActionEvent event) {
        return handleBuild(event, Permission.BUILD_STATION, "train depot", "mark depot for train systems");
    }
}
