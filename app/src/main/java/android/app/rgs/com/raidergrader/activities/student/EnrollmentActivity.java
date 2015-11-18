package android.app.rgs.com.raidergrader.activities.student;

import android.app.ProgressDialog;
import android.app.rgs.com.raidergrader.R;
import android.app.rgs.com.raidergrader.controllers.AccountController;
import android.app.rgs.com.raidergrader.controllers.ClassController;
import android.app.rgs.com.raidergrader.data_access.HttpStatusCodes;
import android.app.rgs.com.raidergrader.data_access.Repository;
import android.app.rgs.com.raidergrader.data_access.RequestError;
import android.app.rgs.com.raidergrader.data_access.RestTask;
import android.app.rgs.com.raidergrader.data_access.RestUtil;
import android.app.rgs.com.raidergrader.models.ControllerCallback;
import android.app.rgs.com.raidergrader.utilities.GlobalHandling;
import android.app.rgs.com.raidergrader.utilities.RgsTextWatcher;
import android.app.rgs.com.raidergrader.utilities.ValidateConstant;
import android.app.rgs.com.raidergrader.models.ClassModel;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;

import java.io.IOException;

public class EnrollmentActivity extends AppCompatActivity
        implements ControllerCallback<ClassModel> {

    private EditText inputClassId;
    private TextInputLayout inputLayoutClassId;
    private ClassController controller = new ClassController(this, this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enrollment);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        inputClassId = (EditText) findViewById(R.id.input_classid);
        inputLayoutClassId = (TextInputLayout) findViewById(R.id.input_layout_classid);

        inputClassId.addTextChangedListener(new RgsTextWatcher(getWindow(), inputClassId, inputLayoutClassId, ValidateConstant.INTEGER));
    }

    public void onClickSubmit(View v) {
//        if(!Validators.hasNoError(inputLayoutClassId)) {
//            return;
//        }

        String idString = inputClassId.getText().toString();
        int classId = Integer.parseInt(idString);
        controller.GetClassWithId(classId);
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

    @Override
    public void DisplayResult(ClassModel classModel) {
        Repository.setCurrentClass(classModel);

        Intent intent = new Intent(this, EnrollmentConfirmationActivity.class);
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 0) {
            finish();
        }
    }
}