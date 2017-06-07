package models;

import services.MatchingCheckService;
import services.impl.EntryMatchService;
import services.impl.FullMatchService;
import services.impl.LevenshteinMatchService;

/**
 * Created by Roman Nagibov
 */
public enum MatchingEnum {

    FullMatchMode {
        public MatchingCheckService getMode() {

            return new FullMatchService();
        }
    },
    EntryMatchMode {
        public MatchingCheckService getMode() {

            return new EntryMatchService();
        }
    },
    LevenshteinMatchMode {
        public MatchingCheckService getMode() {

            return new LevenshteinMatchService();
        }
    };

    public abstract MatchingCheckService getMode();
}
