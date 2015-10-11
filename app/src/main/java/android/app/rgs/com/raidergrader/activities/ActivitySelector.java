package android.app.rgs.com.raidergrader.activities;

import android.app.rgs.com.raidergrader.R;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
            case R.id.btn_std_classdetail:
                Intent intent5 = new Intent(this, StudentClassDetailActivity.class);
                startActivity(intent5);
                break;
            case R.id.btn_tch_classlist:
                Intent intent6 = new Intent(this, TeacherClassListActivity.class);
                startActivity(intent6);
                break;
            case R.id.btn_std_dropclass:
                Intent intent7 = new Intent(this, StudentDropClassActivity.class);
                startActivity(intent7);
                break;
            case R.id.btn_tch_deleteclass:
                Intent intent8 = new Intent(this, TeacherDeleteClassActivity.class);
                startActivity(intent8);
                break;
            case R.id.btn_tch_manageclass:
                Intent intent9 = new Intent(this, TeacherClassManagementActivity.class);
                startActivity(intent9);
                break;
            case R.id.btn_std_workitemdetail:
                Intent intent10 = new Intent(this, StudentWorkItemDetailActivity.class);
                startActivity(intent10);
                break;
            default:
                Toast.makeText(this, "Not implemented", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}