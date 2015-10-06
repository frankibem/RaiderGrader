package android.app.rgs.com.raidergrader.helpers;

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
        return !inputPassword.isEmpty();
    }

    /**
     * Returns true if @inputText is non-empty and false otherwise.
     *
     * @param inputText String to validate
     */
    public static boolean validateNonEmptyText(String inputText) {
        return !inputText.isEmpty();
    }
}