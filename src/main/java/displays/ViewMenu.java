package displays;

import property.RuntimeProperty;

/**
 * Created by Roman Nagibov
 */
public class ViewMenu {
    private RuntimeProperty property = RuntimeProperty.getInstance();

    public void viewResult() {
        ViewDisplay viewDisplay = new ViewDisplay();
        while (!(property.isStopCommand())) {
            if (property.isSelectedExceptionPoint()) {
                viewDisplay.inputExceptionMode();
                continue;
            }
            if (!property.isSelectFilesPoint()) {
                viewDisplay.inputFiles();
                continue;
            }
            if (!property.isSelectModePoint()) {
                viewDisplay.inputMode();
                continue;
            }
            if (!property.isSelectedResultPoint()) {
                viewDisplay.inputResultMode();
            }
        }
    }

}
