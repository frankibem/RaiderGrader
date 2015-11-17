package android.app.rgs.com.raidergrader.activities.student;

import android.app.rgs.com.raidergrader.R;
import android.app.rgs.com.raidergrader.adapters.StudentEnrollmentListAdapter;
import android.app.rgs.com.raidergrader.controllers.AccountController;
import android.app.rgs.com.raidergrader.controllers.EnrollmentController;
import android.app.rgs.com.raidergrader.data_access.Repository;
import android.app.rgs.com.raidergrader.dialogs.EnrollmentPendingFragment;
import android.app.rgs.com.raidergrader.models.ControllerCallback;
import android.app.rgs.com.raidergrader.models.EnrollmentModel;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class StudentClassListActivity extends AppCompatActivity
        implements ControllerCallback<List<EnrollmentModel>> {
    ListView listView;
    EnrollmentController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_class_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        controller = new EnrollmentController(this, this);

        listView = (ListView) findViewById(R.id.listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EnrollmentModel model = (EnrollmentModel) parent.getItemAtPosition(position);
                if (model.Pending) {
                    EnrollmentPendingFragment dialog = new EnrollmentPendingFragment();
                    dialog.setModel(model);
                    dialog.show(getSupportFragmentManager(), "pending_enrollment");
                    return;
                }

                Repository.setCurrentClass(model.Class);
                Intent intent = new Intent(getApplicationContext(), StudentClassItemsActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        fetchData();
    }

    /**
     * Retrieves the list of enrollments for the student
     */
    private void fetchData() {
        controller.GetStudentEnrollments(Repository.USERNAME);
    }

    @Override
    public void DisplayResult(List<EnrollmentModel> result) {
        // Sort such that pending enrollments come first
        Collections.sort(result, PendingEnrollmentComparator);

        StudentEnrollmentListAdapter adapter = new StudentEnrollmentListAdapter(this, result);
        listView.setAdapter(adapter);
    }

    /**
     * Navigate to the enrollment page when fab is clicked
     *
     * @param view The view that was clicked/tapped
     */
    public void requestEnrollment(View view) {
        Intent intent = new Intent(getApplicationContext(), EnrollmentActivity.class);
        startActivity(intent);
    }

    /**
     * Pending enrollments should appear before non-pending enrollments
     */
    private static Comparator<EnrollmentModel> PendingEnrollmentComparator = new Comparator<EnrollmentModel>() {
        @Override
        public int compare(EnrollmentModel lhs, EnrollmentModel rhs) {
            if (lhs.Pending == rhs.Pending) {
                return 0;
            } else if (lhs.Pending) {
                return -1;
            } else {
                return 1;
            }
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
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
        }

        return super.onOptionsItemSelected(item);
    }
}