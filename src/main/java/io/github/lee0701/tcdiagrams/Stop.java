package io.github.lee0701.tcdiagrams;

public class Stop {

    private String destination;
    private long stopDuration;
    private long departTime;

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
}
