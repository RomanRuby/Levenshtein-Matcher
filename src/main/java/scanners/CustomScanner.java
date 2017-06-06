package scanners;

import lombok.Data;

import java.util.Scanner;

/**
 * @author Roman Nagibov
 */
@Data
public class CustomScanner {

    private  Scanner scanner = new Scanner(System.in);
    private static CustomScanner instance;

    private CustomScanner() {
    }

    public static CustomScanner getInstance() {
        if (instance == null) {
            instance = new CustomScanner();
        }
        return instance;
    }

    public void checkNextLine

}
