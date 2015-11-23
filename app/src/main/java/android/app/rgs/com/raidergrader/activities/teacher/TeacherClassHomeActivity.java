package android.app.rgs.com.raidergrader.activities.teacher;

import android.app.Activity;
import android.app.rgs.com.raidergrader.R;
import android.app.rgs.com.raidergrader.adapters.TeacherClassListAdapter;
import android.app.rgs.com.raidergrader.controllers.AccountController;
import android.app.rgs.com.raidergrader.controllers.ClassController;
import android.app.rgs.com.raidergrader.data_access.Repository;
import android.app.rgs.com.raidergrader.models.ClassModel;
import android.app.rgs.com.raidergrader.models.ControllerCallback;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

/**
 * @author Lauren Joness
 */

public class TeacherClassHomeActivity extends AppCompatActivity
        implements ControllerCallback<List<ClassModel>> {
    private ListView listView;
    private TextView emptyText;

    private ClassController controller = new ClassController(this, this);
    private TeacherClassListAdapter adapter;
    private List<ClassModel> classes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_class_home);

        final Activity activity = this;

        emptyText = (TextView) findViewById(R.id.text_empty);
        listView = (ListView) findViewById(R.id.listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ClassModel item = classes.get(position);
                Repository.setCurrentClass(item);

                Intent intent = new Intent(getApplicationContext(), TeacherClassItemsActivity.class);
                activity.startActivityForResult(intent, 2);
            }
        });

        fetchData();
    }

    private void fetchData() {
        List<ClassModel> classes = Repository.getClassList();

        // Only refresh if no data
        if (classes == null) {
            controller.GetTeacherClasses(Repository.USERNAME);
        } else {
            DisplayResult(classes);
        }
    }

    public void createClass(View view) {
        Intent intent = new Intent(this, TeacherCreateClassActivity.class);

        // 1 - new class was successfully created
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // New class was created - refresh data
        if (resultCode == 1 || resultCode == 2) {
            Repository.setClassList(null);
            fetchData();
        }
    }

    @Override
    public void DisplayResult(List<ClassModel> result) {
        // Cache class list
        Repository.setClassList(result);

        if (result.isEmpty()) {
            emptyText.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
            return;
        }

        classes = result;
        adapter = new TeacherClassListAdapter(this, result);
        listView.setAdapter(adapter);

        emptyText.setVisibility(View.GONE);
        listView.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.logout_menu, menu);
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