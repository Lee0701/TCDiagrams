package io.github.lee0701.tcdiagrams;

import com.bergerkiller.bukkit.tc.properties.TrainProperties;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.*;

public class TrainSystem {

    public static Map<String, TrainSystem> TRAIN_SYSTEMS = new HashMap<>();

    private final String name;

    private final List<TrainRun> trainRuns = new ArrayList<>();

    private long startTime;
    private int runIndex;
    private boolean started;

    public TrainSystem(String name) {
        this.name = name;
    }

    public void start() {
        for(TrainRun trainRun : trainRuns) trainRun.setStarted(false);
        this.startTime = System.currentTimeMillis();
        this.runIndex = -1;
        this.started = true;

        trainRuns.sort(Comparator.comparingLong(TrainRun::getStartTimeOffset));
        nextRun();
    }

    public void stop() {
        for(TrainRun trainRun : trainRuns) trainRun.setStarted(false);
        this.startTime = 0;
        this.started = false;
    }

    public void nextRun() {
        TrainRun trainRun = trainRuns.get(++runIndex);
        long delay = trainRun.getStartTimeOffset() - System.currentTimeMillis();
        delay /= 1000/20;
        new BukkitRunnable() {
            @Override
            public void run() {
                if(!started) return;
                trainRun.start(findTrain());
                if(runIndex == trainRuns.size()-1) {
                    stop();
                    return;
                }
                nextRun();
            }
        }.runTaskLaterAsynchronously(TCDiagramsPlugin.getInstance(), delay);
    }

    private TrainProperties findTrain() {
        for(Train train : Train.TRAINS.values()) {
            if(train.getSystemName().equals(this.name)) return TrainProperties.get(train.getTrainName());
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public List<TrainRun> getTrainRuns() {
        return trainRuns;
    }

}
