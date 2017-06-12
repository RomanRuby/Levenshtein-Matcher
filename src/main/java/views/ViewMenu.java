package views;

import models.MatchingEnum;
import scanners.InstanceScanner;
import services.FileReaderService;
import services.impl.DefaultFileReaderService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Roman Nagibov
 */
public class ViewMenu {

    private static final String INPUT_KEY = "input";
    private static final String PATTERN_KEY = "pattern";
    private static ViewMenu viewMenu;
    private static String inputFile;
    private static String patternsFile;
    private final InstanceScanner scanner = InstanceScanner.getInstance();
    private final FileReaderService fileReaderService = DefaultFileReaderService.getInstance();
    private boolean run = true;


    private ViewMenu() {
    }

    public static ViewMenu getInstance() {
        if (null == viewMenu) {
            viewMenu = new ViewMenu();
        }
        return viewMenu;
    }

    public void runMainMenu() {
        while (run) {
            runFilesMenu();
        }
    }


    private void runModeMenu(Map<String, List<String>> files) {
        System.out.println(MenuTemplate.MODE_MENU.toString());
        switch (scanner.readRow()) {
            case "1": {
                printResult(getResult(MatchingEnum.FullMatchMode, files));
                runResultMenu(files);
                break;
            }
            case "2": {
                printResult(getResult(MatchingEnum.EntryMatchMode, files));
                runResultMenu(files);
                break;
            }
            case "3": {
                printResult(getResult(MatchingEnum.LevenshteinMatchMode, files));
                runResultMenu(files);
                break;
            }
            case "4": {
                stop();
                break;
            }
            default: {
                System.out.println("Choose right option!");
                runModeMenu(files);
            }
        }

    }

    private void runResultMenu(Map<String, List<String>> files) {
        System.out.println(MenuTemplate.RESULT_MENU.toString());
        switch (scanner.readRow()) {
            case "1": {
                runModeMenu(files);
                break;
            }
            case "2": {
                clearFilesPath();
                runFilesMenu();
                break;
            }
            case "3": {
                stop();
                break;
            }
            default: {
                System.out.println("Choose right option!");
                runResultMenu(files);
            }
        }

    }

    private void runFilesMenu() {
        System.out.println(MenuTemplate.FILES_MENU.toString());
        switch (scanner.readRow()) {
            case "1": {
                inputFileResolver();
                break;
            }
            case "2": {
                stop();
                break;
            }
            default: {
                System.out.println("Choose right option!");
                runMainMenu();
            }
        }

    }

    private void clearFilesPath() {
        inputFile = null;
        patternsFile = null;
    }

    private void inputFileResolver() {
        System.out.println("Input path");
        String file = InstanceScanner.getInstance().readRow();

        if (fileReaderService.isCorrectPath(file)) {
            if (null == inputFile) {
                inputFile = file;
                System.out.println("Choose the second file");
                inputFileResolver();
            }
            patternsFile = file;
            runModeMenu(readFiles(inputFile, patternsFile));
            return;
        }

        System.out.println("Choose right path");
        runFilesMenu();
    }

    private Map<String, List<String>> readFiles(String inputPath, String patternsPath) {
        Map<String, List<String>> files = new HashMap<>();

        files.put(INPUT_KEY, fileReaderService.readRows(inputPath));
        files.put(PATTERN_KEY, fileReaderService.readRows(patternsPath));

        return files;
    }

    private void printResult(List<String> result) {
        if (null == result || result.size() == 0) {
            System.out.println("Empty list");
            return;
        }
        result.forEach(System.out::println);
    }

    private List<String> getResult(MatchingEnum mode, Map<String, List<String>> files) {
        return mode.getMode().match(files.get(INPUT_KEY), files.get(PATTERN_KEY));
    }

    private void stop() {
        System.out.println("Good bye!");
        InstanceScanner.getInstance().stopScannerThread();
        run = false;
    }

}
