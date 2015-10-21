package android.app.rgs.com.raidergrader.data_access;

import android.app.rgs.com.raidergrader.models.ClassModel;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.List;

public class Repository {
    //TODO: Change to https in final application
    public static final String baseUrl = "http://raidergrader.azurewebsites.net/";
    public static final String PREFERENCES_KEY = "com.raidergrader.settings";

    public static final String ACCESS_TOKEN_KEY = "ACCESS_TOKEN";
    public static String ACCESS_TOKEN = null;

    public static final String USERNAME_KEY = "USERNAME";
    public static String USERNAME = "";

    public static List<ClassModel> studentClasses;
    public static ClassModel selectedEnrollClass;

    /**
     * Saves the users credentials locally
     *
     * @param username
     * @param token
     * @param context
     */
    public static void saveUserCredentials(String username, String token, Context context) {
        ACCESS_TOKEN = token;
        USERNAME = username;

        SharedPreferences settings = context.getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(Repository.ACCESS_TOKEN_KEY, Repository.ACCESS_TOKEN);
        editor.putString(Repository.USERNAME_KEY, Repository.USERNAME);
        editor.commit();
    }

    /**
     * Clears the local copy of the user's credentials
     *
     * @param context
     */
    public static void clearUserCredentials(Context context) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.remove(Repository.ACCESS_TOKEN_KEY);
        editor.remove(Repository.USERNAME_KEY);
        editor.commit();
    }
}