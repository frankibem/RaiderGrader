package android.app.rgs.com.raidergrader.controllers;

import android.app.Activity;
import android.app.ProgressDialog;
import android.app.rgs.com.raidergrader.data_access.HttpStatusCodes;
import android.app.rgs.com.raidergrader.data_access.Repository;
import android.app.rgs.com.raidergrader.data_access.RequestError;
import android.app.rgs.com.raidergrader.data_access.RestTask;
import android.app.rgs.com.raidergrader.data_access.RestUtil;
import android.app.rgs.com.raidergrader.helpers.GlobalHandling;
import android.app.rgs.com.raidergrader.models.ControllerCallback;
import android.app.rgs.com.raidergrader.models.WorkItemModel;
import android.widget.Toast;

import java.io.IOException;

/**
 * Created by Frank Ibem on 10/16/2015.
 */
public class WorkItemController {
    private Activity activity;
    private ControllerCallback controllerCallback;
    private ProgressDialog mProgress;

    /**
     * Creates an AccountController
     *
     * @param activity Activity to be used by the controller
     * @param callback Callback to be notified when results are received
     */
    public WorkItemController(Activity activity, ControllerCallback callback) {
        this.activity = activity;
        this.controllerCallback = callback;
    }

    /**@author Claire Gray
     * deleting Work item
     */
    public void DeleteWorkItem(int workItemId){
        RestTask.ResponseCallback responseCallback = new RestTask.ResponseCallback() {
            @Override
            public void onRequestSuccess(String response) {
                if (mProgress != null) {
                    mProgress.dismiss();
                }

                Toast.makeText(activity, "Work Item deleted", Toast.LENGTH_SHORT).show();
                controllerCallback.DisplayResult(null);
            }

            @Override
            public void onRequestError(RequestError error) {
                if (mProgress != null) {
                    mProgress.dismiss();
                }

                // TODO: Add appropriate error handling later
                if (error.getStatusCode() == HttpStatusCodes.Conflict) {
                    GlobalHandling.makeShortToast(activity, "You are already enrolled in this class");
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
            RestTask task = RestUtil.obtainDeleteTask(Repository.baseUrl + "api/WorkItems/" + workItemId);
            task.setResponseCallback(responseCallback);
            task.setProgressCallback(progressCallback);
            task.execute();

            mProgress = ProgressDialog.show(activity, "Loading", "Deleting your Work Item", true);
        } catch (Exception ex) {
            responseCallback.onRequestError(new RequestError(HttpStatusCodes.Incomplete, ex.getMessage()));
        }
    }
}