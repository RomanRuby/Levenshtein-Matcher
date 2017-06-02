package services.impl;


import services.MatchingCheckService;

import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Created by Roman Nagibov
 */
public class FullMatchService implements MatchingCheckService {

    @Override
    public List<String> match(List<String> inputList, List<String> patternList) {
        return inputList.stream().filter(patternList::contains).collect(toList());
    }

}
