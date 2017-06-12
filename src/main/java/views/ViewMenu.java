package views;

import models.MatchingEnum;
import scanners.InstanceScanner;
import services.FileReaderService;
import services.impl.DefaultFileReaderService;

import java.util.List;

/**
 * Created by Roman Nagibov
 */
public class ViewMenu {

    private static ViewMenu viewMenu;
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
                runFilesMenu();
            }
        }
    }

    private void runModeMenu(List<String> inputFileLines, List<String> patternFileLines) {
        System.out.println(MenuTemplate.MODE_MENU.toString());
        switch (scanner.readRow()) {
            case "1": {
                printResult(getResult(MatchingEnum.FullMatchMode, inputFileLines, patternFileLines));
                runResultMenu(inputFileLines, patternFileLines);
                break;
            }
            case "2": {
                printResult(getResult(MatchingEnum.EntryMatchMode, inputFileLines, patternFileLines));
                runResultMenu(inputFileLines, patternFileLines);
                break;
            }
            case "3": {
                printResult(getResult(MatchingEnum.LevenshteinMatchMode, inputFileLines, patternFileLines));
                runResultMenu(inputFileLines, patternFileLines);
                break;
            }
            case "4": {
                stop();
                break;
            }
            default: {
                System.out.println("Choose right option!");
                runModeMenu(inputFileLines, patternFileLines);
            }
        }

    }

    private void runResultMenu(List<String> inputFileLines, List<String> patternFileLines) {
        System.out.println(MenuTemplate.RESULT_MENU.toString());
        switch (scanner.readRow()) {
            case "1": {
                runModeMenu(inputFileLines, patternFileLines);
                break;
            }
            case "2": {
                runFilesMenu();
                break;
            }
            case "3": {
                stop();
                break;
            }
            default: {
                System.out.println("Choose right option!");
                runResultMenu(inputFileLines, patternFileLines);
            }
        }

    }

    private void runFilePatternMenu(String filePath) throws IllegalArgumentException {
        System.out.println(MenuTemplate.FILES_PATH_MENU.toString());
        switch (scanner.readRow()) {
            case "1": {
                patternFileResolver(filePath);
                break;
            }
            case "2": {
                stop();
                break;
            }
            default: {
                System.out.println("Choose right option!");
                runFilePatternMenu(filePath);
            }
        }

    }

    private void inputFileResolver() {
        System.out.println("location input file ");
        String filePath = InstanceScanner.getInstance().readRow();

        if (!fileReaderService.isCorrectPath(filePath)) {
            System.out.println("Choose right path");
            runFilesMenu();
            return;
        }

        patternFileResolver(filePath);
    }

    private void patternFileResolver(String inputFilePath) {
        System.out.println("location patterns file");
        String patternFilePath = InstanceScanner.getInstance().readRow();

        if (!fileReaderService.isCorrectPath(patternFilePath)) {
            System.out.println("Choose right path");
            runFilePatternMenu(inputFilePath);
            return;
        }

        List<String> inputRows = fileReaderService.readRows(inputFilePath);
        List<String> patternRows = fileReaderService.readRows(patternFilePath);
        runModeMenu(inputRows, patternRows);
    }

    private void printResult(List<String> result) {
        if (null == result || result.size() == 0) {
            System.out.println("Empty list");
            return;
        }
        result.forEach(System.out::println);
    }

    private List<String> getResult(MatchingEnum mode, List<String> inputFileLines, List<String> patternFileLines) {
        return mode.getMode().match(inputFileLines, patternFileLines);
    }

    private void stop() {
        System.out.println("Good bye!");
        InstanceScanner.getInstance().stopScannerThread();
        run = false;
    }

}
