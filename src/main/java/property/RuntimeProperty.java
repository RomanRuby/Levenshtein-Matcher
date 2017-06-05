package property;

import lombok.Data;
import models.dto.MatchDto;


/**
 * Created by Roman Nagibov
 */
@Data
public class RuntimeProperty {

    private static RuntimeProperty instance;
    private boolean isSelectFilesPoint = false;
    private boolean isSelectModePoint = false;
    private boolean isSelectedResultPoint = false;
    private boolean isSelectedExceptionPoint = false;
    private boolean stopCommand = false;

    private RuntimeProperty() {
    };

    public static synchronized RuntimeProperty getInstance() {
        if (instance == null) {
            instance = new RuntimeProperty();
        }
        return instance;
    }

    public void initStopCommand() {
        this.stopCommand = true;
    }

}
