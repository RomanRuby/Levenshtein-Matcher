package services.impl;

import services.FileReaderService;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * @author Roman Nagibov
 */
public class DefaultFileReaderService implements FileReaderService {

    @Override
    public List<String> readRow(String filePath) throws IOException {
          return Files.readAllLines(Paths.get(filePath), StandardCharsets.UTF_8);
    }

    @Override
    public Boolean checkCorrectPathAndType(String filePathString) {
        File file = new File(filePathString);
        return file.exists() && !(file.isDirectory());
    }

}