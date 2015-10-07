package android.app.rgs.com.raidergrader.activities;

import android.app.rgs.com.raidergrader.R;
import android.app.rgs.com.raidergrader.data_access.Repository;
import android.app.rgs.com.raidergrader.view_models.ClassViewModel;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class EnrollmentConfirmationActivity extends AppCompatActivity {

    TextView outputEdit;
    TextView className;
    TextView courseNumber;
    TextView teacherName;
    TextView startDate;
    TextView endDate;
    ClassViewModel cvm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enrollment_confirmation);

        cvm = Repository.selectedEnrollClass;
        setReferences();
        setTextValues();
    }

    private void setReferences(){
        outputEdit = (TextView) findViewById(R.id.enrollconfirm);
    }

    private void setTextValues(){
        className = (TextView) findViewById(R.id.className);
        className.setText(cvm.Title);
        courseNumber = (TextView) findViewById(R.id.courseNumber);
        courseNumber.setText(cvm.Prefix + " " + cvm.CourseNumber + "-" + cvm.Section);
        teacherName = (TextView) findViewById(R.id.teacherName);
        teacherName.setText(cvm.TeacherName);
//        startDate = (TextView) findViewById(R.id.startDate);
//        startDate.setText(cvm.StartDate);
//        endDate = (TextView) findViewById(R.id.endDate);
//        endDate.setText(cvm.EndDate);
    }

    public void onClickConfirm(View v) {


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
}
