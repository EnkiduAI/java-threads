package com.epam.solovey.util;

public class TrainIdGenerator {
private static long id;

private TrainIdGenerator(){

}

public static long generateId(){
    return ++id;
}
}
