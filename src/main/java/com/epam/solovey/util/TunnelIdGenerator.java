package com.epam.solovey.util;

public class TunnelIdGenerator {

    private static long id;

    private TunnelIdGenerator(){

    }

    public static long generateTunnelId(){
        return ++id;
    }
}
