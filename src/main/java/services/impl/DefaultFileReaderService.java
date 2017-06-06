package services.impl;

import displays.ViewDisplay;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import services.FileReaderService;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

/**
 * @author Roman Nagibov
 */
public class DefaultFileReaderService implements FileReaderService {

    private static final Logger LOGGER = LogManager.getLogger(ViewDisplay.class.getName());


    @Override
    public List<String> readRow(String filePath)  {
        try {
            return Files.readAllLines(Paths.get(filePath), StandardCharsets.UTF_8);
        } catch (IOException e) {
            LOGGER.info("Info Message Logged", new IOException(e.toString()));
            return  new ArrayList<String>();
        }
    }

    @Override
    public Boolean checkCorrectPathAndType(String filePathString) {
        File file = new File(filePathString);
        return file.exists() && !(file.isDirectory());
    }

}
