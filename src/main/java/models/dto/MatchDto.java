package models.dto;

import lombok.Data;
import models.MatchingEnum;
import property.RuntimeProperty;

import java.util.List;

/**
 * @author Roman Nagibov
 */
@Data
public class MatchDto  {
private static MatchDto matchDtoInstance;
    private MatchingEnum matchingMode;
    private List<String> inputFile;
    private List<String> patternsFile;
    private List<String> resultList;

    private MatchDto() {
    };

    public static synchronized MatchDto getInstance() {
        if (matchDtoInstance == null) {
            matchDtoInstance = new MatchDto();
        }
        return matchDtoInstance;
    }
}
