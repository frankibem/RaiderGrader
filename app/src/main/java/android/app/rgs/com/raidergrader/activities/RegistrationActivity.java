package android.app.rgs.com.raidergrader.activities;

import android.app.ProgressDialog;
import android.app.rgs.com.raidergrader.R;
import android.app.rgs.com.raidergrader.data_access.Repository;
import android.app.rgs.com.raidergrader.data_access.RestTask;
import android.app.rgs.com.raidergrader.data_access.RestUtil;
import android.app.rgs.com.raidergrader.helpers.RgsTextWatcher;
import android.app.rgs.com.raidergrader.helpers.ValidateConstant;
import android.app.rgs.com.raidergrader.helpers.Validators;
import android.app.rgs.com.raidergrader.models.RegisterModel;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;

public class RegistrationActivity extends AppCompatActivity
        implements RestTask.ResponseCallback, RestTask.ProgressCallback {
    private EditText inputFirstName, inputLastName, inputEmail, inputPassword, inputConfirmPwd;
    private TextInputLayout inputLayoutFName, inputLayoutLName, inputLayoutEmail, inputLayoutPassword,
            inputLayoutConfirmPwd;
    private Spinner spinnerRole;
    private Button btnRegister;
    private ProgressDialog mProgress;

    private String[] roles = {"Teacher", "Student"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        LoadReferences();
        SetValidators();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, roles);
        spinnerRole.setAdapter(adapter);
    }

    private void LoadReferences() {
        inputFirstName = (EditText) findViewById(R.id.input_fName);
        inputLastName = (EditText) findViewById(R.id.input_lName);
        inputEmail = (EditText) findViewById(R.id.input_email);
        inputPassword = (EditText) findViewById(R.id.input_password);
        inputConfirmPwd = (EditText) findViewById(R.id.input_confirmPwd);

        inputLayoutFName = (TextInputLayout) findViewById(R.id.input_layout_fName);
        inputLayoutLName = (TextInputLayout) findViewById(R.id.input_layout_lName);
        inputLayoutEmail = (TextInputLayout) findViewById(R.id.input_layout_email);
        inputLayoutPassword = (TextInputLayout) findViewById(R.id.input_layout_password);
        inputLayoutConfirmPwd = (TextInputLayout) findViewById(R.id.input_layout_confirmPwd);

        spinnerRole = (Spinner) findViewById(R.id.spinner_roles);
        btnRegister = (Button) findViewById(R.id.btn_register);
    }

    private void SetValidators() {
        inputFirstName.addTextChangedListener(new RgsTextWatcher(getWindow(), inputFirstName,
                inputLayoutFName, ValidateConstant.NON_EMPTY_TEXT));
        inputLastName.addTextChangedListener(new RgsTextWatcher(getWindow(), inputLastName,
                inputLayoutLName, ValidateConstant.NON_EMPTY_TEXT));
        inputEmail.addTextChangedListener(new RgsTextWatcher(getWindow(), inputEmail,
                inputLayoutEmail, ValidateConstant.EMAIL));
        inputPassword.addTextChangedListener(new RgsTextWatcher(getWindow(), inputPassword,
                inputLayoutConfirmPwd, ValidateConstant.NON_EMPTY_TEXT));
        inputConfirmPwd.addTextChangedListener(new RgsTextWatcher(getWindow(), inputConfirmPwd,
                inputLayoutConfirmPwd, ValidateConstant.NON_EMPTY_TEXT));
    }

    /**
     * Validates all input fields
     *
     * @return
     */
    private boolean ValidateFields() {
        boolean foundError = false;

        if (!Validators.validateNonEmptyText(inputFirstName.getText().toString())) {
            foundError = true;
            inputLayoutFName.setError("First name should not be empty");
        }
        if (!Validators.validateNonEmptyText(inputLastName.getText().toString())) {
            foundError = true;
            inputLayoutLName.setError("Last name should not be empty");
        }
        if (!Validators.validateEmail(inputEmail.getText().toString())) {
            foundError = true;
            inputLayoutEmail.setError("Email is not valid");
        }
        if (!Validators.validateNonEmptyText(inputPassword.getText().toString())) {
            foundError = true;
            inputLayoutPassword.setError("Password is not valid");
        }
        if (!Validators.validateTextEquality(inputPassword.getText().toString(), inputConfirmPwd.getText().toString())) {
            foundError = true;
            inputLayoutConfirmPwd.setError("Passwords do not match");
        }

        return foundError;
    }

    /**
     * Returns true if input associated input control has no error
     *
     * @param til
     * @return
     */
    private boolean HasError(TextInputLayout til) {
        return til.getError() == "";
    }

    public void onClick(View v) {
        if (!ValidateFields()) {
            return;
        }

        try {
            RegisterModel registerModel = new RegisterModel();
            registerModel.FirstName = inputFirstName.getText().toString();
            registerModel.LastName = inputLastName.getText().toString();
            registerModel.Email = inputEmail.getText().toString();
            registerModel.Password = inputPassword.getText().toString();
            //returns an object that is typecast to string
            registerModel.Role = (String) spinnerRole.getSelectedItem();

            Gson gson = new Gson();
            //sent to server
            String entity = gson.toJson(registerModel);

            RestTask task = RestUtil.obtainJSONPostTask(Repository.baseUrl + "api/Account/Register", entity);
            task.setProgressCallback(this);
            task.setResponseCallback(this);
            task.execute();

            mProgress=ProgressDialog.show(this, "Loading", "Creating your account", true);
        } catch (Exception e) {
        }
    }

    @Override
    public void onProgressUpdate(int progress) {

    }

    @Override
    public void onRequestSuccess(String response) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);

        Toast.makeText(this, "Account created", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestError(Exception error) {

    }
}