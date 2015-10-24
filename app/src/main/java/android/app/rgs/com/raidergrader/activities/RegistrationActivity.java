package android.app.rgs.com.raidergrader.activities;

import android.app.rgs.com.raidergrader.R;
import android.app.rgs.com.raidergrader.controllers.AccountController;
import android.app.rgs.com.raidergrader.utilities.RgsTextWatcher;
import android.app.rgs.com.raidergrader.utilities.ValidateConstant;
import android.app.rgs.com.raidergrader.utilities.Validators;
import android.app.rgs.com.raidergrader.models.ControllerCallback;
import android.app.rgs.com.raidergrader.models.RegisterModel;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class RegistrationActivity extends AppCompatActivity
        implements ControllerCallback {
    private EditText inputFirstName,
            inputLastName,
            inputEmail,
            inputPassword,
            inputConfirmPwd;

    private TextInputLayout inputLayoutFName,
            inputLayoutLName,
            inputLayoutEmail,
            inputLayoutPassword,
            inputLayoutConfirmPwd;

    private Spinner spinnerRole;
    private Button btnRegister;

    private AccountController controller;

    private String[] roles = {"Teacher", "Student"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        controller = new AccountController(this, this);

        LoadReferences();
        SetValidators();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, roles);
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
                inputLayoutPassword, ValidateConstant.PASSWORD));
        inputConfirmPwd.addTextChangedListener(new RgsTextWatcher(getWindow(), inputConfirmPwd,
                inputLayoutConfirmPwd, ValidateConstant.NON_EMPTY_TEXT));
    }

    /**
     * Returns true if all input fields pass validation
     */
    private boolean ValidateFields() {
        boolean noErrors = true;

        if (!Validators.validateNonEmptyText(inputFirstName.getText().toString())) {
            noErrors = false;
            inputLayoutFName.setError("First name should not be empty");
        }
        if (!Validators.validateNonEmptyText(inputLastName.getText().toString())) {
            noErrors = false;
            inputLayoutLName.setError("Last name should not be empty");
        }
        if (!Validators.validateEmail(inputEmail.getText().toString())) {
            noErrors = false;
            inputLayoutEmail.setError("Email is not valid");
        }
        if (!Validators.validateNonEmptyText(inputPassword.getText().toString())) {
            noErrors = false;
            inputLayoutPassword.setError("Password is not valid");
        }
        if (!Validators.validateTextEquality(inputPassword.getText().toString(), inputConfirmPwd.getText().toString())) {
            noErrors = false;
            inputLayoutConfirmPwd.setError("Passwords do not match");
        }

        return noErrors;
    }

    public void onClick(View v) {
        if (!ValidateFields()) {
            Toast.makeText(this, "Review your input", Toast.LENGTH_SHORT).show();
            return;
        }

        RegisterModel registerModel = new RegisterModel();
        registerModel.FirstName = inputFirstName.getText().toString();
        registerModel.LastName = inputLastName.getText().toString();
        registerModel.Email = inputEmail.getText().toString();
        registerModel.Password = inputPassword.getText().toString();
        registerModel.ConfirmPassword = inputConfirmPwd.getText().toString();
        registerModel.Role = (String) spinnerRole.getSelectedItem();

        controller.RegisterUser(registerModel);
    }

    @Override
    public void DisplayResult(Object result) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}