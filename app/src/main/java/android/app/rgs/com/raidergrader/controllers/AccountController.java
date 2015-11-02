package android.app.rgs.com.raidergrader.controllers;

import android.app.Activity;
import android.app.ProgressDialog;
import android.app.rgs.com.raidergrader.activities.LoginActivity;
import android.app.rgs.com.raidergrader.activities.student.StudentClassListActivity;
import android.app.rgs.com.raidergrader.activities.teacher.TeacherClassHomeActivity;
import android.app.rgs.com.raidergrader.data_access.HttpStatusCodes;
import android.app.rgs.com.raidergrader.data_access.Repository;
import android.app.rgs.com.raidergrader.data_access.RequestError;
import android.app.rgs.com.raidergrader.data_access.RestTask;
import android.app.rgs.com.raidergrader.data_access.RestUtil;
import android.app.rgs.com.raidergrader.utilities.GlobalHandling;
import android.app.rgs.com.raidergrader.models.ControllerCallback;
import android.app.rgs.com.raidergrader.models.RegisterModel;
import android.content.Intent;
import android.util.Pair;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Controller for account related activities
 *
 * @author Frank ibem
 */
public class AccountController {
    private final Activity activity;
    private final ControllerCallback controllerCallback;
    private ProgressDialog mProgress;

    /**
     * Creates an AccountController
     *
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

                Toast.makeText(activity, "Account created", Toast.LENGTH_SHORT).show();
                controllerCallback.DisplayResult(null);
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
                    String accessToken = jsonObject.getString("access_token");
                    String username = jsonObject.getString("userName");

                    Repository.saveUserCredentials(username, accessToken, activity);
                    controllerCallback.DisplayResult("success");
                } catch (JSONException e) {
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
     * Determines the user's role and navigates to the appropriate activity
     */
    public void DetermineUserRole() {
        RestTask.ResponseCallback responseCallback = new RestTask.ResponseCallback() {
            @Override
            public void onRequestSuccess(String response) {
                if (mProgress != null) {
                    mProgress.dismiss();
                }

                Intent intent;
                if (response.toLowerCase().contains("teacher")) {
                    intent = new Intent(activity, TeacherClassHomeActivity.class);
                } else if (response.toLowerCase().contains("student")) {
                    intent = new Intent(activity, StudentClassListActivity.class);
                } else {
                    Toast.makeText(activity, "You cannot use that account with this application", Toast.LENGTH_SHORT).show();
                    return;
                }

                activity.startActivity(intent);
                Toast.makeText(activity, "Login successful", Toast.LENGTH_SHORT).show();
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
            RestTask task = RestUtil.obtainGetTask(Repository.baseUrl + "api/Account/Role");
            task.setProgressCallback(progressCallback);
            task.setResponseCallback(responseCallback);
            task.execute();
        } catch (Exception ex) {
            responseCallback.onRequestError(new RequestError(HttpStatusCodes.Incomplete, ex.getMessage()));
        }
    }

    /**
     * Logs the user out of the application and deletes his/her credentials
     */
    public void LogUserOut() {
        Repository.clearUserCredentials(activity);
        Toast.makeText(activity, "You've been logged out", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(activity, LoginActivity.class);
        activity.startActivity(intent);
    }
}