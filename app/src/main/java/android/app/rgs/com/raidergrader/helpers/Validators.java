package android.app.rgs.com.raidergrader.helpers;

import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.util.Patterns;

public class Validators {
    /**
     * Returns true if @inputEmail is a valid email address
     */
    public static boolean validateEmail(String inputEmail) {
        String email = inputEmail.trim();
        return !(email.isEmpty() || !isValidEmail(email));
    }

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    /**
     * Returns true if @inputPassword is a valid password according to our application rules
     */
    public static boolean validatePassword(String inputPassword) {
        return inputPassword.length() >= 6 && StringUtils.containsDigit(inputPassword) &&
                StringUtils.containsLowerCase(inputPassword) &&
                StringUtils.containsUpperCase(inputPassword) &&
                StringUtils.notContainsAlphaNumeric(inputPassword);
    }

    /**
     * Returns true if @inputText is non-empty and false otherwise.
     *
     * @param inputText String to validate
     */
    public static boolean validateNonEmptyText(String inputText) {
        return !inputText.isEmpty();
    }

    /**
     * Returns true if the two strings are equal
     *
     * @param first
     * @param second
     * @return
     */
    public static boolean validateTextEquality(String first, String second) {
        return first.equals(second);
    }

    /**
     * Returns true if the input string is a valid integer
     *
     * @param inputNumber
     * @return
     */
    public static boolean validateInteger(String inputNumber) {
        try {
            int result = Integer.parseInt(inputNumber);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    /**
     * Returns true if the associated TextView has no errors according
     * to our validation rules
     *
     * @param til
     * @return
     */
    public static boolean hasNoError(TextInputLayout til) {
        return til.getError() == "";
    }
}