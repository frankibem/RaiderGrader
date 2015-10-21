package android.app.rgs.com.raidergrader.activities.student;

import android.app.rgs.com.raidergrader.R;
import android.app.rgs.com.raidergrader.controllers.AccountController;
import android.app.rgs.com.raidergrader.controllers.EnrollmentController;
import android.app.rgs.com.raidergrader.data_access.Repository;
import android.app.rgs.com.raidergrader.models.ControllerCallback;
import android.app.rgs.com.raidergrader.models.EnrollmentBindingModel;
import android.app.rgs.com.raidergrader.models.ClassModel;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class EnrollmentConfirmationActivity extends AppCompatActivity
        implements ControllerCallback {

    TextView className, courseNumber, teacherName;

    private ClassModel cvm;
    private EnrollmentController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enrollment_confirmation);

        controller = new EnrollmentController(this, this);
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
        teacherName.setText(cvm.Teacher.UserName);
//        startDate.setText(cvm.StartDate);
//        endDate.setText(cvm.EndDate);
    }

    public void onClickConfirm(View v) {
        EnrollmentBindingModel model = new EnrollmentBindingModel();
        model.ClassId = cvm.Id;
        model.StudentUserName = Repository.USERNAME;

        controller.RequestEnrollmentforStudent(model);
    }


    @Override
    public void DisplayResult(Object result) {
    }
}