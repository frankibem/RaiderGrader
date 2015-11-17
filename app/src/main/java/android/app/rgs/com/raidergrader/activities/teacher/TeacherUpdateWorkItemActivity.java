package android.app.rgs.com.raidergrader.activities.teacher;

import android.app.rgs.com.raidergrader.R;
import android.app.rgs.com.raidergrader.controllers.ClassController;
import android.app.rgs.com.raidergrader.models.ControllerCallback;
import android.app.rgs.com.raidergrader.models.GradeDistribution;
import android.app.rgs.com.raidergrader.models.UpdateClassModel;
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
            DescriptionInputLayout,
            DueDateInputLayout,
            MaxPointsInputLayout,
            TypeInputLayout,
            ClassInputLayout;

    private Button update_classBtn;

    private ClassController controller;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_update_work_item);

        controller = new ClassController(this,this);

        LoadReference();
        SetValidators();
        ValidateFields();
    }

    private void LoadReference(){

        titleInputLayout=(TextInputLayout) findViewById(R.id.title_layout_textbox);
        DescriptionInputLayout=(TextInputLayout) findViewById(R.id.description_layout_textbox);
        DueDateInputLayout=(TextInputLayout) findViewById(R.id.duedate_layout_textbox);
        MaxPointsInputLayout=(TextInputLayout) findViewById(R.id.maxpoints_layout_textbox);
        TypeInputLayout=(TextInputLayout) findViewById(R.id.type_layout_textbox);
        ClassInputLayout=(TextInputLayout) findViewById(R.id.class_layout_textbox);

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
        if (!Validators.validateFloat(inputExam.getText().toString())) {
            noErrors = false;
            examInputLayout.setError("Exam percentage should be filled with decimal number");
        }
        if (!Validators.validateFloat(inputProject.getText().toString())) {
            noErrors = false;
            projectInputLayout.setError("Project percentage should be filled with decimal number");
        }
        if (!Validators.validateFloat(inputQuiz.getText().toString())) {
            noErrors = false;
            quizInputLayout.setError("Quiz percentage should be filled with decimal number");
        }
        if (!Validators.validateFloat(inputHomework.getText().toString())) {
            noErrors = false;
            homeworkInputLayout.setError("Homework percentage should be filled with decimal number");
        }

        if (!Validators.validateFloat(inputOther.getText().toString())) {
            noErrors = false;
            otherInputLayout.setError("Other percentage should be filled with decimal number");
        }

        return noErrors;
    }

    public void onClickUpdate(View v){
        if (!ValidateFields()) {
            Toast.makeText(this, "Review your input", Toast.LENGTH_SHORT).show();
            return;
        }

        UpdateClassModel updateClassModel = new UpdateClassModel();
        updateClassModel.Title = inputTitle.getText().toString();
        updateClassModel.CourseNumber = Integer.parseInt(inputCourseNumber.getText().toString());
        updateClassModel.Prefix = inputPrefix.getText().toString();
        updateClassModel.Section = Integer.parseInt(inputSection.getText().toString());

        GradeDistribution gradeDistribution= new GradeDistribution();
        gradeDistribution.Exam = Float.parseFloat(inputExam.getText().toString());
        gradeDistribution.Project=Float.parseFloat(inputProject.getText().toString());
        gradeDistribution.Homework = Float.parseFloat(inputHomework.getText().toString());
        gradeDistribution.Quiz=Float.parseFloat(inputQuiz.getText().toString());
        gradeDistribution.Other=Float.parseFloat(inputOther.getText().toString());

        updateClassModel.GradeDistribution=gradeDistribution;
        controller.UpdateClass(updateClassModel);

    }

    @Override
    public void DisplayResult(UpdateClassModel result) {

    }
}
