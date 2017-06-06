package services;

import java.util.List;

/**
 * @author Roman Nagibov
 */
public interface FileReaderService {

    List<String> readRows(String filePath);

    void checkCorrectPathAndType(String filePathString);

}
