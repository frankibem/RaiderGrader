package android.app.rgs.com.raidergrader.activities.teacher;

import android.app.rgs.com.raidergrader.R;
import android.app.rgs.com.raidergrader.adapters.WaitListAdapter;
import android.app.rgs.com.raidergrader.controllers.EnrollmentController;
import android.app.rgs.com.raidergrader.data_access.Repository;
import android.app.rgs.com.raidergrader.models.ControllerCallback;
import android.app.rgs.com.raidergrader.models.EnrollmentBindingModel;
import android.app.rgs.com.raidergrader.models.EnrollmentModel;
import android.app.rgs.com.raidergrader.models.EnrollmentState;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class TeacherClassWaitlistActivity extends AppCompatActivity
        implements ControllerCallback<List<EnrollmentModel>> {

    private EnrollmentController controller = new EnrollmentController(this, this);
    private static List<EnrollmentModel> enrollments;
    private static List<AcceptEnrollmentViewModel> viewModels;
    private ListView listView;
    private WaitListAdapter adapter;

    public class AcceptEnrollmentViewModel {
        public String Name;
        public String Email;
        public boolean Accept;
        public EnrollmentState State;

        /**
         * View-model for EnrollmentModels
         *
         * @param model EnrollmentModel to be converted to a view-model
         */
        public AcceptEnrollmentViewModel(EnrollmentModel model) {
            Accept = !model.Pending;
            Email = model.Student.UserName;
            Name = String.format("%s, %s", model.Student.LastName, model.Student.FirstName);
            State = EnrollmentState.UNKNOWN;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_class_waitlist);

        listView = (ListView) findViewById(R.id.listView);
        refresh();
    }

    public void refresh() {
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
        for (AcceptEnrollmentViewModel viewModel : adapter.getViewModels()) {
            models.add(new EnrollmentBindingModel(viewModel.Accept, Repository.getCurrentClass().Id, viewModel.Email));
        }

        controller.AcceptStudentEnrollment(models);
    }

    @Override
    public void DisplayResult(List<EnrollmentModel> result) {
        enrollments = result;
        viewModels = new ArrayList<>();
        for (EnrollmentModel model : enrollments) {
            viewModels.add(new AcceptEnrollmentViewModel(model));
        }
    }
}