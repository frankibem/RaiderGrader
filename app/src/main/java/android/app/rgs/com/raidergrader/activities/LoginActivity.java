package android.app.rgs.com.raidergrader.activities;

import android.app.ProgressDialog;
import android.app.rgs.com.raidergrader.R;
import android.app.rgs.com.raidergrader.controllers.AccountController;
import android.app.rgs.com.raidergrader.data_access.HttpStatusCodes;
import android.app.rgs.com.raidergrader.data_access.Repository;
import android.app.rgs.com.raidergrader.data_access.RequestError;
import android.app.rgs.com.raidergrader.data_access.RestTask;
import android.app.rgs.com.raidergrader.data_access.RestUtil;
import android.app.rgs.com.raidergrader.helpers.GlobalHandling;
import android.app.rgs.com.raidergrader.helpers.RgsTextWatcher;
import android.app.rgs.com.raidergrader.helpers.ValidateConstant;
import android.app.rgs.com.raidergrader.helpers.Validators;
import android.app.rgs.com.raidergrader.models.ControllerCallback;
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
        implements ControllerCallback {
    private EditText inputEmail,
            inputPassword;

    private TextInputLayout inputLayoutEmail,
            inputLayoutPassword;

    private AccountController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        controller = new AccountController(this, this);

        inputLayoutEmail = (TextInputLayout) findViewById(R.id.input_layout_email);
        inputLayoutPassword = (TextInputLayout) findViewById(R.id.input_layout_password);
        inputEmail = (EditText) findViewById(R.id.input_email);
        inputPassword = (EditText) findViewById(R.id.input_password);

        inputEmail.addTextChangedListener(new RgsTextWatcher(getWindow(), inputEmail, inputLayoutEmail, ValidateConstant.EMAIL));
        inputPassword.addTextChangedListener(new RgsTextWatcher(getWindow(), inputPassword, inputLayoutPassword, ValidateConstant.NON_EMPTY_TEXT));
    }

    public void onClickLogin(View view) {
        boolean hasErrors = false;
        if (!Validators.validateEmail(inputEmail.getText().toString())) {
            inputEmail.setError(getResources().getString(R.string.invalid_email));
            hasErrors = true;
        }
        if (!Validators.validateNonEmptyText(inputPassword.getText().toString())) {
            inputLayoutPassword.setError(getResources().getString(R.string.invalid_nonEmptyText));
            hasErrors = true;
        }

        if (hasErrors) {
            return;
        }

        String password = inputPassword.getText().toString().trim();
        String username = inputEmail.getText().toString().trim();

        controller.LogUserIn(username, password);
    }

    public void onClickRegister(View view) {
        Intent intent = new Intent(this, RegistrationActivity.class);
        startActivity(intent);
    }

    @Override
    public void DisplayResult(Object result) {
    }
}