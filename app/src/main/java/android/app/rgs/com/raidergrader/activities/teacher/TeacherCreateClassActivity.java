package android.app.rgs.com.raidergrader.activities.teacher;

import android.app.rgs.com.raidergrader.R;
import android.app.rgs.com.raidergrader.controllers.AccountController;
import android.app.rgs.com.raidergrader.controllers.ClassController;
import android.app.rgs.com.raidergrader.data_access.Repository;
import android.app.rgs.com.raidergrader.dialogs.ClassCreatedFragment;
import android.app.rgs.com.raidergrader.models.ClassModel;
import android.app.rgs.com.raidergrader.models.ControllerCallback;
import android.app.rgs.com.raidergrader.models.CreateClassModel;
import android.app.rgs.com.raidergrader.models.GradeDistribution;
import android.app.rgs.com.raidergrader.utilities.RgsTextWatcher;
import android.app.rgs.com.raidergrader.utilities.ValidateConstant;
import android.app.rgs.com.raidergrader.utilities.Validators;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * @author Joshua Hernandez
 */

public class TeacherCreateClassActivity extends AppCompatActivity
        implements ControllerCallback<ClassModel> {
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
    private ClassController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_create_class);

        LoadReferences();
        SetValidators();
        controller = new ClassController(this, this);
    }

    private void LoadReferences() {
        courseInputLayout = (TextInputLayout) findViewById(R.id.inputLayoutCourseNumber);
        titleInputLayout = (TextInputLayout) findViewById(R.id.inputLayoutTitle);
        prefixInputLayout = (TextInputLayout) findViewById(R.id.inputLayoutPrefix);
        sectionInputLayout = (TextInputLayout) findViewById(R.id.inputLayoutSection);
        examInputLayout = (TextInputLayout) findViewById(R.id.inputLayoutExam);
        projectInputLayout = (TextInputLayout) findViewById(R.id.inputLayoutProject);
        quizInputLayout = (TextInputLayout) findViewById(R.id.inputLayoutQuiz);
        homeworkInputLayout = (TextInputLayout) findViewById(R.id.inputLayoutHomework);
        otherInputLayout = (TextInputLayout) findViewById(R.id.inputLayoutOther);

        inputTitle = (EditText) findViewById(R.id.inputTitle);
        inputCourseNumber = (EditText) findViewById(R.id.inputCourseNumber);
        inputPrefix = (EditText) findViewById(R.id.inputPrefix);
        inputSection = (EditText) findViewById(R.id.inputSection);
        inputExam = (EditText) findViewById(R.id.inputExam);
        inputProject = (EditText) findViewById(R.id.inputProject);
        inputQuiz = (EditText) findViewById(R.id.inputQuiz);
        inputHomework = (EditText) findViewById(R.id.inputHomework);
        inputOther = (EditText) findViewById(R.id.inputOther);
    }

    private void SetValidators() {
        inputTitle.addTextChangedListener(new RgsTextWatcher(getWindow(), inputTitle,
                titleInputLayout, ValidateConstant.NON_EMPTY_TEXT));
        inputCourseNumber.addTextChangedListener(new RgsTextWatcher(getWindow(), inputCourseNumber,
                courseInputLayout, ValidateConstant.INTEGER));
        inputPrefix.addTextChangedListener(new RgsTextWatcher(getWindow(), inputPrefix,
                prefixInputLayout, ValidateConstant.NON_EMPTY_TEXT));
        inputSection.addTextChangedListener(new RgsTextWatcher(getWindow(), inputSection,
                sectionInputLayout, ValidateConstant.INTEGER));
        inputExam.addTextChangedListener(new RgsTextWatcher(getWindow(), inputExam,
                examInputLayout, ValidateConstant.FLOAT));
        inputProject.addTextChangedListener(new RgsTextWatcher(getWindow(), inputProject,
                projectInputLayout, ValidateConstant.FLOAT));
        inputQuiz.addTextChangedListener(new RgsTextWatcher(getWindow(), inputQuiz,
                quizInputLayout, ValidateConstant.FLOAT));
        inputHomework.addTextChangedListener(new RgsTextWatcher(getWindow(), inputHomework,
                homeworkInputLayout, ValidateConstant.FLOAT));
        inputOther.addTextChangedListener(new RgsTextWatcher(getWindow(), inputOther,
                otherInputLayout, ValidateConstant.FLOAT));
    }

    /**
     * Returns true if all field values validate successfully, otherwise false.
     * @return
     */
    private boolean ValidateFields() {
        boolean noErrors = true;

        if (!Validators.validateNonEmptyText(inputTitle.getText().toString())) {
            noErrors = false;
            titleInputLayout.setError("Title should not be empty");
        }
        if (!Validators.validateInteger(inputCourseNumber.getText().toString())) {
            noErrors = false;
            courseInputLayout.setError("Course number should be filled with integers");
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

    public void onClick(View v) {
        if (!ValidateFields()) {
            Toast.makeText(this, "Review your input", Toast.LENGTH_SHORT).show();
            return;
        }

        GradeDistribution gradeDistribution = new GradeDistribution();
        gradeDistribution.Exam = Float.parseFloat(inputExam.getText().toString());
        gradeDistribution.Project = Float.parseFloat(inputProject.getText().toString());
        gradeDistribution.Homework = Float.parseFloat(inputHomework.getText().toString());
        gradeDistribution.Quiz = Float.parseFloat(inputQuiz.getText().toString());
        gradeDistribution.Other = Float.parseFloat(inputOther.getText().toString());

        CreateClassModel createClassModel = new CreateClassModel();
        createClassModel.Title = inputTitle.getText().toString();
        createClassModel.CourseNumber = Integer.parseInt(inputCourseNumber.getText().toString());
        createClassModel.Prefix = inputPrefix.getText().toString();
        createClassModel.Section = Integer.parseInt(inputSection.getText().toString());
        createClassModel.TeacherUserName = Repository.USERNAME;
        createClassModel.GradeDistribution = gradeDistribution;

        controller.CreateClass(createClassModel);
    }

    @Override
    public void DisplayResult(ClassModel result) {
        ClassCreatedFragment fragment = new ClassCreatedFragment();
        fragment.setModel(result);
        fragment.show(getSupportFragmentManager(), "class_created");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_delete_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_logout) {
            AccountController accountController = new AccountController(this, null);
            accountController.LogUserOut();
            return true;
        } else if (id == R.id.menu_edit) {
            // Navigate to activity for deleting work item

        } else if (id == R.id.menu_delete) {
            // Place code to delete work item here
        }

        return super.onOptionsItemSelected(item);
    }
}