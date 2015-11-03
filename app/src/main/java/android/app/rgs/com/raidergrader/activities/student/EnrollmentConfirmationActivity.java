package android.app.rgs.com.raidergrader.activities.student;

import android.app.rgs.com.raidergrader.R;
import android.app.rgs.com.raidergrader.controllers.EnrollmentController;
import android.app.rgs.com.raidergrader.data_access.Repository;
import android.app.rgs.com.raidergrader.models.ControllerCallback;
import android.app.rgs.com.raidergrader.models.EnrollmentBindingModel;
import android.app.rgs.com.raidergrader.models.ClassModel;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class EnrollmentConfirmationActivity extends AppCompatActivity
        implements ControllerCallback {

    TextView className, courseNumber, teacherName;

    private ClassModel classModel;
    private EnrollmentController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enrollment_confirmation);

        controller = new EnrollmentController(this, this);
        classModel = Repository.getCurrentClass();
        setReferences();
        setTextValues();
    }

    private void setReferences() {
        className = (TextView) findViewById(R.id.className);
        courseNumber = (TextView) findViewById(R.id.courseNumber);
        teacherName = (TextView) findViewById(R.id.teacherName);
    }

    private void setTextValues() {
        className.setText(classModel.Title);
        courseNumber.setText(String.format("%s %d - %d", classModel.Prefix, classModel.CourseNumber, classModel.Section));
        teacherName.setText(classModel.Teacher.UserName);
    }

    public void onClickConfirm(View v) {
        EnrollmentBindingModel model = new EnrollmentBindingModel();
        model.ClassId = classModel.Id;
        model.StudentUserName = Repository.USERNAME;

        controller.RequestEnrollmentForStudent(model);
    }


    @Override
    public void DisplayResult(Object result) {
    }
}