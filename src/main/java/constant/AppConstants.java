package constant;

public class AppConstants {
    private AppConstants() {
    }
    public static final String TIME_REGEX = "^([01]?[0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])$";

    //I took help from the internet to write this regex
    public static final String INPUT_VALIDATION_REGEX = "^\\s*(([01]?[0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9]))\\s+(\\w+)\\s+(Start|End)\\s*$";
    public static final String USERNAME_REGEX = "^\\w+$";
    public static final String START_INDICATOR = "Start";
    public static final String END_INDICATOR = "End";
}