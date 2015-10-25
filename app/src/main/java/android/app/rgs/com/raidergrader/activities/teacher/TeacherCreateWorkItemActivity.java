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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class TeacherCreateWorkItemActivity extends AppCompatActivity implements ControllerCallback {

    private EditText inputTitle,
                     inputDescription,
                     inputdueDate,
                     inputmaxPoint,
                     inputclassId,
                     inputType;

    private TextInputLayout inputLayoutTitle,
                            inputLayoutDescription,
                            inputLayoutdueDate,
                            inputLayoutmaxPoint,
                            inputLayoutclassId,
                            inputLayoutType;

    private Button btnCreate;

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

    }

    private void LoadReferences() {
        inputTitle = (EditText) findViewById(R.id.input_Title);
        inputDescription = (EditText) findViewById(R.id.input_Description);
        inputdueDate = (EditText) findViewById(R.id.input_Ddate);
        inputmaxPoint = (EditText) findViewById(R.id.input_Mpoint);
        inputclassId = (EditText) findViewById(R.id.input_classid);
        inputType = (EditText) findViewById(R.id.input_Type);

        inputLayoutTitle = (TextInputLayout) findViewById(R.id.input_layout_Title);
        inputLayoutDescription = (TextInputLayout) findViewById(R.id.input_layout_Description);
        inputLayoutdueDate = (TextInputLayout) findViewById(R.id.input_layout_Ddate);
        inputLayoutmaxPoint = (TextInputLayout) findViewById(R.id.input_layout_Mpoint);
        inputLayoutclassId = (TextInputLayout) findViewById(R.id.input_layout_ClassId);
        inputLayoutType = (TextInputLayout) findViewById(R.id.input_layout_Type);

        btnCreate = (Button) findViewById(R.id.btn_create);
    }

    private void SetValidators() {
        inputTitle.addTextChangedListener(new RgsTextWatcher(getWindow(), inputTitle,
                inputLayoutTitle, ValidateConstant.NON_EMPTY_TEXT));
        inputDescription.addTextChangedListener(new RgsTextWatcher(getWindow(), inputDescription,
                inputLayoutDescription, ValidateConstant.NON_EMPTY_TEXT));
        inputdueDate.addTextChangedListener(new RgsTextWatcher(getWindow(), inputdueDate,
                inputLayoutdueDate, ValidateConstant.NON_EMPTY_TEXT));
        inputmaxPoint.addTextChangedListener(new RgsTextWatcher(getWindow(),  inputmaxPoint,
                inputLayoutmaxPoint, ValidateConstant.FLOAT));
        inputclassId.addTextChangedListener(new RgsTextWatcher(getWindow(), inputclassId,
                inputLayoutclassId, ValidateConstant.INTEGER));
        inputType.addTextChangedListener(new RgsTextWatcher(getWindow(), inputType,
                inputLayoutType, ValidateConstant.INTEGER));
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
        if (!Validators.validateNonEmptyText(inputdueDate.getText().toString())) {
            noErrors = false;
            inputLayoutdueDate.setError("Due date should not be empty");
        }
        if (!Validators.validateFloat(inputmaxPoint.getText().toString())) {
            noErrors = false;
            inputLayoutmaxPoint.setError("Max point should be a floating number.");
        }
        if (!Validators.validateInteger(inputclassId.getText().toString())) {
            noErrors = false;
            inputLayoutclassId.setError("Class ID should be an integer.");
        }
        if (!Validators.validateInteger(inputType.getText().toString())) {
            noErrors = false;
            inputLayoutType.setError("Type should be an integer.");
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
        createWorkItemModel.DueDate = inputdueDate.getText().toString();
        createWorkItemModel.MaxPoints = Float.parseFloat(inputmaxPoint.getText().toString());
        createWorkItemModel.ClassId = Integer.parseInt(inputclassId.getText().toString());
        createWorkItemModel.Type = Integer.parseInt(inputType.getText().toString());

        controller.CreateWorkItem(createWorkItemModel);
    }

    @Override
    public void DisplayResult(Object result) {
        Intent intent = new Intent(this, TeacherUpdateWorkItemActivity.class);
        startActivity(intent);
    }

}
