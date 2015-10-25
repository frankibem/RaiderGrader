package android.app.rgs.com.raidergrader.activities.teacher;

import android.app.rgs.com.raidergrader.R;
import android.app.rgs.com.raidergrader.controllers.WorkItemController;
import android.app.rgs.com.raidergrader.models.CreateWorkItemModel;
import android.app.rgs.com.raidergrader.utilities.RgsTextWatcher;
import android.app.rgs.com.raidergrader.utilities.ValidateConstant;
import android.app.rgs.com.raidergrader.utilities.Validators;
import android.app.rgs.com.raidergrader.models.ControllerCallback;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


public class TeacherCreateWorkItemActivity extends AppCompatActivity implements ControllerCallback {

    private EditText inputTitle,
                     inputDescription,
                     inputmaxPoint;

    private TextInputLayout inputLayoutTitle,
                            inputLayoutDescription,
                            inputLayoutmaxPoint;

    private Button setTime,
                   setDate,
                   cancel,
                   done;

    private Spinner spinnerType;

    String[] types = {"Exam", "Quiz", "Homework", "Project", "Participation"};

    private WorkItemController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_create_work_item);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        controller = new WorkItemController(this,this);

        LoadReferences();
        SetValidators();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, types);
        spinnerType.setAdapter(adapter);

    }

    private void LoadReferences() {
        inputTitle = (EditText) findViewById(R.id.input_Title);
        inputDescription = (EditText) findViewById(R.id.input_Description);
        inputmaxPoint = (EditText) findViewById(R.id.input_Mpoint);
        inputLayoutTitle = (TextInputLayout) findViewById(R.id.input_layout_Title);
        inputLayoutDescription = (TextInputLayout) findViewById(R.id.input_layout_Description);
        inputLayoutmaxPoint = (TextInputLayout) findViewById(R.id.input_layout_Mpoint);

        setTime = (Button) findViewById(R.id.btn_setTime);
        setDate = (Button) findViewById(R.id.btn_setDate);
        cancel = (Button) findViewById(R.id.btn_cancel);
        done = (Button) findViewById(R.id.btn_done);

        spinnerType = (Spinner) findViewById(R.id.spinner_type);
    }

    private void SetValidators() {
        inputTitle.addTextChangedListener(new RgsTextWatcher(getWindow(), inputTitle,
                inputLayoutTitle, ValidateConstant.NON_EMPTY_TEXT));
        inputDescription.addTextChangedListener(new RgsTextWatcher(getWindow(), inputDescription,
                inputLayoutDescription, ValidateConstant.NON_EMPTY_TEXT));
        inputmaxPoint.addTextChangedListener(new RgsTextWatcher(getWindow(),  inputmaxPoint,
                inputLayoutmaxPoint, ValidateConstant.FLOAT));
    }

    /**
     * Returns true if all input fields pass validation
     */
    private boolean ValidateFields() {
        boolean noErrors = true;

        if (!Validators.validateNonEmptyText(inputTitle.getText().toString())) {
            noErrors = false;
            inputLayoutTitle.setError("Title should not be empty");
        }
        if (!Validators.validateNonEmptyText(inputDescription.getText().toString())) {
            noErrors = false;
            inputLayoutDescription.setError("Description should not be empty");
        }
        if (!Validators.validateFloat(inputmaxPoint.getText().toString())) {
            noErrors = false;
            inputLayoutmaxPoint.setError("Max point should be a floating number.");
        }
        return noErrors;
    }

    public void onClick(View v) {
        if (!ValidateFields()) {
            Toast.makeText(this, "Review your input", Toast.LENGTH_SHORT).show();
            return;
        }

        CreateWorkItemModel createWorkItemModel = new CreateWorkItemModel();
        createWorkItemModel.Title = inputTitle.getText().toString();
        createWorkItemModel.Description = inputDescription.getText().toString();
        createWorkItemModel.MaxPoints = Float.parseFloat(inputmaxPoint.getText().toString());

        controller.CreateWorkItem(createWorkItemModel);
    }

    @Override
    public void DisplayResult(Object result) {
        Intent intent = new Intent(this, TeacherUpdateWorkItemActivity.class);
        startActivity(intent);
    }

}
