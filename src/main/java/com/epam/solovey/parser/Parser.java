package com.epam.solovey.parser;

import com.epam.solovey.entity.Train;

public class Parser {
    private static final String WHITE_SPACE_DELIMITER = "\\s+";

    public Train parseParameters(String parameters) {
        String[] trainParameters = parameters.split(WHITE_SPACE_DELIMITER);
        Train.Type type = Train.Type.valueOf(trainParameters[0]);
        Train.Destination destination = Train.Destination.valueOf(trainParameters[1]);
        Train.Status status = Train.Status.valueOf(trainParameters[2]);
        return new Train(type, destination, status);
    }
}
