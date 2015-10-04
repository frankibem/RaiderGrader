package android.app.rgs.com.raidergrader.activities;

import android.app.rgs.com.raidergrader.EnrollmentConfirmationActivity;
import android.app.rgs.com.raidergrader.R;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EnrollmentActivity extends AppCompatActivity {

    Button submitBtn;
    EditText classIDEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enrollment);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        submitBtn = (Button) findViewById(R.id.submit_btn);
        classIDEdit = (EditText) findViewById(R.id.classID_text);

        setSupportActionBar(toolbar);
    }

    public void onClickSubmit(View v) {
        String classid = classIDEdit.getText().toString();
        Intent intent = new Intent(this, EnrollmentConfirmationActivity.class);
        intent.putExtra("classid", classid);
        startActivity(intent);

    }

}
