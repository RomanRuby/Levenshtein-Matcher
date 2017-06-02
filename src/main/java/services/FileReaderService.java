package services;

import java.io.IOException;
import java.util.List;

/**
 * @author Roman Nagibov
 */
public interface FileReaderService {

    List<String> readRow(String filePath) throws IOException;

    Boolean checkCorrectPathAndType(String filePathString);

}
