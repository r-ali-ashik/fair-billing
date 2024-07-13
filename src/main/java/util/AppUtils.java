package util;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;

public class AppUtils {

    private AppUtils() {
    }

    public static boolean isEmpty(CharSequence charSequence) {
        return Objects.isNull(charSequence) || charSequence.length() == 0;
    }

    public static boolean isEmpty(String value) {
        return isNull(value) || value.isEmpty();
    }

    public static boolean isEmpty(Map<?, ?> map) {
        return isNull(map) || map.isEmpty();
    }

    public static boolean isEmpty(Collection collection) {
        return isNull(collection) || collection.isEmpty();
    }

    public static boolean isNull(Object object) {
        return Objects.isNull(object);
    }

    public static String getFileName(String[] args) {
        String fileName = null;
        for (String arg : args) {
            if (arg.endsWith(".txt")) {
                fileName = arg;
                break;
            }
        }
        return fileName;
    }
}
