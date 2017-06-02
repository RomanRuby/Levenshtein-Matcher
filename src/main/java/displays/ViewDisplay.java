package displays;

import ExceptionMenu.DefaultExceptionMenu;
import models.MatchingEnum;
import models.dto.MatchDto;
import services.FileReaderService;
import services.impl.DefaultFileReaderService;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

/**
 * Created by Roman Nagibov
 */
public class ViewDisplay extends Menu{

    private FileReaderService fileReaderService;
    private Boolean stopCommand = false;
    private List<String> inputTXT;
    private List<String> patternsTXT;


    public ViewDisplay() {
        fileReaderService = new DefaultFileReaderService();
    }

    private MatchDto inputMode() throws ExecutionException, InterruptedException, IOException {
        MatchDto matchDto = new MatchDto();
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
                matchDto.setSelected(MatchingEnum.FullMatchMode);
                matchDto.setResultList(MatchingEnum.FullMatchMode.getMode().match(inputTXT, patternsTXT));
                break;
            }
            case 2: {
                matchDto.setSelected(MatchingEnum.EntryMatchMode);
                matchDto.setResultList(MatchingEnum.EntryMatchMode.getMode().match(inputTXT, patternsTXT));
                break;
            }
            case 3: {
                matchDto.setSelected(MatchingEnum.LevensteinMatchMode);
                matchDto.setResultList(MatchingEnum.LevensteinMatchMode.getMode().match(inputTXT, patternsTXT));
                break;
            }

            default:
                new DefaultExceptionMenu().exceptionMode();
        }
        return matchDto;
    }

    public void demonstrate() throws InterruptedException, ExecutionException, IOException {
        Scanner scanner = null;
        try {
            scanner = fixScannerThread();
        } catch (ExecutionException | InterruptedException e) {
            System.out.println("problem with console");
            System.exit(0);
        }

        while (!stopCommand) {
            System.out.println(MenuSettings.RESULT_MENU.toString());
            int input;
            try {
                input = scanner.nextInt();
            } catch (InputMismatchException ex) {
                input = 0;
            }
            switch (input) {
                case 1: {
                    mainRealization();
                    break;
                }
                case 2: {
                    initStopCommand();
                    System.out.println("Stop");
                    break;
                }
                default:
                    System.out.println("Choose right option:");
                    demonstrate();
                    break;
            }
        }
    }

    public void mainRealization() throws ExecutionException, InterruptedException, IOException {
            System.out.println("Command-line Pattern matching program :");
            System.out.println("Enter location input file with type txt");
            String inputFile = fixScannerThread().nextLine();

            System.out.println("Enter location patterns file with type txt");
            String patternsFile = fixScannerThread().nextLine();

            if (!(fileReaderService.checkCorrectPathAndType(inputFile) || fileReaderService.checkCorrectPathAndType(patternsFile))) {
                System.out.println("Files doesn't exist or type incorrect");
                return;
            }

            inputTXT = fileReaderService.readRow(inputFile);
            patternsTXT = fileReaderService.readRow(patternsFile);

            MatchDto matchDto = inputMode();
            outputResult(matchDto);

    }

    private void outputResult(MatchDto matchDto) {
        System.out.println("Version mode = " + matchDto.getSelected());
        List<String> resultList = matchDto.getResultList();

        if (resultList.isEmpty()) {
            System.out.println("zero matches");
        } else {
            for (String printString : resultList) {
                System.out.println(printString);
            }
        }
    }

    private void initStopCommand() {
        this.stopCommand = true;
    }

}
