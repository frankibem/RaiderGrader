package android.app.rgs.com.raidergrader.activities;

import android.app.ProgressDialog;
import android.app.rgs.com.raidergrader.R;
import android.app.rgs.com.raidergrader.data_access.HttpStatusCodes;
import android.app.rgs.com.raidergrader.data_access.Repository;
import android.app.rgs.com.raidergrader.data_access.RequestError;
import android.app.rgs.com.raidergrader.data_access.RestTask;
import android.app.rgs.com.raidergrader.data_access.RestUtil;
import android.app.rgs.com.raidergrader.helpers.GlobalHandling;
import android.app.rgs.com.raidergrader.helpers.RgsTextWatcher;
import android.app.rgs.com.raidergrader.helpers.ValidateConstant;
import android.app.rgs.com.raidergrader.models.ClassModel;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;

import java.io.IOException;

public class EnrollmentActivity extends AppCompatActivity
        implements RestTask.ProgressCallback, RestTask.ResponseCallback {

    Button submitBtn;
    EditText inputClassId;
    TextInputLayout inputLayoutClassId;
    ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enrollment);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        submitBtn = (Button) findViewById(R.id.submit_btn);
        inputClassId = (EditText) findViewById(R.id.input_classid);
        inputLayoutClassId = (TextInputLayout) findViewById(R.id.input_layout_classid);

        inputClassId.addTextChangedListener(new RgsTextWatcher(getWindow(), inputClassId, inputLayoutClassId, ValidateConstant.INTEGER));
    }

    public void onClickSubmit(View v) {
//        if(!Validators.hasNoError(inputLayoutClassId)) {
//            return;
//        }
        String classid = inputClassId.getText().toString();

        try {
            RestTask task = RestUtil.obtainGetTask(Repository.baseUrl + "api/Classes/" + classid);
            task.setProgressCallback(this);
            task.setResponseCallback(this);
            task.execute();

            mProgress = ProgressDialog.show(this, "Loading", "Fetching Data", true);
        } catch (IOException e) {
            onRequestError(new RequestError(HttpStatusCodes.Incomplete, e.getMessage()));
        }
    }

    @Override
    public void onProgressUpdate(int progress) {

    }

    @Override
    public void onRequestSuccess(String response) {
        if (mProgress != null) {
            mProgress.dismiss();
        }
        Gson gson = new Gson();
        ClassModel cvm = gson.fromJson(response, ClassModel.class);

        Intent intent = new Intent(this, EnrollmentConfirmationActivity.class);
        Repository.selectedEnrollClass = cvm;
        startActivity(intent);
    }

    @Override
    public void onRequestError(RequestError error) {
        if (mProgress != null) {
            mProgress.dismiss();
        }

        if (error.getStatusCode() == HttpStatusCodes.NotFound) {
            GlobalHandling.makeShortToast(this, "No class exists with that ID");
        }else{
            GlobalHandling.generalError(this, error);
        }
    }
}