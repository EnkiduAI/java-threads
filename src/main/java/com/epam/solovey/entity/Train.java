package com.epam.solovey.entity;

import com.epam.solovey.exception.TrainException;
import com.epam.solovey.util.TrainIdGenerator;

public class Train implements Runnable {
    private final long id;
    private Status status;
    private Type trainType;
    private Destination trainDestination;

    @Override
    public void run() {
        TrafficControl trafficControl = TrafficControl.getInstance();
        trafficControl.iterationCount();
        Tunnel tunnel = trafficControl.acquireTunnel(this);
        try {
            tunnel.proceed(this);
        } catch (TrainException e) {
            e.printStackTrace();
        }
        trafficControl.releaseTunnel(tunnel);
    }

    public enum Type {
        PASSENGER, CARGO
    }

    public enum Destination {
        EAST, WEST
    }

    public enum Status {
        NEW, ON_WAY, FINISHED
    }

    public Train(Type trainType, Destination trainDestination, Status status) {
        this.trainType = trainType;
        this.trainDestination = trainDestination;
        this.status = status;
        this.id = TrainIdGenerator.generateId();
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }

    public Type getTrainType() {
        return trainType;
    }

    public Destination getTrainDestination() {
        return trainDestination;
    }

    public void setTrainType(Type trainType) {
        this.trainType = trainType;
    }

    public void setTrainDestination(Destination trainDestination) {
        this.trainDestination = trainDestination;
    }

    public long getId() {
        return id;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Train train = (Train) o;

        if (id != train.id) return false;
        return status == train.status;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }
}
