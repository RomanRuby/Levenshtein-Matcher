package views;


/**
 * Created by Roman Nagibov
 */
public enum MenuTemplate {

    GREETING {
        @Override
        public String toString() {
            return "Command-line Pattern matching program.";
        }
    },
    MODE_MENU {
        @Override
        public String toString() {
            return " "
                    + "\nEnter your option : "
                    + "\n 1 - mode output all the rows from input file that match exactly any row in pattern file "
                    + "\n 2 - mode output all the lines from input.txt that contain a match from "
                    + "patterns.txt somewhere in the line."
                    + "\n 3 - mode output all the lines from input.txt that contain a match with "
                    + "edit distance <= 1 patterns.txt"
                    + "\n 4 - exit"
                    + "\n Input number:";
        }

    },
    RESULT_MENU {
        @Override
        public String toString() {
            return " "
                    + "\n Enter your option : "
                    + "\n 1 - select different mode "
                    + "\n 2 - select new files "
                    + "\n 3 - exit"
                    + "\n Input number:";
        }
    },
    FILES_MENU {
        @Override
        public String toString() {
            return " "
                    + "\n Enter your option : "
                    + "\n 1 - choose files "
                    + "\n 2 - exit"
                    + "\n Input number:";
        }
    },
    FILES_PATH_MENU {
        @Override
        public String toString() {
            return " "
                    + "\n Enter your option : "
                    + "\n 1 - input file location"
                    + "\n 2 - exit"
                    + "\n Input number:";
        }
    }

}
