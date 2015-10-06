package android.app.rgs.com.raidergrader.activities;

import android.app.ProgressDialog;
import android.app.rgs.com.raidergrader.R;
import android.app.rgs.com.raidergrader.data_access.RestTask;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EnrollmentActivity extends AppCompatActivity
implements RestTask.ProgressCallback, RestTask.ResponseCallback{

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

//        inputClassId.addTextChangedListener();
    }

    public void onClickSubmit(View v) {
        String classid = inputClassId.getText().toString();
        Intent intent = new Intent(this, EnrollmentConfirmationActivity.class);
        intent.putExtra("classid", classid);
        startActivity(intent);

    }

    @Override
    public void onProgressUpdate(int progress) {

    }

    @Override
    public void onRequestSuccess(String response) {

    }

    @Override
    public void onRequestError(Exception error) {

    }
}
