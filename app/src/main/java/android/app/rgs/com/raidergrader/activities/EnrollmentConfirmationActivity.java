package android.app.rgs.com.raidergrader.activities;

import android.app.ProgressDialog;
import android.app.rgs.com.raidergrader.R;
import android.app.rgs.com.raidergrader.adapters.ClassListAdapter;
import android.app.rgs.com.raidergrader.data_access.HttpStatusCodes;
import android.app.rgs.com.raidergrader.data_access.Repository;
import android.app.rgs.com.raidergrader.data_access.RequestError;
import android.app.rgs.com.raidergrader.data_access.RestTask;
import android.app.rgs.com.raidergrader.data_access.RestUtil;
import android.app.rgs.com.raidergrader.helpers.GlobalHandling;
import android.app.rgs.com.raidergrader.models.EnrollmentModel;
import android.app.rgs.com.raidergrader.view_models.ClassViewModel;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;

public class EnrollmentConfirmationActivity extends AppCompatActivity
        implements RestTask.ProgressCallback, RestTask.ResponseCallback {

    TextView className, courseNumber, teacherName;
//    TextView startDate;
//    TextView endDate;

    private ProgressDialog mProgress;
    private ClassViewModel cvm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enrollment_confirmation);

        cvm = Repository.selectedEnrollClass;
        setReferences();
        setTextValues();
    }

    private void setReferences() {
        className = (TextView) findViewById(R.id.className);
        courseNumber = (TextView) findViewById(R.id.courseNumber);
        teacherName = (TextView) findViewById(R.id.teacherName);
//        startDate = (TextView) findViewById(R.id.startDate);
//        endDate = (TextView) findViewById(R.id.endDate);
    }

    private void setTextValues() {
        className.setText(cvm.Title);
        courseNumber.setText(String.format("%s %d - %d", cvm.Prefix, cvm.CourseNumber, cvm.Section));
        teacherName.setText(cvm.TeacherName);
//        startDate.setText(cvm.StartDate);
//        endDate.setText(cvm.EndDate);
    }

    public void onClickConfirm(View v) {
        EnrollmentModel model = new EnrollmentModel();
        model.ClassId = cvm.Id;
        model.StudentUserName = Repository.USERNAME;

        Gson gson = new Gson();
        String request = gson.toJson(model);

        try {
            RestTask task = RestUtil.obtainJSONPostTask(Repository.baseUrl + "api/Students", request);
            task.setProgressCallback(this);
            task.setResponseCallback(this);
            task.execute();

            mProgress = ProgressDialog.show(this, "Loading", "Enrolling you into class...", true);
        } catch (Exception e) {
            onRequestError(new RequestError(HttpStatusCodes.Incomplete, e.getMessage()));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_enrollment_confirmation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onProgressUpdate(int progress) {
    }

    @Override
    public void onRequestSuccess(String response) {
        if (mProgress != null) {
            mProgress.dismiss();
        }

        Intent intent = new Intent(this, StudentClassListActivity.class);
        startActivity(intent);
        Toast.makeText(this, "Enrollment successful", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestError(RequestError error) {
        if (mProgress != null) {
            mProgress.dismiss();
        }
        if (error.getStatusCode() == HttpStatusCodes.Conflict) {
            GlobalHandling.makeShortToast(this, "You are already enrolled in this class");
        } else {
            GlobalHandling.generalError(this, error);
        }
    }
}