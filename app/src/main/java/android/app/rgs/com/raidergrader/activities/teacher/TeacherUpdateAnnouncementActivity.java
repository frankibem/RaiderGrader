package android.app.rgs.com.raidergrader.activities.teacher;

import android.app.rgs.com.raidergrader.R;
import android.app.rgs.com.raidergrader.controllers.AccountController;
import android.app.rgs.com.raidergrader.controllers.AnnouncementController;
import android.app.rgs.com.raidergrader.data_access.Repository;
import android.app.rgs.com.raidergrader.models.AnnouncementModel;
import android.app.rgs.com.raidergrader.models.ControllerCallback;
import android.app.rgs.com.raidergrader.models.UpdateAnnouncementModel;
import android.app.rgs.com.raidergrader.utilities.RgsTextWatcher;
import android.app.rgs.com.raidergrader.utilities.ValidateConstant;
import android.app.rgs.com.raidergrader.utilities.Validators;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class TeacherUpdateAnnouncementActivity extends AppCompatActivity
        implements ControllerCallback<UpdateAnnouncementModel> {
    private EditText inputTitle, inputDescription;

    private TextInputLayout titleInputLayout, descriptionInputLayout;

    private Button updateButton;

    private AnnouncementController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_update_announcement);

        controller = new AnnouncementController(this, this);

        LoadReference();
        SetValidators();
        ValidateFields();
    }

    private void LoadReference() {
        titleInputLayout = (TextInputLayout) findViewById(R.id.announcement_title_layout_textbox);
        descriptionInputLayout = (TextInputLayout) findViewById(R.id.announcement_description_layout_textbox);

        inputTitle = (EditText) findViewById(R.id.announcement_title_textbox);
        inputDescription = (EditText) findViewById(R.id.announcement_description_textbox);

        updateButton = (Button) findViewById(R.id.update_announcement_button);
    }

    private void SetValidators() {
        inputTitle.addTextChangedListener(new RgsTextWatcher(getWindow(), inputTitle, titleInputLayout, ValidateConstant.NON_EMPTY_TEXT));
        inputDescription.addTextChangedListener(new RgsTextWatcher(getWindow(), inputDescription, descriptionInputLayout, ValidateConstant.NON_EMPTY_TEXT));

    }

    private boolean ValidateFields() {
        boolean noErrors = true;

        if (!Validators.validateNonEmptyText(inputTitle.getText().toString())){
            noErrors = false;
            titleInputLayout.setError("Title cannot be empty.");
        }

        if (!Validators.validateNonEmptyText(inputDescription.getText().toString())){
            noErrors = false;
            descriptionInputLayout.setError("Description cannot be empty.");
        }

        return noErrors;
    }

    public void onClickUpdate(View v){
        if (!ValidateFields()) {
            Toast.makeText(this, "Review your input", Toast.LENGTH_SHORT).show();
            return;
        }

        UpdateAnnouncementModel model = new UpdateAnnouncementModel();
        model.Title = inputTitle.getText().toString();
        model.Description = inputDescription.getText().toString();

        controller.UpdateAnnouncement(model);
    }

    @Override
    public void DisplayResult(UpdateAnnouncementModel result){

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.item_logout) {
            AccountController accountController = new AccountController(this, null);
            accountController.LogUserOut();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
