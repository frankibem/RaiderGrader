package android.app.rgs.com.raidergrader.activities.teacher;

import android.app.rgs.com.raidergrader.R;
import android.app.rgs.com.raidergrader.controllers.ClassController;
import android.app.rgs.com.raidergrader.models.ControllerCallback;
import android.app.rgs.com.raidergrader.models.GradeDistribution;
import android.app.rgs.com.raidergrader.models.UpdateClassModel;
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

public class TeacherUpdateClassActivity extends AppCompatActivity
        implements ControllerCallback<UpdateClassModel> {

    private EditText inputTitle,
            inputCourseNumber,
            inputPrefix,
            inputSection,
            inputExam,
            inputProject,
            inputQuiz,
            inputHomework,
            inputOther;

    private TextInputLayout titleInputLayout,
            courseInputLayout,
            prefixInputLayout,
            sectionInputLayout,
            examInputLayout,
            projectInputLayout,
            quizInputLayout,
            homeworkInputLayout,
            otherInputLayout;

    private Button update_classBtn;

    private ClassController controller;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_update_class);

        controller = new ClassController(this,this);

        LoadReference();
        SetValidators();
        ValidateFields();
    }

    private void LoadReference(){

        courseInputLayout=(TextInputLayout)findViewById(R.id.courseNumber_layout_textbox);
        titleInputLayout=(TextInputLayout) findViewById(R.id.classname_layout_textbox);
        prefixInputLayout=(TextInputLayout) findViewById(R.id.prefix_layout_textbox);
        sectionInputLayout=(TextInputLayout) findViewById(R.id.section_layout_textbox);
        examInputLayout=(TextInputLayout) findViewById(R.id.exam_layout_textbox);
        projectInputLayout=(TextInputLayout) findViewById(R.id.project_layout_textbox);
        quizInputLayout=(TextInputLayout) findViewById(R.id.quiz_layout_textbox);
        homeworkInputLayout=(TextInputLayout) findViewById(R.id.homework_layout_textbox);
        otherInputLayout=(TextInputLayout) findViewById(R.id.other_layout_textbox);

        inputTitle=(EditText) findViewById(R.id.classname_textbox);
        inputCourseNumber =(EditText) findViewById(R.id.courseNumber_textbox);
        inputPrefix=(EditText) findViewById(R.id.prefix_textbox);
        inputSection=(EditText) findViewById(R.id.section_textbox);
        inputExam=(EditText) findViewById(R.id.exam_textbox);
        inputProject=(EditText) findViewById(R.id.project_textbox);
        inputQuiz=(EditText) findViewById(R.id.quiz_textbox);
        inputHomework=(EditText)findViewById(R.id.homework_textbox);
        inputOther=(EditText) findViewById(R.id.other_textbox);

        update_classBtn=(Button) findViewById(R.id.update_class_btn);
    }

    private void SetValidators() {
        inputTitle.addTextChangedListener(new RgsTextWatcher(getWindow(), inputTitle,
                titleInputLayout, ValidateConstant.NON_EMPTY_TEXT));
        inputCourseNumber.addTextChangedListener(new RgsTextWatcher(getWindow(), inputCourseNumber,
                courseInputLayout, ValidateConstant.INTEGER));
        inputPrefix.addTextChangedListener(new RgsTextWatcher(getWindow(), inputPrefix,
                prefixInputLayout, ValidateConstant.NON_EMPTY_TEXT));
        inputSection.addTextChangedListener(new RgsTextWatcher(getWindow(),inputSection,
                sectionInputLayout, ValidateConstant.INTEGER));
        inputExam.addTextChangedListener(new RgsTextWatcher(getWindow(),inputExam,
                examInputLayout, ValidateConstant.FLOAT));
        inputProject.addTextChangedListener(new RgsTextWatcher(getWindow(),inputProject,
                projectInputLayout, ValidateConstant.FLOAT));
        inputQuiz.addTextChangedListener(new RgsTextWatcher(getWindow(),inputQuiz,
                quizInputLayout, ValidateConstant.FLOAT));
        inputHomework.addTextChangedListener(new RgsTextWatcher(getWindow(),inputHomework,
                homeworkInputLayout, ValidateConstant.FLOAT));
        inputOther.addTextChangedListener(new RgsTextWatcher(getWindow(),inputOther,
                otherInputLayout, ValidateConstant.FLOAT));


    }

    private boolean ValidateFields() {
        boolean noErrors = true;

        if (!Validators.validateNonEmptyText(inputTitle.getText().toString())) {
            noErrors = false;
            titleInputLayout.setError("Title should not be empty");
        }
        if (!Validators.validateInteger(inputCourseNumber.getText().toString())) {
            noErrors = false;
            courseInputLayout.setError("Coursenumber should be filled with integers");
        }
        if (!Validators.validateNonEmptyText(inputPrefix.getText().toString())) {
            noErrors = false;
            prefixInputLayout.setError("Prefix should not be empty.");
        }
        if (!Validators.validateInteger(inputSection.getText().toString())) {
            noErrors = false;
            sectionInputLayout.setError("Section should be filled with integers");
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
