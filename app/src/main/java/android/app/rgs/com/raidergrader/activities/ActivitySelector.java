package android.app.rgs.com.raidergrader.activities;

import android.app.rgs.com.raidergrader.R;
import android.app.rgs.com.raidergrader.activities.student.EnrollmentActivity;
import android.app.rgs.com.raidergrader.activities.student.StudentClassListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

public class ActivitySelector extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_selector);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                Intent intent1 = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent1);
                break;
            case R.id.btn_enroll:
                Intent intent2 = new Intent(getApplicationContext(), EnrollmentActivity.class);
                startActivity(intent2);
                break;
            case R.id.btn_register:
                Intent intent3 = new Intent(getApplicationContext(), RegistrationActivity.class);
                startActivity(intent3);
                break;
            case R.id.btn_std_classlist:
                Intent intent4 = new Intent(getApplicationContext(), StudentClassListActivity.class);
                startActivity(intent4);
                break;
            default:
                Toast.makeText(this, "Not implemented", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}