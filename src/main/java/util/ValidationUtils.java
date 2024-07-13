package util;

import static constant.AppConstants.*;

public class ValidationUtils {

    private ValidationUtils() {
    }

    public static boolean isValidSession(String value) {
        return value.matches(INPUT_VALIDATION_REGEX);
    }

    public static boolean isValidTimestamp(String timeString) {
        return timeString.matches(TIME_REGEX);
    }

    public static boolean isValidUsername(String username) {
        return username.matches(USERNAME_REGEX);
    }

    public static boolean isValidIndicator(String indicator) {
        return indicator.equals(START_INDICATOR) || indicator.equals(END_INDICATOR);
    }
}
