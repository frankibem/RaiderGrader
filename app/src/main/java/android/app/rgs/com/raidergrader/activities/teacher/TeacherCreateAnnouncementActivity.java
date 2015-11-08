package android.app.rgs.com.raidergrader.activities.teacher;

import android.app.rgs.com.raidergrader.R;
import android.app.rgs.com.raidergrader.controllers.AnnouncementController;
import android.app.rgs.com.raidergrader.data_access.Repository;
import android.app.rgs.com.raidergrader.models.ControllerCallback;
import android.app.rgs.com.raidergrader.models.CreateAnnouncementModel;
import android.app.rgs.com.raidergrader.utilities.RgsTextWatcher;
import android.app.rgs.com.raidergrader.utilities.ValidateConstant;
import android.app.rgs.com.raidergrader.utilities.Validators;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class TeacherCreateAnnouncementActivity extends AppCompatActivity implements ControllerCallback {

    private EditText inputTitle,
            inputDescription;

    private TextInputLayout inputLayoutTitle,
            inputLayoutDescription;

    private AnnouncementController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_create_announcement);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        controller = new AnnouncementController(this, this);
        LoadReferences();
        SetValidators();
    }

    private void LoadReferences() {
        inputTitle = (EditText) findViewById(R.id.input_Title);
        inputDescription = (EditText) findViewById(R.id.input_Description);
        inputLayoutTitle = (TextInputLayout) findViewById(R.id.input_layout_Title);
        inputLayoutDescription = (TextInputLayout) findViewById(R.id.input_layout_Description);

    }

    private void SetValidators() {
        inputTitle.addTextChangedListener(new RgsTextWatcher(getWindow(), inputTitle,
                inputLayoutTitle, ValidateConstant.NON_EMPTY_TEXT));
        inputDescription.addTextChangedListener(new RgsTextWatcher(getWindow(), inputDescription,
                inputLayoutDescription, ValidateConstant.NON_EMPTY_TEXT));
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
        return noErrors;
    }

    public void onClickCancel(View v) {
        finish();
    }

    public void onClickDone(View v) {
        if (!ValidateFields()) {
            Toast.makeText(this, "Review your input", Toast.LENGTH_SHORT).show();
            return;
        }
        CreateAnnouncementModel createAnnouncementModel = new CreateAnnouncementModel();
        createAnnouncementModel.Title = inputTitle.getText().toString();
        createAnnouncementModel.Description = inputDescription.getText().toString();
        createAnnouncementModel.ClassId = Repository.getCurrentClass().Id;

        controller.CreateAnnouncement(createAnnouncementModel);
    }

    public void DisplayResult(Object result) {
        Intent intent = new Intent(this, TeacherAnnouncementListActivity.class);
        startActivity(intent);
    }
}
