package views;

import models.MatchingEnum;
import org.apache.commons.lang3.StringUtils;
import services.FileReaderService;
import services.impl.DefaultFileReaderService;
import threadScanners.InstanceScanner;

import java.util.HashMap;
import java.util.InputMismatchException;
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
    private String inputFile;
    private String patternsFile;

    private ViewMenu() {

    }

    public static ViewMenu getInstance() {
        if (null == viewMenu) {
            viewMenu = new ViewMenu();
        }
        return viewMenu;
    }

    public void getMainMenu() {
        while (run) {
            getFilesMenu();
        }
    }

    private void getFilesMenu() {

        System.out.println(MenuTemplate.FILES_MENU.toString());


        String row = scanner.readRow();
        if (!StringUtils.isNumeric(row)) {
            System.out.println("Input must be number!");
            getFilesMenu();
        }
        int input = Integer.valueOf(row);

        switch (input) {
            case 1: {
                System.out.println("location input file with type txt ");
                inputFile = getCorrectPath();

                System.out.println("location patterns file with type txt");
                patternsFile = getCorrectPath();
                readFiles(inputFile, patternsFile);
                break;
            }
            case 2: {
                stop();
                break;
            }
            default:
                System.out.println("Choose right option!");
                getMainMenu();
        }

    }

    public String getCorrectPath() throws IllegalArgumentException {
        String file = InstanceScanner.getInstance().readRow();
    while(!loadFiles(file)) {
        file = InstanceScanner.getInstance().readRow();
        getFilePathMenu(file);
    }
        return file;
    }

    private void getModeMenu(Map<String, List<String>> files) {

        System.out.println(MenuTemplate.MODE_MENU.toString());
        try {
            int input = Integer.valueOf(InstanceScanner.getInstance().readRow());
            switch (input) {
                case 1: {
                    printResult(getResult(MatchingEnum.FullMatchMode, files));
                    getResultMenu(files);
                    break;
                }
                case 2: {
                    printResult(getResult(MatchingEnum.EntryMatchMode, files));
                    getResultMenu(files);
                    break;
                }
                case 3: {
                    printResult(getResult(MatchingEnum.LevenshteinMatchMode, files));
                    getResultMenu(files);
                    break;
                }
                case 4: {
                    stop();
                    break;
                }
                default:
                    System.out.println("Choose right option!");
                    getModeMenu(files);
            }
        } catch (InputMismatchException ex) {
            System.out.println("Input must be number!");
            getModeMenu(files);
        }

    }

    private void getResultMenu(Map<String, List<String>> files) {
        System.out.println(MenuTemplate.RESULT_MENU.toString());
        try {
            int input = Integer.valueOf(InstanceScanner.getInstance().readRow());
            switch (input) {
                case 1: {
                    getModeMenu(files);
                    break;
                }
                case 2: {
                    getFilesMenu();
                    break;
                }
                case 3: {
                    stop();
                    break;
                }
                default:
                    System.out.println("Choose right option!");
                    getResultMenu(files);
            }
        } catch (InputMismatchException ex) {
            System.out.println("Input must be number!");
            getResultMenu(files);
        }
    }

    private void getFilePathMenu(String path) throws IllegalArgumentException {
        System.out.println(MenuTemplate.FILES_PATH_MENU.toString());
        try {
            int input = Integer.valueOf(InstanceScanner.getInstance().readRow());
            switch (input) {
                case 1: {
                    loadFiles(path);
                    break;
                }
                case 2: {
                    stop();
                    break;
                }
                default:
                    System.out.println("Choose right option!");
            }
        } catch (InputMismatchException ex) {
            System.out.println("Input must be number!");
        }
    }

    private boolean loadFiles(String path) throws IllegalArgumentException {
        return fileReaderService.checkCorrectPathAndType(path);
    }

    private Map<String, List<String>> readFiles(String inputPath, String patternsPath) {
        Map<String, List<String>> files = new HashMap<>();
        files.put(INPUT_KEY, fileReaderService.readRows(inputPath));
        files.put(PATTERN_KEY, fileReaderService.readRows(patternsPath));
        return files;
    }

    private void printResult(List<String> result) {
        if (result.isEmpty()) {
            System.out.println("zero matches");
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
