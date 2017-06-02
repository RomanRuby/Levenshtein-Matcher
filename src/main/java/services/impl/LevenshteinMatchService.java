package services.impl;

import org.apache.commons.lang3.StringUtils;
import services.MatchingCheckService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Created by Roman Nagibov
 */
public class LevenshteinMatchService implements MatchingCheckService {

    @Override
    public List<String> match(List<String> inputList, List<String> patternList) {
        List<String> matchesString = new ArrayList<>();
        for (String pattern : patternList) {
             matchesString.addAll(inputList.stream()
                     .filter(input -> StringUtils.getLevenshteinDistance(input, pattern) <= 1)
                     .collect(Collectors.toList()));
        }
        return matchesString;
    }

}
