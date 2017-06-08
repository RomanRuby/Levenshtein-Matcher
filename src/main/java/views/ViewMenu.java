package views;

import models.MatchingEnum;
import org.apache.commons.lang3.StringUtils;
import services.FileReaderService;
import services.impl.DefaultFileReaderService;
import scanners.InstanceScanner;

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
        String row = scanner.readRow();
        if (!StringUtils.isNumeric(row)) {
            System.out.println("Input must be number!");
            runFilesMenu();
            return;
        }
        int input = Integer.valueOf(row);
        switch (input) {
            case 1: {
                inputFileHandle();
                break;
            }
            case 2: {
                stop();
                break;
            }
            default:
                System.out.println("Choose right option!");
                runMainMenu();
        }

    }

    private void runModeMenu(Map<String, List<String>> files) {
        System.out.println(MenuTemplate.MODE_MENU.toString());
        String row = scanner.readRow();
        if (!StringUtils.isNumeric(row)) {
            System.out.println("Input must be number!");
            runModeMenu(files);
            return;
        }
        int input = Integer.valueOf(row);
            switch (input) {
                case 1: {
                    printResult(getResult(MatchingEnum.FullMatchMode, files));
                    runResultMenu(files);
                    break;
                }
                case 2: {
                    printResult(getResult(MatchingEnum.EntryMatchMode, files));
                    runResultMenu(files);
                    break;
                }
                case 3: {
                    printResult(getResult(MatchingEnum.LevenshteinMatchMode, files));
                    runResultMenu(files);
                    break;
                }
                case 4: {
                    stop();
                    break;
                }
                default:
                    System.out.println("Choose right option!");
                    runModeMenu(files);
            }

        }

    private void runResultMenu(Map<String, List<String>> files) {
        System.out.println(MenuTemplate.RESULT_MENU.toString());
        String row = scanner.readRow();
        if (!StringUtils.isNumeric(row)) {
            System.out.println("Input must be number!");
            runResultMenu(files);
            return;
        }
        int input = Integer.valueOf(row);
            switch (input) {
                case 1: {
                    runModeMenu(files);
                    break;
                }
                case 2: {
                    runFilesMenu();
                    break;
                }
                case 3: {
                    stop();
                    break;
                }
                default:
                    System.out.println("Choose right option!");
                    runResultMenu(files);
            }

    }

    private void runFilePatternMenu(String file) throws IllegalArgumentException {
        System.out.println(MenuTemplate.FILES_PATH_MENU.toString());
        String row = scanner.readRow();
        if (!StringUtils.isNumeric(row)) {
            System.out.println("Input must be number!");
            runFilePatternMenu(file);
            return;
        }
        int input = Integer.valueOf(row);
            switch (input) {
                case 1: {
                    patternFileHandle(file);
                    break;
                }
                case 2: {
                    stop();
                    break;
                }
                default:
                    System.out.println("Choose right option!");
            }

    }

    private void runFileInputMenu() throws IllegalArgumentException {
        System.out.println(MenuTemplate.FILES_PATH_MENU.toString());
        String row = scanner.readRow();
        if (!StringUtils.isNumeric(row)) {
            System.out.println("Input must be number!");
            runFileInputMenu();
            return;
        }
        int input = Integer.valueOf(row);
            switch (input) {
                case 1: {
                    inputFileHandle();
                    break;
                }
                case 2: {
                    stop();
                    break;
                }
                default:
                    System.out.println("Choose right option!");
            }

    }

    private void inputFileHandle() {
        System.out.println("location input file with type txt ");
        String file = InstanceScanner.getInstance().readRow();
        if (fileReaderService.isCorrectPath(file)) {
            patternFileHandle(file);
            return;
        }
        System.out.println("Choose right path");
        runFileInputMenu();
    }

    private void patternFileHandle(String file) {
        System.out.println("location patterns file with type txt");
        String patternFile = InstanceScanner.getInstance().readRow();

        if (fileReaderService.isCorrectPath(patternFile)) {
            runModeMenu(readFiles(file, patternFile));
            return;
        }

        System.out.println("Choose right path");
        runFilePatternMenu(file);
    }

    private Map<String, List<String>> readFiles(String inputPath, String patternsPath) {
        Map<String, List<String>> files = new HashMap<>();

        files.put(INPUT_KEY, fileReaderService.readRows(inputPath));
        files.put(PATTERN_KEY, fileReaderService.readRows(patternsPath));

        return files;
    }

    private void printResult(List<String> result) {
        if(null == result || result.size() == 0){
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
