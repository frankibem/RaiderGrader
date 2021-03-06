package android.app.rgs.com.raidergrader.controllers;

import android.app.Activity;
import android.app.ProgressDialog;
import android.app.rgs.com.raidergrader.data_access.HttpStatusCodes;
import android.app.rgs.com.raidergrader.data_access.Repository;
import android.app.rgs.com.raidergrader.data_access.RequestError;
import android.app.rgs.com.raidergrader.data_access.RestTask;
import android.app.rgs.com.raidergrader.data_access.RestUtil;
import android.app.rgs.com.raidergrader.models.UpdateWorkItemModel;
import android.app.rgs.com.raidergrader.utilities.GlobalHandling;
import android.app.rgs.com.raidergrader.utilities.JsonHelpers;
import android.app.rgs.com.raidergrader.models.ControllerCallback;
import android.app.rgs.com.raidergrader.models.ScoreUnitModel;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * @author Frank Ibem.
 */
public class GradeController {
    private Activity activity;
    private ControllerCallback controllerCallback;
    private ProgressDialog mProgress;

    /**
     * Creates an AccountController
     *
     * @param activity Activity to be used by the controller
     * @param callback Callback to be notified when results are received
     */
    public GradeController(Activity activity, ControllerCallback callback) {
        this.activity = activity;
        this.controllerCallback = callback;
    }

    /**
     * Returns a list of ScoreUnits for a work-item. The ScoreUnits represent students grade
     *
     * @param workItemId ID of the work-item to return grades for
     */
    public void GetGradesForWorkItem(int workItemId) {
        RestTask.ResponseCallback responseCallback = new RestTask.ResponseCallback() {
            @Override
            public void onRequestSuccess(String response) {
                if (mProgress != null) {
                    mProgress.dismiss();
                }

                Gson gson = JsonHelpers.getGson();

                Type type = new TypeToken<List<ScoreUnitModel>>() {
                }.getType();
                List<ScoreUnitModel> result = gson.fromJson(response, type);

                controllerCallback.DisplayResult(result);
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
            RestTask task = RestUtil.obtainGetTask(Repository.baseUrl + "api/ScoreUnits?workItemId=" + workItemId);
            task.setProgressCallback(progressCallback);
            task.setResponseCallback(responseCallback);
            task.execute();

            mProgress = ProgressDialog.show(activity, "Loading", "Creating your account", true);
        } catch (Exception ex) {
            responseCallback.onRequestError(new RequestError(HttpStatusCodes.Incomplete, ex.getMessage()));
        }
    }

    /**
     * Update the grades of students for a particular work item
     * Created by Michael Arroyo on 10/18/2015
     * @param model
     */
    public void UpdateScoresForWorkItem(List<UpdateWorkItemModel> model) {
        Gson gson = JsonHelpers.getGson();
        String request = gson.toJson(model);

        RestTask.ResponseCallback responseCallback = new RestTask.ResponseCallback() {
            @Override
            public void onRequestSuccess(String response) {
                if (mProgress != null) {
                    mProgress.dismiss();
                }

                Toast.makeText(activity, "Grades updated.", Toast.LENGTH_SHORT).show();
                controllerCallback.DisplayResult(null);
            }

            @Override
            public void onRequestError(RequestError error) {
                if (mProgress != null) {
                    mProgress.dismiss();
                }

                //TODO: Add appropriate error handling later
                if (error.getStatusCode() == HttpStatusCodes.BadRequest) {
                    GlobalHandling.makeShortToast(activity, "Please review your input.");
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
            RestTask task = RestUtil.obtainJSONPutTask(Repository.baseUrl + "api/ScoreUnits", request);
            task.setProgressCallback(progressCallback);
            task.setResponseCallback(responseCallback);
            task.execute();

            mProgress = ProgressDialog.show(activity, "Loading", "Updating grades", true);
        } catch (Exception ex) {
            responseCallback.onRequestError(new RequestError(HttpStatusCodes.Incomplete, ex.getMessage()));
        }
    }
}