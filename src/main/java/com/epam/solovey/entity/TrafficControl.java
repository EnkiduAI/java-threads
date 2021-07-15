package com.epam.solovey.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TrafficControl {
    private int tunnelNumber;
    private int tunnelCapacity;
    private static final Logger logger = LogManager.getLogger();
    private final Lock lock = new ReentrantLock();
    private final Deque<Tunnel> availableTunnels = new ArrayDeque<>();
    private final Deque<Condition> waitingTrains = new ArrayDeque<>();
    private static final AtomicBoolean isFree = new AtomicBoolean(false);

    Condition condition = lock.newCondition();
    private static class LoadSingleton {
        static final TrafficControl INSTANCE = new TrafficControl(2, 2);
    }

    public static TrafficControl getInstance() {
        return LoadSingleton.INSTANCE;
    }

    public TrafficControl(int tunnelNumber, int tunnelCapacity) {
        this.tunnelNumber = tunnelNumber;
        this.tunnelCapacity = tunnelCapacity;
        for (int i = 0; i < tunnelNumber; i++) {
            availableTunnels.add(new Tunnel());
        }
    }

    public Tunnel acquireTunnel(Train train) {
        lock.lock();
        try{
            while (availableTunnels.isEmpty()){
                condition.await();
            }
            if(train.getTrainDestination() == Train.Destination.WEST && iterationCount() % 2 != 0){
                waitingTrains.addFirst(condition);
            } else {
                waitingTrains.addLast(condition);
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
        return availableTunnels.removeLast();
    }

    public void releaseTunnel(Tunnel tunnel){
        lock.lock();
        try{
            condition = waitingTrains.pollFirst();
            availableTunnels.offer(tunnel);
            condition.signalAll();
        }finally {
            lock.unlock();
        }
    }


    public int iterationCount() {
        AtomicInteger count = new AtomicInteger(0);
        return count.incrementAndGet();
    }

}
