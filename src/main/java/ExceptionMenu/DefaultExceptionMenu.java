package ExceptionMenu;

import displays.Menu;
import displays.MenuSettings;
import displays.ViewDisplay;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

/**
 * @author Roman Nagibov
 */
public class DefaultExceptionMenu extends Menu {

   public void exceptionMode() throws ExecutionException, InterruptedException, IOException {
        ViewDisplay viewDisplay = new ViewDisplay();
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
                viewDisplay.mainRealization();
                break;
            }
            case 2: {
                viewDisplay.demonstrate();
                break;
            }
            default:
                exceptionMode();
        }
    }


}
