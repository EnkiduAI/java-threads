package com.epam.solovey;

import com.epam.solovey.entity.Train;
import com.epam.solovey.exception.TrainException;
import com.epam.solovey.parser.Parser;
import com.epam.solovey.reader.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    private static final String FILE_PATH = String.valueOf(Main.class.getResource("/trainParameters.txt"));

    public static void main(String[] args) {

        Reader reader = new Reader();
        Parser parser = new Parser();
        try {
            List<String> trainsParameters = reader.readFromFile(FILE_PATH);
            List<Train> trains = new ArrayList<>();
            for(String param : trainsParameters){
                Train train = parser.parseParameters(param);
                trains.add(train);
            }
            ExecutorService executorService = Executors.newFixedThreadPool(trains.size());
            trains.forEach(executorService::execute);
            executorService.shutdown();
        }catch (TrainException ignored){

        }
    }
}
