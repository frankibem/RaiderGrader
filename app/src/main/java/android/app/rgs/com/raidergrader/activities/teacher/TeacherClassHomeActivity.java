package android.app.rgs.com.raidergrader.activities.teacher;

import android.app.rgs.com.raidergrader.R;
import android.app.rgs.com.raidergrader.adapters.TeacherClassListAdapter;
import android.app.rgs.com.raidergrader.controllers.ClassController;
import android.app.rgs.com.raidergrader.data_access.Repository;
import android.app.rgs.com.raidergrader.models.ClassModel;
import android.app.rgs.com.raidergrader.models.ControllerCallback;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

/**
 * @author Lauren Joness
 */

public class TeacherClassHomeActivity extends AppCompatActivity
        implements ControllerCallback<List<ClassModel>> {
    private ListView listView;
    private ClassController controller = new ClassController(this, this);
    private TeacherClassListAdapter adapter;
    private List<ClassModel> classes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_class_home);

        listView = (ListView) findViewById(R.id.listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ClassModel item = classes.get(position);
                Repository.setCurrentClass(item);

                Intent intent = new Intent(getApplicationContext(), TeacherClassItemsActivity.class);
                startActivity(intent);
            }
        });

        fetchData();
    }

    private void fetchData() {
        controller.GetTeacherClasses(Repository.USERNAME);
    }

    public void createClass(View view) {
        Intent intent = new Intent(this, TeacherCreateClassActivity.class);
        startActivity(intent);
    }

    @Override
    public void DisplayResult(List<ClassModel> result) {
        classes = result;

        adapter = new TeacherClassListAdapter(this, result);
        listView.setAdapter(adapter);
    }
}
