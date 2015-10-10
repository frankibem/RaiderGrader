package android.app.rgs.com.raidergrader.helpers;

/**
 * Created by Frank Ibem on 10/10/2015.
 */
public class StringUtils {
    /**
     * Returns true if the source string contains an uppercase letter
     * @param source
     * @return
     */
    public static boolean containsUpperCase(String source) {
        for (char ch : source.toCharArray()) {
            if (Character.isUpperCase(ch)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if the source string contains a lowercase letter
     * @param source
     * @return
     */
    public static boolean containsLowerCase(String source) {
        for (char ch : source.toCharArray()) {
            if (Character.isLowerCase(ch)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if the source string contains a digit
     * @param source
     * @return
     */
    public static boolean containsDigit(String source) {
        for (char ch : source.toCharArray()) {
            if (Character.isDigit(ch)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if the source string contains a character that is not alphanumeric
     * @param source
     * @return
     */
    public static boolean notContainsAlphaNumeric(String source) {
        for (char ch : source.toCharArray()) {
            if (!Character.isLetterOrDigit(ch)) {
                return true;
            }
        }
        return false;
    }
}