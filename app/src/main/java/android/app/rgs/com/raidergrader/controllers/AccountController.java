package android.app.rgs.com.raidergrader.controllers;

import android.app.Activity;
import android.app.ProgressDialog;
import android.app.rgs.com.raidergrader.activities.LoginActivity;
import android.app.rgs.com.raidergrader.activities.StudentClassListActivity;
import android.app.rgs.com.raidergrader.data_access.HttpStatusCodes;
import android.app.rgs.com.raidergrader.data_access.Repository;
import android.app.rgs.com.raidergrader.data_access.RequestError;
import android.app.rgs.com.raidergrader.data_access.RestTask;
import android.app.rgs.com.raidergrader.data_access.RestUtil;
import android.app.rgs.com.raidergrader.helpers.GlobalHandling;
import android.app.rgs.com.raidergrader.models.ControllerCallback;
import android.app.rgs.com.raidergrader.models.RegisterModel;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Pair;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Frank Ibem on 10/16/2015.
 */
public class AccountController {
    private Activity activity;
    private ControllerCallback controllerCallback;
    private ProgressDialog mProgress;

    /**
     * Creates an AccountController
     * Frank Ibem
     * @param activity Activity to be used by the controller
     * @param callback Callback to be notified when results are received
     */
    public AccountController(Activity activity, ControllerCallback callback) {
        this.activity = activity;
        this.controllerCallback = callback;
    }

    /**
     * Register a user
     *
     * @param model Model containing the users details
     */
    public void RegisterUser(RegisterModel model) {
        Gson gson = new Gson();
        String request = gson.toJson(model);

        RestTask.ResponseCallback responseCallback = new RestTask.ResponseCallback() {
            @Override
            public void onRequestSuccess(String response) {
                if (mProgress != null) {
                    mProgress.dismiss();
                }

                Intent intent = new Intent(activity, LoginActivity.class);
                activity.startActivity(intent);

                Toast.makeText(activity, "Account created", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRequestError(RequestError error) {
                if (mProgress != null) {
                    mProgress.dismiss();
                }

                if (error.getStatusCode() == HttpStatusCodes.BadRequest) {
                    GlobalHandling.makeShortToast(activity, "Please review your input");
                } else {
                    GlobalHandling.generalError(activity, error);
                }
            }
        };
        RestTask.ProgressCallback progressCallback = new RestTask.ProgressCallback() {
            @Override
            public void onProgressUpdate(int progress) {
            }
        };

        try {
            RestTask task = RestUtil.obtainJSONPostTask(Repository.baseUrl + "api/Account/Register", request);
            task.setProgressCallback(progressCallback);
            task.setResponseCallback(responseCallback);
            task.execute();

            mProgress = ProgressDialog.show(activity, "Loading", "Creating your account", true);
        } catch (Exception ex) {
            responseCallback.onRequestError(new RequestError(HttpStatusCodes.Incomplete, ex.getMessage()));
        }
    }

    /**
     * Logs the user into the application
     *
     * @param email    User's email address
     * @param password User's password
     */
    public void LogUserIn(String email, String password) {
        ArrayList<Pair<String, String>> formBody = new ArrayList<>();
        formBody.add(new Pair<>("password", password));
        formBody.add(new Pair<>("grant_type", "password"));
        formBody.add(new Pair<>("username", email));

        RestTask.ResponseCallback responseCallback = new RestTask.ResponseCallback() {
            @Override
            public void onRequestSuccess(String response) {
                if (mProgress != null) {
                    mProgress.dismiss();
                }

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Repository.ACCESS_TOKEN = jsonObject.getString("access_token");
                    Repository.USERNAME = jsonObject.getString("userName");

                    saveUserCredentials(Repository.USERNAME, Repository.ACCESS_TOKEN);
                    Intent intent = new Intent(activity, StudentClassListActivity.class);
                    activity.startActivity(intent);
                    Toast.makeText(activity, "Login successful", Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    //TODO: Consider carefully
                    onRequestError(new RequestError(HttpStatusCodes.Incomplete, e.getMessage()));
                }
            }

            @Override
            public void onRequestError(RequestError error) {
                if (mProgress != null) {
                    mProgress.dismiss();
                }

                if (error.getStatusCode() == HttpStatusCodes.BadRequest ||
                        error.getStatusCode() == HttpStatusCodes.Unauthorized) {
                    GlobalHandling.makeShortToast(activity, "Incorrect username or password");
                } else {
                    GlobalHandling.generalError(activity, error);
                }
            }
        };
        RestTask.ProgressCallback progressCallback = new RestTask.ProgressCallback() {
            @Override
            public void onProgressUpdate(int progress) {
            }
        };

        try {
            RestTask loginTask = RestUtil.obtainLoginTask(Repository.baseUrl + "Token", formBody);
            loginTask.setResponseCallback(responseCallback);
            loginTask.setProgressCallback(progressCallback);
            loginTask.execute();

            mProgress = ProgressDialog.show(activity, "Loading", "Logging you in...", true);
        } catch (Exception ex) {
            responseCallback.onRequestError(new RequestError(HttpStatusCodes.Incomplete, ex.getMessage()));
        }
    }

    /**
     * Logs the user out of the application and deletes his/her credentials
     */
    public void LogUserOut() {
        SharedPreferences settings = activity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.remove(Repository.ACCESS_TOKEN_KEY);
        editor.remove(Repository.USERNAME_KEY);
        editor.commit();
    }

    /**
     * Store the user's credentials for next use
     *
     * @param username The user's username
     * @param token    The token received from the web endpoint
     */
    private void saveUserCredentials(String username, String token) {
        Repository.ACCESS_TOKEN = token;
        Repository.USERNAME = username;

        SharedPreferences settings = activity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(Repository.ACCESS_TOKEN_KEY, Repository.ACCESS_TOKEN);
        editor.putString(Repository.USERNAME_KEY, Repository.USERNAME);
        editor.commit();
    }
}