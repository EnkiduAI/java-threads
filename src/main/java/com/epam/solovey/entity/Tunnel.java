package com.epam.solovey.entity;

import com.epam.solovey.exception.TrainException;
import com.epam.solovey.util.TunnelIdGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.TimeUnit;


public class Tunnel {
    private static final Logger logger = LogManager.getLogger();
    private final long tunnelId;


    public Tunnel() {
        this.tunnelId = TunnelIdGenerator.generateTunnelId();
    }

    public long getTunnelId() {
        return tunnelId;
    }

    public void proceed (Train train) throws TrainException {
        train.setStatus(Train.Status.ON_WAY);
        try {
            train.setStatus(Train.Status.ON_WAY);
            TimeUnit.MILLISECONDS.sleep(300);
        }catch(InterruptedException e){
            throw new TrainException("Thread interrupted", e);
        }
        train.setStatus(Train.Status.FINISHED);
        logger.info("Train "+ train.getId() + " destination "
                + train.getTrainDestination() + " passed the tunnel "+ getTunnelId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tunnel tunnel = (Tunnel) o;

        return tunnelId == tunnel.tunnelId;
    }

    @Override
    public int hashCode() {
        return (int) (tunnelId ^ (tunnelId >>> 32));
    }
}
