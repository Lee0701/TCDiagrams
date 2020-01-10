package io.github.lee0701.tcdiagrams;

import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.HashMap;
import java.util.Map;

public class Stop implements ConfigurationSerializable {

    private String destination;
    private long stopDuration;
    private long departTime;

    public Stop(String destination, long stopDuration, long departTime) {
        this.destination = destination;
        this.stopDuration = stopDuration;
        this.departTime = departTime;
    }

    public Stop(String destination) {
        this.destination = destination;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public long getStopDuration() {
        return stopDuration;
    }

    public void setStopDuration(long stopDuration) {
        this.stopDuration = stopDuration;
    }

    public long getDepartTime() {
        return departTime;
    }

    public void setDepartTime(long departTime) {
        this.departTime = departTime;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> result = new HashMap<>();
        result.put("destination", destination);
        result.put("stopDuration", stopDuration);
        result.put("departTime", departTime);
        return result;
    }

    public static Stop deserialize(Map<String, Object> args) {
        try {
            String destination = (String) args.get("destination");
            long stopDuration = (Integer) args.get("stopDuration");
            long departTime = (Integer) args.get("departTime");
            return new Stop(destination, stopDuration, departTime);
        } catch(NullPointerException | ClassCastException ex) {
            ex.printStackTrace();
            return null;
        }
    }

}
