package android.app.rgs.com.raidergrader.activities;

import android.app.ProgressDialog;
import android.app.rgs.com.raidergrader.R;
import android.app.rgs.com.raidergrader.data_access.HttpStatusCodes;
import android.app.rgs.com.raidergrader.data_access.Repository;
import android.app.rgs.com.raidergrader.data_access.RequestError;
import android.app.rgs.com.raidergrader.data_access.RestTask;
import android.app.rgs.com.raidergrader.data_access.RestUtil;
import android.app.rgs.com.raidergrader.helpers.RgsTextWatcher;
import android.app.rgs.com.raidergrader.helpers.ValidateConstant;
import android.app.rgs.com.raidergrader.helpers.Validators;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity
        implements RestTask.ProgressCallback, RestTask.ResponseCallback {
    private EditText inputEmail, inputPassword;
    private TextInputLayout inputLayoutEmail, inputLayoutPassword;
    private Button btnLogin;

    private ProgressDialog mProgress;
    private String username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        inputLayoutEmail = (TextInputLayout) findViewById(R.id.input_layout_email);
        inputLayoutPassword = (TextInputLayout) findViewById(R.id.input_layout_password);
        inputEmail = (EditText) findViewById(R.id.input_email);
        inputPassword = (EditText) findViewById(R.id.input_password);
        btnLogin = (Button) findViewById(R.id.btn_login);

        inputEmail.addTextChangedListener(new RgsTextWatcher(getWindow(), inputEmail, inputLayoutEmail, ValidateConstant.EMAIL));
        inputPassword.addTextChangedListener(new RgsTextWatcher(getWindow(), inputPassword, inputLayoutPassword, ValidateConstant.PASSWORD));

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitForm();
            }
        });
    }

    private void submitForm() {
        if (!Validators.validateEmail(inputEmail.getText().toString())) {
            return;
        }
        if (!Validators.validatePassword(inputPassword.getText().toString())) {
            inputLayoutPassword.setError("Password cannot be empty");
            return;
        }

        try {
            password = inputPassword.getText().toString().trim();
            username = inputEmail.getText().toString().trim();

            ArrayList<Pair<String, String>> formBody = new ArrayList<>();
            formBody.add(new Pair<>("password", password));
            formBody.add(new Pair<>("grant_type", "password"));
            formBody.add(new Pair<>("username", username));

            RestTask loginTask = RestUtil.obtainLoginTask(Repository.baseUrl + "Token", formBody);
            loginTask.setResponseCallback(this);
            loginTask.setProgressCallback(this);
            loginTask.execute();

            mProgress = ProgressDialog.show(this, "Loading", "Logging you in...", true);
        } catch (Exception e) {
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

        try {
            storeCredentials(response);
            Intent intent = new Intent(getApplicationContext(), StudentClassListActivity.class);
            startActivity(intent);
            Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();
        } catch (JSONException e) {
            //TODO: Consider carefully
            onRequestError(new RequestError(HttpStatusCodes.Incomplete, e.getMessage()));
        }
    }

    private void storeCredentials(String response) throws JSONException {
        JSONObject jsonObject = new JSONObject(response);
        Repository.ACCESS_TOKEN = jsonObject.getString("access_token");
        Repository.USERNAME = username;

        // Persist
        SharedPreferences settings = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(Repository.ACCESS_TOKEN_KEY, Repository.ACCESS_TOKEN);
        editor.putString(Repository.USERNAME_KEY, Repository.USERNAME);
        editor.commit();
    }


    @Override
    public void onRequestError(RequestError error) {
        if (mProgress != null) {
            mProgress.dismiss();
        }
        Toast.makeText(this, error.getMessage(), Toast.LENGTH_SHORT).show();
    }

    public void onClickRegister(View view) {
        Intent intent = new Intent(this, RegistrationActivity.class);
        startActivity(intent);
    }
}