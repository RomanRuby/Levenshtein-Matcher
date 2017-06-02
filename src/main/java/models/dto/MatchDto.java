package models.dto;

import ExceptionMenu.DefaultExceptionMenu;
import lombok.Data;
import models.MatchingEnum;

import java.util.List;

/**
 * @author Roman Nagibov
 */
@Data
public class MatchDto extends DefaultExceptionMenu {

    private MatchingEnum selected;
    private List<String> resultList;

}
