package displays;

import models.MatchingEnum;
import models.dto.MatchDto;
import property.RuntimeProperty;
import services.FileReaderService;
import services.impl.DefaultFileReaderService;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.*;

/**
 * Created by Roman Nagibov
 */
public class ViewMenu {
    private RuntimeProperty property = RuntimeProperty.getInstance();
    private MatchDto matchDto = MatchDto.getInstance();
    private FileReaderService fileReaderService = new DefaultFileReaderService();

    public void inputMode()  {

        Scanner scanner = fixScannerThread();
        System.out.println(MenuSettings.MODE_MENU.toString());
        int input;
        try {
            input = scanner.nextInt();
        } catch (InputMismatchException ex) {
            input = 0;
        }
        switch (input) {
            case 1: {
                property.setSelectModePoint(true);
                matchDto.setMatchingMode(MatchingEnum.FullMatchMode);
                matchDto.setResultList(MatchingEnum.FullMatchMode.getMode().match(matchDto.getInputFile(), matchDto.getPatternsFile()));
                outputResult();
                break;
            }
            case 2: {
                property.setSelectModePoint(true);
                matchDto.setMatchingMode(MatchingEnum.EntryMatchMode);
                matchDto.setResultList( MatchingEnum.EntryMatchMode.getMode().match(matchDto.getInputFile(), matchDto.getPatternsFile()));
                outputResult();
                break;
            }
            case 3: {
                property.setSelectModePoint(true);
                matchDto.setMatchingMode(MatchingEnum.LevensteinMatchMode);
                matchDto.setResultList( MatchingEnum.LevensteinMatchMode.getMode().match(matchDto.getInputFile(), matchDto.getPatternsFile()));
                outputResult();
                break;
            }
            case 4: {
                property.initStopCommand();
                break;
            }


            default:
                property.setSelectedExceptionPoint(true);
        }
    }
    public void inputFiles()  {

        Scanner scanner = fixScannerThread();
        System.out.println(MenuSettings.FILES_MENU.toString());
        int input;
        try {
            input = scanner.nextInt();
        } catch (InputMismatchException ex) {
            input = 0;
        }
        switch (input) {
            case 1: {
                System.out.println("Command-line Pattern matching program :");
                property.setSelectFilesPoint(true);
                filesSet();
                break;
            }
            case 2: {
                property.initStopCommand();
                break;
            }
            default:
                property.setSelectFilesPoint(false);
                property.setSelectedExceptionPoint(true);
        }
    }

    public void inputResultMode()  {

        Scanner scanner = fixScannerThread();
        System.out.println(MenuSettings.RESULT_MENU.toString());
        int input;
        try {
            input = scanner.nextInt();
        } catch (InputMismatchException ex) {
            input = 0;
        }
        switch (input) {
            case 1: {
                property.setSelectedResultPoint(true);
                property.setSelectModePoint(false);
                break;
            }
            case 2: {
                property.setSelectedResultPoint(true);
                property.setSelectModePoint(false);
                property.setSelectFilesPoint(false);
                break;
            }
            case 3: {
                property.initStopCommand();
                break;
            }
            default:
                property.setSelectedResultPoint(false);
                property.setSelectedExceptionPoint(true);
        }
    }
    public void inputExceptionMode() {

        Scanner scanner = fixScannerThread();
        System.out.println(MenuSettings.EXCEPTION_MENU.toString());
        int input;
        try {
            input = scanner.nextInt();
        } catch (InputMismatchException ex) {
            input = 0;
        }
        switch (input) {
            case 1: {
                property.setSelectedExceptionPoint(false);

                break;
            }
            case 2: {
                property.initStopCommand();
                break;
            }
            default:
                property.setSelectedExceptionPoint(false);
        }
    }

    private Scanner fixScannerThread()  {
        ExecutorService executor = Executors.newFixedThreadPool(1);
        Future<Scanner> future = executor.submit(processingSearchMessage);
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            System.out.println("");
        }
        return null;
    }

    private Callable<Scanner> processingSearchMessage = () -> new Scanner(System.in);

    private void filesSet() {

        System.out.println("Enter location input file with type txt");
        String inputFile = fixScannerThread().nextLine();
        System.out.println("Enter location patterns file with type txt");
        String patternsFile = fixScannerThread().nextLine();
        if (!(fileReaderService.checkCorrectPathAndType(inputFile) )) {
            System.out.println("Files doesn't exist or type incorrect");
            property.setSelectedExceptionPoint(true);
            property.setSelectFilesPoint(false);
            return;
           }
        matchDto.setInputFile(fileReaderService.readRow(inputFile));
        matchDto.setPatternsFile(fileReaderService.readRow(patternsFile));
    }

    private void outputResult() {
        System.out.println("Version mode = " + matchDto.getMatchingMode());
        List<String> resultList = matchDto.getResultList();

        if (resultList.isEmpty()) {
            System.out.println("zero matches");
        } else {
            for (String printString : resultList) {
                System.out.println(printString);
            }
        }
    }

}
