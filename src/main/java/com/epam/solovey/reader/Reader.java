package com.epam.solovey.reader;

import com.epam.solovey.exception.TrainException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class Reader {
    public List<String> readFromFile(String filePath) throws TrainException {
        try{
            Path path = Path.of(filePath);
            return Files.lines(path).collect(Collectors.toList());
        }catch (InvalidPathException | IOException e){
            throw new TrainException("Cannot open file", e);
        }
    }
}
