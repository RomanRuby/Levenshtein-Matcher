package displays;


/**
 * Created by Roman Nagibov
 */
public enum MenuSettings {

    MODE_MENU {
        @Override
        public String toString() {
            return " "
                    + "\nEnter your option : "
                    + "\n 1 - mode output all the lines from input.txt that match exactly any pattern " +
                    "in patterns.txt"
                    + "\n 2 - mode output all the lines from input.txt that contain a match from " +
                    "patterns.txt somewhere in the line."
                    + "\n 3 - mode output all the lines from input.txt that contain a match with " +
                    "edit distance <= 1 patterns.txt"
                    + "\n 4 - end"
                    + "\n Input number:";
        }
    },
    RESULT_MENU {
        @Override
        public String toString() {
            return " "
                    + "\n Enter your option : "
                    + "\n Press 1 - select mode "
                    + "\n Press 1 - select files "
                    + "\n 2 - end"
                    + "\n Input number:";
        }
    },
    FILES_MENU {
        @Override
        public String toString() {
            return " "
                    + "\n Enter your option : "
                    + "\n Press 1 - choose files "
                    + "\n 2 - end"
                    + "\n Input number:";
        }
    },
    EXCEPTION_MENU {
        @Override
        public String toString() {
            return " "
                    + "\n Enter your option : "
                    + "\n Press 1 - retry "
                    + "\n 2 - end"
                    + "\n Input number:";
        }
    }

}
