package android.app.rgs.com.raidergrader.controllers;

import android.app.Activity;
import android.app.ProgressDialog;
import android.app.rgs.com.raidergrader.activities.StudentClassListActivity;
import android.app.rgs.com.raidergrader.data_access.HttpStatusCodes;
import android.app.rgs.com.raidergrader.data_access.Repository;
import android.app.rgs.com.raidergrader.data_access.RequestError;
import android.app.rgs.com.raidergrader.data_access.RestTask;
import android.app.rgs.com.raidergrader.data_access.RestUtil;
import android.app.rgs.com.raidergrader.helpers.GlobalHandling;
import android.app.rgs.com.raidergrader.helpers.JsonHelpers;
import android.app.rgs.com.raidergrader.models.ControllerCallback;
import android.app.rgs.com.raidergrader.models.EnrollmentBindingModel;
import android.app.rgs.com.raidergrader.models.EnrollmentModel;
import android.app.rgs.com.raidergrader.models.ScoreUnitModel;
import android.content.Intent;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Frank Ibem on 10/16/2015.
 */
public class EnrollmentController {
    private Activity activity;
    private ControllerCallback controllerCallback;
    private ProgressDialog mProgress;

    /**
     * Creates an EnrollmentController
     *
     * @param activity Activity to be used by the controller
     * @param callback Callback to be notified when results are received
     */
    public EnrollmentController(Activity activity, ControllerCallback callback) {
        this.activity = activity;
        this.controllerCallback = callback;
    }

    /**
     * Noris Rogers
     * Request enrollment for a student into a class
     *
     * @param model Model containing the details of the request
     */
    public void RequestEnrollmentforStudent(EnrollmentBindingModel model) {
        Gson gson = JsonHelpers.getGson();
        String request = gson.toJson(model);

        RestTask.ResponseCallback responseCallback = new RestTask.ResponseCallback() {
            @Override
            public void onRequestSuccess(String response) {
                if (mProgress != null) {
                    mProgress.dismiss();
                }

                Toast.makeText(activity, "Enrollment has been requested", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRequestError(RequestError error) {
                if (mProgress != null) {
                    mProgress.dismiss();
                }
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
            RestTask task = RestUtil.obtainJSONPostTask(Repository.baseUrl + "api/Enrollments", request);
            task.setProgressCallback(progressCallback);
            task.setResponseCallback(responseCallback);

            task.execute();
            mProgress = ProgressDialog.show(activity, "Loading", "Requesting your enrollment...", true);
        } catch (Exception ex) {
            responseCallback.onRequestError(new RequestError(HttpStatusCodes.Incomplete, ex.getMessage()));
        }
    }


    /**
    * Lauren Joness
    * Drops a student from a class
    * */
    public void DropStudent(EnrollmentBindingModel model){
        Gson gson = JsonHelpers.getGson();
        String request = gson.toJson(model);

        RestTask.ResponseCallback responseCallback = new RestTask.ResponseCallback() {
            @Override
            public void onRequestSuccess(String response) {
                if (mProgress != null) {
                    mProgress.dismiss();
                }

                Toast.makeText(activity, "Successfully dropped class.", Toast.LENGTH_SHORT).show();
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
            RestTask task = RestUtil.obtainJSONDeleteTask(Repository.baseUrl + "api/Enrollments", request);
            task.setProgressCallback(progressCallback);
            task.setResponseCallback(responseCallback);
            task.execute();

            mProgress = ProgressDialog.show(activity, "Loading", "Dropping class...", true);
        } catch (Exception ex) {
            responseCallback.onRequestError(new RequestError(HttpStatusCodes.Incomplete, ex.getMessage()));
        }
    }

    /**
    * Lauren Joness
    * Returns a list of all enrollments for a student (pending and not)
    * */
    public void GetStudentEnrollments(String studentUserName){
        RestTask.ResponseCallback responseCallback = new RestTask.ResponseCallback() {
            @Override
            public void onRequestSuccess(String response) {
                if (mProgress != null) {
                    mProgress.dismiss();
                }
                Gson gson = JsonHelpers.getGson();

                Type type = new TypeToken<List<EnrollmentModel>>() {
                }.getType();
                List<EnrollmentModel> result = gson.fromJson(response, type);

                controllerCallback.DisplayResult(result);
            }

            @Override
            public void onRequestError(RequestError error) {
                if (mProgress != null) {
                    mProgress.dismiss();
                }

                // TODO: Add appropriate error handling later
                if (error.getStatusCode() == HttpStatusCodes.Conflict) {
                    GlobalHandling.makeShortToast(activity, "Error loading enrollments");
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
            RestTask task = RestUtil.obtainGetTask(Repository.baseUrl + "api/Enrollments?studentUserName=" + studentUserName);
            task.setResponseCallback(responseCallback);
            task.setProgressCallback(progressCallback);
            task.execute();

            mProgress = ProgressDialog.show(activity, "Loading", "Loading student enrollments", true);
        } catch (Exception ex) {
            responseCallback.onRequestError(new RequestError(HttpStatusCodes.Incomplete, ex.getMessage()));
        }
    }

    /**
     * Noris Rogers
     *
     *  Accepts or rejects students enrollment into a class
     *  @param model Model containing the details of the request
     */

    public void AcceptStudentEnrollment(EnrollmentBindingModel model){
        Gson gson = JsonHelpers.getGson();
        String request = gson.toJson(model);

        RestTask.ResponseCallback responseCallback = new RestTask.ResponseCallback() {
            @Override
            public void onRequestSuccess(String response) {
                if (mProgress != null) {
                    mProgress.dismiss();
                }

                Toast.makeText(activity, "Enrollment successful", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRequestError(RequestError error) {
                if (mProgress != null) {
                    mProgress.dismiss();
                }
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
            RestTask task = RestUtil.obtainJSONPostTask(Repository.baseUrl + "api/Enrollments", request);
            task.setProgressCallback(progressCallback);
            task.setResponseCallback(responseCallback);

            task.execute();
            mProgress = ProgressDialog.show(activity, "Loading", "Accepting enrollment...", true);
        } catch (Exception ex) {
            responseCallback.onRequestError(new RequestError(HttpStatusCodes.Incomplete, ex.getMessage()));
        }
    }

    /**
     * Claire Gray
     * Returns a list of all students whose enrollment into a class is pending
     * @param classId
     */
    public void GetPendingEnrollments(int classId) {
        RestTask.ResponseCallback responseCallback = new RestTask.ResponseCallback() {
            @Override
            public void onRequestSuccess(String response) {
                if (mProgress != null) {
                    mProgress.dismiss();
                }

                Gson gson = JsonHelpers.getGson();
                Type type = new TypeToken<List<EnrollmentModel>>() {
                }.getType();
                List<EnrollmentModel> result = gson.fromJson(response, type);
                List<EnrollmentModel> pending = new ArrayList<>();
                for (EnrollmentModel enroll : result) {
                    if (enroll.Pending) {
                        pending.add(enroll);
                    }
                }
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
            RestTask task = RestUtil.obtainGetTask(Repository.baseUrl + "api/Enrollments?classId=" + classId);
            task.setProgressCallback(progressCallback);
            task.setResponseCallback(responseCallback);
            task.execute();

            mProgress = ProgressDialog.show(activity, "Loading", "Fetching Waitlist.", true);
        } catch (Exception ex) {
            responseCallback.onRequestError(new RequestError(HttpStatusCodes.Incomplete, ex.getMessage()));
        }
    }

    /**
     * Claire Gray
     * Returns a list of all students who have been accepted into a class
     *
     * @param classId
     */
    public void GetAcceptedEnrollment(int classId){
        RestTask.ResponseCallback responseCallback = new RestTask.ResponseCallback() {
            @Override
            public void onRequestSuccess(String response) {
                if (mProgress != null) {
                    mProgress.dismiss();
                }

                Gson gson = JsonHelpers.getGson();
                Type type = new TypeToken<List<EnrollmentModel>>() {
                }.getType();
                List<EnrollmentModel> result = gson.fromJson(response, type);
                List<EnrollmentModel> accepted = new ArrayList<>();
                for (EnrollmentModel enroll : result) {
                    if (!enroll.Pending) {
                        accepted.add(enroll);
                    }
                }
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
            RestTask task = RestUtil.obtainGetTask(Repository.baseUrl + "api/Enrollments?classId=" + classId);
            task.setProgressCallback(progressCallback);
            task.setResponseCallback(responseCallback);
            task.execute();

            mProgress = ProgressDialog.show(activity, "Loading", "You have successfully enrolled in your class.", true);
        } catch (Exception ex) {
            responseCallback.onRequestError(new RequestError(HttpStatusCodes.Incomplete, ex.getMessage()));
        }

    }
}