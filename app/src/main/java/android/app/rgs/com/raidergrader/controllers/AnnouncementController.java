package android.app.rgs.com.raidergrader.controllers;

import android.app.Activity;
import android.app.ProgressDialog;
import android.app.rgs.com.raidergrader.data_access.HttpStatusCodes;
import android.app.rgs.com.raidergrader.data_access.Repository;
import android.app.rgs.com.raidergrader.data_access.RequestError;
import android.app.rgs.com.raidergrader.data_access.RestTask;
import android.app.rgs.com.raidergrader.data_access.RestUtil;
import android.app.rgs.com.raidergrader.helpers.GlobalHandling;
import android.app.rgs.com.raidergrader.helpers.JsonHelpers;
import android.app.rgs.com.raidergrader.models.AnnouncementModel;
import android.app.rgs.com.raidergrader.models.ControllerCallback;
import android.app.rgs.com.raidergrader.models.CreateAnnouncementModel;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by Frank Ibem on 10/16/2015.
 */
public class AnnouncementController {
    private Activity activity;
    private ControllerCallback controllerCallback;
    private ProgressDialog mProgress;

    /**
     * Creates an AccountController
     *
     * @param activity Activity to be used by the controller
     * @param callback Callback to be notified when results are received
     */
    public AnnouncementController(Activity activity, ControllerCallback callback) {
        this.activity = activity;
        this.controllerCallback = callback;
    }

    /*
    * Lauren Joness
    * Creates a new announcement in a class
    * */
    public void CreateAnnouncement(CreateAnnouncementModel model) {
        Gson gson = JsonHelpers.getGson();
        String request = gson.toJson(model);

        RestTask.ResponseCallback responseCallback = new RestTask.ResponseCallback() {
            @Override
            public void onRequestSuccess(String response) {
                if (mProgress != null) {
                    mProgress.dismiss();
                }

                Toast.makeText(activity, "Announcement successfully created!", Toast.LENGTH_SHORT).show();
                controllerCallback.DisplayResult(null);
            }

            @Override
            public void onRequestError(RequestError error) {
                if (mProgress != null) {
                    mProgress.dismiss();
                }

                // TODO: Add appropriate error handling later
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
            RestTask task = RestUtil.obtainJSONPostTask(Repository.baseUrl + "api/Announcements", request);
            task.setProgressCallback(progressCallback);
            task.setResponseCallback(responseCallback);
            task.execute();

            mProgress = ProgressDialog.show(activity, "Loading", "Creating announcement", true);
        } catch (Exception ex) {
            responseCallback.onRequestError(new RequestError(HttpStatusCodes.Incomplete, ex.getMessage()));
        }
    }
}