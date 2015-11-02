package android.app.rgs.com.raidergrader.activities.teacher;

import android.app.rgs.com.raidergrader.R;
import android.app.rgs.com.raidergrader.adapters.TeacherAcceptedStudentListAdapter;
import android.app.rgs.com.raidergrader.controllers.EnrollmentController;
import android.app.rgs.com.raidergrader.data_access.Repository;
import android.app.rgs.com.raidergrader.models.ControllerCallback;
import android.app.rgs.com.raidergrader.models.EnrollmentModel;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author Lauren Joness
 */

public class TeacherClassAcceptedStudentsActivity extends AppCompatActivity
        implements ControllerCallback<List<EnrollmentModel>> {

    private ListView listView;
    private TeacherAcceptedStudentListAdapter adapter;
    private List<EnrollmentModel> students;
    private EnrollmentController controller = new EnrollmentController(this, this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_class_accepted_students);

        listView = (ListView) findViewById(R.id.listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EnrollmentModel item = students.get(position);
                Repository.setCurrentStudent(item.Student);

                Intent intent = new Intent(getApplicationContext(), TeacherStudentDetailActivity.class);
                startActivity(intent);
            }
        });

        fetchData();
    }

    private void fetchData() {
        controller.GetAcceptedEnrollment(Repository.getCurrentClass().Id);
    }

    @Override
    public void DisplayResult(List<EnrollmentModel> result) {
        students = result;
        Collections.sort(students, new EnrollmentComparator());
        adapter = new TeacherAcceptedStudentListAdapter(this, result);
        listView.setAdapter(adapter);
    }

    private class EnrollmentComparator implements Comparator<EnrollmentModel> {
        @Override
        public int compare(EnrollmentModel lhs, EnrollmentModel rhs) {
            return lhs.Student.LastName.compareTo(rhs.Student.LastName);
        }
    }
}
