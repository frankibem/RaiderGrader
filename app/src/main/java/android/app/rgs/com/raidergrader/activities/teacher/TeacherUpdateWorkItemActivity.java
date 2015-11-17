package android.app.rgs.com.raidergrader.activities.teacher;

import android.app.rgs.com.raidergrader.R;
import android.app.rgs.com.raidergrader.controllers.WorkItemController;
import android.app.rgs.com.raidergrader.models.ControllerCallback;
import android.app.rgs.com.raidergrader.models.UpdateWorkItemModel;
import android.app.rgs.com.raidergrader.utilities.RgsTextWatcher;
import android.app.rgs.com.raidergrader.utilities.ValidateConstant;
import android.app.rgs.com.raidergrader.utilities.Validators;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class TeacherUpdateWorkItemActivity extends AppCompatActivity
        implements ControllerCallback<UpdateWorkItemModel> {

    private EditText inputTitle,
            inputDescription,
            inputDueDate,
            inputMaxPoints,
            inputType,
            inputClass;

    private TextInputLayout titleInputLayout,
            descriptionInputLayout,
            duedateInputLayout,
            maxpointsInputLayout,
            typeInputLayout,
            classInputLayout;

    private Button update_classBtn;

    private WorkItemController controller;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_update_work_item);

        controller = new WorkItemController(this,this);

        LoadReference();
        SetValidators();
        ValidateFields();
    }

    private void LoadReference(){

        titleInputLayout=(TextInputLayout) findViewById(R.id.title_layout_textbox);
        descriptionInputLayout=(TextInputLayout) findViewById(R.id.description_layout_textbox);
        duedateInputLayout=(TextInputLayout) findViewById(R.id.duedate_layout_textbox);
        maxpointsInputLayout=(TextInputLayout) findViewById(R.id.maxpoints_layout_textbox);
        typeInputLayout=(TextInputLayout) findViewById(R.id.type_layout_textbox);
        classInputLayout=(TextInputLayout) findViewById(R.id.class_layout_textbox);

        inputTitle=(EditText) findViewById(R.id.title_textbox);
        inputDescription =(EditText) findViewById(R.id.description_textbox);
        inputDueDate=(EditText) findViewById(R.id.duedate_textbox);
        inputMaxPoints=(EditText) findViewById(R.id.maxpoints_textbox);
        inputType=(EditText) findViewById(R.id.type_textbox);
        inputClass=(EditText) findViewById(R.id.class_textbox);

        update_classBtn=(Button) findViewById(R.id.update_class_btn);
    }

    private void SetValidators() {
        inputTitle.addTextChangedListener(new RgsTextWatcher(getWindow(), inputTitle,
                titleInputLayout, ValidateConstant.NON_EMPTY_TEXT));
        inputDescription.addTextChangedListener(new RgsTextWatcher(getWindow(), inputDescription,
                descriptionInputLayout, ValidateConstant.INTEGER));
        inputDueDate.addTextChangedListener(new RgsTextWatcher(getWindow(), inputDueDate,
                duedateInputLayout, ValidateConstant.NON_EMPTY_TEXT));
        inputMaxPoints.addTextChangedListener(new RgsTextWatcher(getWindow(),inputMaxPoints,
                maxpointsInputLayout, ValidateConstant.INTEGER));
        inputType.addTextChangedListener(new RgsTextWatcher(getWindow(),inputType,
                typeInputLayout, ValidateConstant.FLOAT));
        inputClass.addTextChangedListener(new RgsTextWatcher(getWindow(),inputClass,
                classInputLayout, ValidateConstant.FLOAT));

    }

    private boolean ValidateFields() {
        boolean noErrors = true;

        if (!Validators.validateNonEmptyText(inputTitle.getText().toString())) {
            noErrors = false;
            titleInputLayout.setError("Title should not be empty");
        }
        if (!Validators.validateInteger(inputDescription.getText().toString())) {
            noErrors = false;
            descriptionInputLayout.setError("Coursenumber should be filled with integers");
        }
        if (!Validators.validateNonEmptyText(inputDueDate.getText().toString())) {
            noErrors = false;
            duedateInputLayout.setError("Prefix should not be empty.");
        }
        if (!Validators.validateInteger(inputMaxPoints.getText().toString())) {
            noErrors = false;
            maxpointsInputLayout.setError("Section should be filled with integers");
        }
        if (!Validators.validateFloat(inputType.getText().toString())) {
            noErrors = false;
            typeInputLayout.setError("Exam percentage should be filled with decimal number");
        }
        if (!Validators.validateFloat(inputClass.getText().toString())) {
            noErrors = false;
            classInputLayout.setError("Project percentage should be filled with decimal number");
        }

        return noErrors;
    }

    public void onClickUpdate(View v){
        if (!ValidateFields()) {
            Toast.makeText(this, "Review your input", Toast.LENGTH_SHORT).show();
            return;
        }

        UpdateWorkItemModel updateWorkItemModel = new UpdateWorkItemModel();
        updateWorkItemModel.Title = inputTitle.getText().toString();
        updateWorkItemModel.Description = inputDescription.getText().toString();
        updateWorkItemModel.DueDate = inputDueDate.getText().toString();
        updateWorkItemModel.MaxPoints = Integer.parseInt(inputMaxPoints.getText().toString());

        controller.GetWorkItemsForClass(R.id.class);

    }

    @Override
    public void DisplayResult(UpdateWorkItemModel result) {

    }
}
