package android.app.rgs.com.raidergrader.activities.teacher;

import android.app.rgs.com.raidergrader.R;
import android.app.rgs.com.raidergrader.adapters.WaitListAdapter;
import android.app.rgs.com.raidergrader.controllers.AccountController;
import android.app.rgs.com.raidergrader.controllers.EnrollmentController;
import android.app.rgs.com.raidergrader.data_access.Repository;
import android.app.rgs.com.raidergrader.models.ControllerCallback;
import android.app.rgs.com.raidergrader.models.EnrollmentBindingModel;
import android.app.rgs.com.raidergrader.models.EnrollmentModel;
import android.app.rgs.com.raidergrader.models.EnrollmentState;
import android.app.rgs.com.raidergrader.models.WaitListItem;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class TeacherClassWaitlistActivity extends AppCompatActivity
        implements ControllerCallback<List<EnrollmentModel>> {
    public static List<EnrollmentModel> enrollments;
    public static List<WaitListItem> viewModels;

    private EnrollmentController controller = new EnrollmentController(this, this);
    private ListView listView;
    private WaitListAdapter adapter;
    private TextView emptyText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_class_waitlist);

        emptyText = (TextView) findViewById(R.id.text_empty);
        listView = (ListView) findViewById(R.id.listView);
        fetchData();
    }

    public void fetchData() {
        if (viewModels == null) {
            controller.GetPendingEnrollments(Repository.getCurrentClass().Id);
        } else {
            populateList();
        }
    }

    /**
     * Populate the listView
     */
    private void populateList() {
        if (viewModels.isEmpty()) {
            emptyText.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
            return;
        }

        emptyText.setVisibility(View.GONE);
        listView.setVisibility(View.VISIBLE);

        adapter = new WaitListAdapter(this, viewModels);
        listView.setAdapter(adapter);
    }

    /**
     * Converts the view-models to models and requests an update
     *
     * @param view The view that was clicked
     */
    public void onDoneClick(View view) {
        List<EnrollmentBindingModel> models = new ArrayList<>();
        for (WaitListItem viewModel : adapter.getViewModels()) {
            // Update only those that have been accepted or rejected
            switch (viewModel.State){
                case ACCEPT:
                case REJECT:
                    EnrollmentBindingModel model = new EnrollmentBindingModel();
                    model.ClassId = Repository.getCurrentClass().Id;
                    model.StudentUserName = viewModel.Email;
                    if(viewModel.State == EnrollmentState.ACCEPT){
                        model.Accept = true;
                    }else{
                        model.Accept = false;
                    }

                    models.add(model);
                    break;
            }
        }

        controller.AcceptStudentEnrollment(models);
    }

    @Override
    public void finish() {
        viewModels = null;
        enrollments = null;
        super.finish();
    }

    @Override
    public void DisplayResult(List<EnrollmentModel> result) {
        enrollments = result;
        viewModels = new ArrayList<>();
        for (EnrollmentModel model : enrollments) {
            viewModels.add(new WaitListItem(model));
        }
        populateList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.refresh_logout_menu, menu);
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
        }else if(id == R.id.menu_refresh){
            viewModels = null;
            fetchData();
        }

        return super.onOptionsItemSelected(item);
    }
}