package android.app.rgs.com.raidergrader.activities.teacher;

import android.app.rgs.com.raidergrader.R;
import android.app.rgs.com.raidergrader.controllers.WorkItemController;
import android.app.rgs.com.raidergrader.data_access.Repository;
import android.app.rgs.com.raidergrader.dialogs.DatePickerFragment;
import android.app.rgs.com.raidergrader.dialogs.TimePickerFragment;
import android.app.rgs.com.raidergrader.models.ControllerCallback;
import android.app.rgs.com.raidergrader.models.OnDatePickedListener;
import android.app.rgs.com.raidergrader.models.OnTimePickedListener;
import android.app.rgs.com.raidergrader.models.UpdateWorkItemModel;
import android.app.rgs.com.raidergrader.models.WorkItemModel;
import android.app.rgs.com.raidergrader.utilities.RgsTextWatcher;
import android.app.rgs.com.raidergrader.utilities.TimeUtils;
import android.app.rgs.com.raidergrader.utilities.ValidateConstant;
import android.app.rgs.com.raidergrader.utilities.Validators;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;

import java.util.Calendar;
import java.util.Locale;

public class TeacherUpdateWorkItemActivity extends AppCompatActivity
        implements ControllerCallback, OnDatePickedListener, OnTimePickedListener {

    private EditText inputTitle,
            inputDescription,
            inputMaxPoints;

    private TextInputLayout inputLayoutTitle,
            inputLayoutDescription,
            inputLayoutmaxPoint;

    private Button btnUpdate,
            btnTime,
            btnDate,
            btnCancel;

    private int HOUR, MINUTE,
            YEAR, MONTH, DAY;

    private Spinner spinnerType;
    private String[] types = {"Exam", "Quiz", "Homework", "Project", "Other"};
    private WorkItemController controller;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_update_work_item);

        controller = new WorkItemController(this,this);

        LoadReference();
        SetValidators();
        setDateTime();
        setFields();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, types);
        spinnerType.setAdapter(adapter);
    }

    private void setFields(){
        WorkItemModel currentWorkItem = Repository.getCurrentWorkItem();
        inputTitle.setText(currentWorkItem.Title);
        inputDescription.setText(currentWorkItem.Description);
        inputMaxPoints.setText(Float.toString(currentWorkItem.MaxPoints));
        spinnerType.setSelection(currentWorkItem.Type);
    }

    private void setDateTime() {
        Calendar calendar = Calendar.getInstance();
        HOUR = calendar.get(Calendar.HOUR_OF_DAY);
        MINUTE = calendar.get(Calendar.MINUTE);
        YEAR = calendar.get(Calendar.YEAR);
        MONTH = calendar.get(Calendar.MONTH);
        DAY = calendar.get(Calendar.DAY_OF_MONTH);
    }

    private void LoadReference(){

        inputTitle=(EditText) findViewById(R.id.input_Title);
        inputDescription =(EditText) findViewById(R.id.input_Description);
        inputMaxPoints=(EditText) findViewById(R.id.input_maxpoint);
        inputLayoutTitle=(TextInputLayout) findViewById(R.id.input_layout_Title);
        inputLayoutDescription =(TextInputLayout) findViewById(R.id.input_layout_Description);
        inputLayoutmaxPoint=(TextInputLayout) findViewById(R.id.input_layout_maxpoint);

        btnTime = (Button) findViewById(R.id.btn_setTime);
        btnDate = (Button) findViewById(R.id.btn_setDate);
        btnCancel = (Button) findViewById(R.id.btn_cancel);
        btnUpdate = (Button) findViewById(R.id.btn_update);

        spinnerType = (Spinner) findViewById(R.id.spinner_type);

    }

    private void SetValidators() {
        inputTitle.addTextChangedListener(new RgsTextWatcher(getWindow(), inputTitle,
                inputLayoutTitle, ValidateConstant.NON_EMPTY_TEXT));
        inputDescription.addTextChangedListener(new RgsTextWatcher(getWindow(), inputDescription,
                inputLayoutDescription, ValidateConstant.NON_EMPTY_TEXT));
        inputMaxPoints.addTextChangedListener(new RgsTextWatcher(getWindow(),inputMaxPoints,
                inputLayoutmaxPoint, ValidateConstant.FLOAT));


    }

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

        if (!Validators.validateFloat(inputMaxPoints.getText().toString())) {
            noErrors = false;
            inputLayoutmaxPoint.setError("Max points should be a decimal number");
        }

        return noErrors;
    }

    public void onClickSetTime(View v) {
        TimePickerFragment timeFragment = new TimePickerFragment();
        timeFragment.setStartTime(HOUR, MINUTE);
        timeFragment.setTimeListener(this);
        timeFragment.show(getSupportFragmentManager(), "timePicker");
    }

    public void onClickSetDate(View v) {
        DatePickerFragment dateFragment = new DatePickerFragment();
        dateFragment.setStartDate(YEAR, MONTH, DAY);
        dateFragment.setDateListener(this);
        dateFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void onClickCancel(View v) {
        finish();
    }

    public void onClickUpdate(View v){
        if (!ValidateFields()) {
            Toast.makeText(this, "Review your input", Toast.LENGTH_SHORT).show();
            return;
        }

        UpdateWorkItemModel updateWorkItemModel = new UpdateWorkItemModel();
        updateWorkItemModel.Title = inputTitle.getText().toString();
        updateWorkItemModel.Description = inputDescription.getText().toString();
        updateWorkItemModel.MaxPoints = Float.parseFloat(inputMaxPoints.getText().toString());
        updateWorkItemModel.Id = Repository.getCurrentWorkItem().Id;

        LocalDateTime dateTime = TimeUtils.LocalDateTimeFromComponents(YEAR, MONTH, DAY, HOUR, MINUTE);
        updateWorkItemModel.DueDate = TimeUtils.ToISO8601String(dateTime);

        String type = (String) spinnerType.getSelectedItem();
        int wiType;
        switch (type) {
            case "Exam":
                wiType = 0;
                break;
            case "Quiz":
                wiType = 1;
                break;
            case "Homework":
                wiType = 2;
                break;
            case "Project":
                wiType = 3;
                break;
            case "Other":
                wiType = 4;
                break;
            default:
                wiType = -1;
        }

        if (wiType != -1) {
            updateWorkItemModel.Type = wiType;
        }

        controller.UpdateWorkItem(updateWorkItemModel);

    }

    @Override
    public void DisplayResult(Object result) {
        Intent intent = new Intent(this, TeacherWorkItemListActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onDatePicked(int year, int month, int dayOfMonth) {
        YEAR = year;
        MONTH = month+1;
        DAY = dayOfMonth;
        LocalDateTime dateTime = TimeUtils.LocalDateTimeFromComponents(YEAR, MONTH, DAY, HOUR, MINUTE);
        LocalDate date = new LocalDate(dateTime);
        btnDate.setText(date.toString("MMM d, yyyy", Locale.getDefault()));
    }

    @Override
    public void onTimePicked(int hour, int minute) {
        HOUR = hour;
        MINUTE = minute;
        LocalDateTime dateTime = TimeUtils.LocalDateTimeFromComponents(YEAR, MONTH, DAY, HOUR, MINUTE);
        LocalTime time = new LocalTime(dateTime);
        btnTime.setText(time.toString("HH:mm a", Locale.getDefault()));
    }


}
