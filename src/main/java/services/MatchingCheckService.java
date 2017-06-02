package services;

import java.util.List;

/**
 * Created by Roman Nagibov
 */

public interface MatchingCheckService {

    List<String> match(List<String> inputList, List<String> patternList);

}
