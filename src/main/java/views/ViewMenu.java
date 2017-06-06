package views;

import models.MatchingEnum;
import scanners.CustomScanner;
import services.FileReaderService;
import services.impl.DefaultFileReaderService;

import java.util.*;
import java.util.concurrent.*;

/**
 * Created by Roman Nagibov
 */
public class ViewMenu {

    private static final String INPUT_KEY = "input";
    private static final String PATTERN_KEY = "pattern";
    private static ViewMenu viewDisplay;
    private FileReaderService fileReaderService = new DefaultFileReaderService();
    private boolean run = true;


    private ViewMenu() {}

    public static ViewMenu getInstance() {
        if (null == viewDisplay) {
            viewDisplay = new ViewMenu();
        }
        return viewDisplay;
    }

    public void getMainMenu() {
        while (run) {
            getFilesMenu();
        }
    }

    private void getFilesMenu() {
        Scanner scanner = fixScannerThread();
        System.out.println(MenuTemplate.FILES_MENU.toString());
        try {
            int input = scanner.nextInt();

            switch (input) {
                case 1: {
                    getModeMenu(loadFiles());
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
        } catch (InputMismatchException ex) {
            System.out.println("Input must be number!");
            getFilesMenu();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            getFilesMenu();
        }
    }

    private void getModeMenu(Map<String, List<String>> files) {
        Scanner scanner = fixScannerThread();
        System.out.println(MenuTemplate.MODE_MENU.toString());
        try {
            int input = scanner.nextInt();
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
                    printResult(getResult(MatchingEnum.LevensteinMatchMode, files));
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
        Scanner scanner = fixScannerThread();
        System.out.println(MenuTemplate.RESULT_MENU.toString());
        try {
            int input = scanner.nextInt();
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


    private Map<String, List<String>> loadFiles() {
        System.out.println("Enter location input file with type txt");
        String inputFilePath = fixScannerThread().nextLine();
        fileReaderService.checkCorrectPathAndType(inputFilePath);

        System.out.println("Enter location patterns file with type txt");
        String patternsFilePath = fixScannerThread().nextLine();
        fileReaderService.checkCorrectPathAndType(patternsFilePath);

        Map<String, List<String>> files = new HashMap<>();

        files.put(INPUT_KEY, fileReaderService.readRows(inputFilePath));
        files.put(PATTERN_KEY, fileReaderService.readRows(patternsFilePath));

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
        run = false;
    }

    public Scanner fixScannerThread()  {
        ExecutorService executor = Executors.newFixedThreadPool(1);
        Future<Scanner> future = executor.submit(processingSearchMessage);
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Callable<Scanner> processingSearchMessage = () -> CustomScanner.getInstance().getScanner();
}
