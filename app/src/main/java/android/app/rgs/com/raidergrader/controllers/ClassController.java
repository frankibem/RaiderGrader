package android.app.rgs.com.raidergrader.controllers;

import android.app.Activity;
import android.app.ProgressDialog;
import android.app.rgs.com.raidergrader.data_access.HttpStatusCodes;
import android.app.rgs.com.raidergrader.data_access.Repository;
import android.app.rgs.com.raidergrader.data_access.RequestError;
import android.app.rgs.com.raidergrader.data_access.RestTask;
import android.app.rgs.com.raidergrader.data_access.RestUtil;
import android.app.rgs.com.raidergrader.utilities.GlobalHandling;
import android.app.rgs.com.raidergrader.utilities.JsonHelpers;
import android.app.rgs.com.raidergrader.models.ClassModel;
import android.app.rgs.com.raidergrader.models.ControllerCallback;
import android.app.rgs.com.raidergrader.models.UpdateClassModel;
import android.widget.Toast;
import android.app.rgs.com.raidergrader.models.CreateClassModel;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

/**
 * @author Frank Ibem
 * @author Michael Arroyo
 * @author Joshua Hernandez
 */

/**
 * Controller for class related actions.
 */
public class ClassController {
    private final Activity activity;
    private final ControllerCallback controllerCallback;
    private ProgressDialog mProgress;

    /**
     * Creates an AccountController
     *
     * @param activity Activity to be used by the controller
     * @param callback Callback to be notified when results are received
     */
    public ClassController(Activity activity, ControllerCallback callback) {
        this.activity = activity;
        this.controllerCallback = callback;
    }

    /**
     * Returns a class with the specified id
     *
     * @param classId Id of the class to obtain
     */
    public void GetClassWithId(int classId) {
        RestTask.ResponseCallback responseCallback = new RestTask.ResponseCallback() {
            @Override
            public void onRequestSuccess(String response) {
                if (mProgress != null) {
                    mProgress.dismiss();
                }

                Gson gson = new Gson();
                ClassModel classModel = gson.fromJson(response, ClassModel.class);
                controllerCallback.DisplayResult(classModel);
            }

            @Override
            public void onRequestError(RequestError error) {
                if (mProgress != null) {
                    mProgress.dismiss();
                }

                if (error.getStatusCode() == HttpStatusCodes.NotFound) {
                    GlobalHandling.makeShortToast(activity, "No class exists with that ID");
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
            RestTask task = RestUtil.obtainGetTask(Repository.baseUrl + "api/Classes/" + Integer.toString(classId));
            task.setProgressCallback(progressCallback);
            task.setResponseCallback(responseCallback);
            task.execute();

            mProgress = ProgressDialog.show(activity, "Loading", "Fetching Data", true);
        } catch (IOException e) {
            responseCallback.onRequestError(new RequestError(HttpStatusCodes.Incomplete, e.getMessage()));
        }
    }

    /**
     * Creates a new class for a teacher from the given model
     *
     * @param createClassModel Model containing class creation details
     */
    public void CreateClass(CreateClassModel createClassModel) {
        Gson gson = JsonHelpers.getGson();
        String request = gson.toJson(createClassModel);

        RestTask.ResponseCallback responseCallback = new RestTask.ResponseCallback() {
            @Override
            public void onRequestSuccess(String response) {
                if (mProgress != null) {
                    mProgress.dismiss();
                }

                Toast.makeText(activity, "Class successfully created", Toast.LENGTH_SHORT).show();
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
            RestTask task = RestUtil.obtainJSONPostTask(Repository.baseUrl + "api/Classes", request);
            task.setResponseCallback(responseCallback);
            task.setProgressCallback(progressCallback);
            task.execute();

            mProgress = ProgressDialog.show(activity, "Loading", "Creating your class", true);
        } catch (Exception ex) {
            responseCallback.onRequestError(new RequestError(HttpStatusCodes.Incomplete, ex.getMessage()));
        }
    }

    /**
     * Delete a class
     *
     * @param classId ID of the class to delete
     */
    public void DeleteClass(int classId) {
        RestTask.ResponseCallback responseCallback = new RestTask.ResponseCallback() {
            @Override
            public void onRequestSuccess(String response) {
                if (mProgress != null) {
                    mProgress.dismiss();
                }

                Toast.makeText(activity, "Class successfully deleted", Toast.LENGTH_SHORT).show();
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
            RestTask task = RestUtil.obtainDeleteTask(Repository.baseUrl + "api/Classes/" + classId);
            task.setResponseCallback(responseCallback);
            task.setProgressCallback(progressCallback);
            task.execute();

            mProgress = ProgressDialog.show(activity, "Loading", "Deleting your class", true);
        } catch (Exception ex) {
            responseCallback.onRequestError(new RequestError(HttpStatusCodes.Incomplete, ex.getMessage()));
        }
    }

    /**
     * Update the details of a class
     *
     * @param model Model containing details used to update the class
     */
    public void UpdateClass(UpdateClassModel model) {
        Gson gson = new Gson();
        String request = gson.toJson(model);

        RestTask.ResponseCallback responseCallback = new RestTask.ResponseCallback() {
            @Override
            public void onRequestSuccess(String response) {
                if (mProgress != null) {
                    mProgress.dismiss();
                }

                Toast.makeText(activity, "Class updated.", Toast.LENGTH_SHORT).show();

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
            RestTask task = RestUtil.obtainJSONPutTask(Repository.baseUrl + "api/Classes", request);
            task.setProgressCallback(progressCallback);
            task.setResponseCallback(responseCallback);
            task.execute();

            mProgress = ProgressDialog.show(activity, "Loading", "Updating class details", true);
        } catch (Exception ex) {
            responseCallback.onRequestError(new RequestError(HttpStatusCodes.Incomplete, ex.getMessage()));
        }
    }

    /**
     * Returns a list of all classes taught by a given teacher.
     *
     * @param userName Teacher's username
     */
    public void GetTeacherClasses(String userName) {

        RestTask.ResponseCallback responseCallback = new RestTask.ResponseCallback() {
            @Override
            public void onRequestSuccess(String response) {
                if (mProgress != null) {
                    mProgress.dismiss();
                }

                Gson gson = JsonHelpers.getGson();

                Type type = new TypeToken<List<ClassModel>>() {
                }.getType();
                List<ClassModel> result = gson.fromJson(response, type);

                controllerCallback.DisplayResult(result);
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
            RestTask task = RestUtil.obtainGetTask(Repository.baseUrl + "api/Classes?userName=" + userName);
            task.setProgressCallback(progressCallback);
            task.setResponseCallback(responseCallback);
            task.execute();

            mProgress = ProgressDialog.show(activity, "Loading", "Getting class list", true);
        } catch (Exception ex) {
            responseCallback.onRequestError(new RequestError(HttpStatusCodes.Incomplete, ex.getMessage()));
        }
    }
}