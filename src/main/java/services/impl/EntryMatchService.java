package services.impl;

import services.MatchingCheckService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Roman Nagibov
 */
public class EntryMatchService implements MatchingCheckService {

    @Override
    public List<String> match(List<String> inputList, List<String> patternList) {
        List<String> matchesString = new ArrayList<>();
        for (String input : inputList) {
            for (String pattern : patternList) {
                if (input.contains(pattern)) {
                    matchesString.add(input);
                }
            }
        }

        return matchesString;
    }

}
