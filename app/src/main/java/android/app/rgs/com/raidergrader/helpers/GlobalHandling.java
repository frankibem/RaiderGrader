package android.app.rgs.com.raidergrader.helpers;

import android.app.rgs.com.raidergrader.activities.LoginActivity;
import android.app.rgs.com.raidergrader.data_access.HttpStatusCodes;
import android.app.rgs.com.raidergrader.data_access.RequestError;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by Frank Ibem on 10/10/2015.
 */
public class GlobalHandling {
    /**
     * Creates and displays a Toast with the given text
     *
     * @param context
     * @param text
     */
    public static void makeShortToast(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    /**
     * Display a Toast and navigate to the Login Page if an authorization error occurs
     *
     * @param context
     */
    public static void authorizationError(Context context) {
        makeShortToast(context, "Your login has expired");

        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    /**
     * Display a Toast if an unknown error occurs.
     *
     * @param context
     */
    public static void unknownError(Context context) {
        makeShortToast(context, "An unknown error occurred. Please try again later");
    }

    /**
     * Display a Toast if a connection error occurs
     *
     * @param context
     */
    public static void connectionError(Context context) {
        makeShortToast(context, "Could not connect to the internet. Some features may be unusable until you reconnect");
    }

    public static void generalError(Context context, RequestError error) {
        if (error.getStatusCode() == HttpStatusCodes.BadRequest) {
            badRequest(context);
        } else if (error.getStatusCode() == HttpStatusCodes.Incomplete) {
            connectionError(context);
        } else if (error.getStatusCode() == HttpStatusCodes.Unauthorized) {
            authorizationError(context);
        } else {
            unknownError(context);
        }
    }

    /**
     * Display a Toast if the server returns a Bad Request due to a binding error or some other error
     *
     * @param context
     */
    public static void badRequest(Context context) {
        makeShortToast(context, "An error occurred while fetching your data");
    }
}