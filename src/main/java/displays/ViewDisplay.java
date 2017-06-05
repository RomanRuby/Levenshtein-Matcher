package displays;

import property.RuntimeProperty;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * Created by Roman Nagibov
 */
public class ViewDisplay extends Menu {
    private RuntimeProperty property = RuntimeProperty.getInstance();

    public void viewResult()  {
        ViewMenu viewMenu = new ViewMenu();
        while (!(property.isStopCommand())) {
            if (property.isSelectedExceptionPoint()){
                viewMenu.inputExceptionMode();
                continue;
            }
            if (!property.isSelectFilesPoint()) {
                viewMenu.inputFiles();
                continue;
            }
            if (!property.isSelectModePoint() ) {
                viewMenu.inputMode();
              continue;
            }

            if (property.isSelectedResultPoint()){
                viewMenu.inputResultMode();
            }
        }
    }

}
