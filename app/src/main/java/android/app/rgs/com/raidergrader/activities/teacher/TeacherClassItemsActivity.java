package android.app.rgs.com.raidergrader.activities.teacher;

/**
 * @author Joshua Hernandez
 */

import android.app.rgs.com.raidergrader.R;
import android.app.rgs.com.raidergrader.controllers.AccountController;
import android.app.rgs.com.raidergrader.controllers.ClassController;
import android.app.rgs.com.raidergrader.data_access.Repository;
import android.app.rgs.com.raidergrader.dialogs.DeleteModelFragment;
import android.app.rgs.com.raidergrader.models.ClassModel;
import android.app.rgs.com.raidergrader.models.ControllerCallback;
import android.app.rgs.com.raidergrader.models.DeleteModelInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class TeacherClassItemsActivity extends AppCompatActivity
        implements ControllerCallback {
    String[] data = {"Students", "Work items", "Announcements"};
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_class_items);

        listView = (ListView) findViewById(R.id.listView);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, data);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        Intent intent = new Intent(getApplicationContext(), TeacherStudentItemsActivity.class);
                        startActivity(intent);
                        break;
                    case 1:
                        Intent intent1 = new Intent(getApplicationContext(), TeacherWorkItemListActivity.class);
                        startActivity(intent1);
                        break;
                    case 2:
                        Intent intent2 = new Intent(getApplicationContext(), TeacherAnnouncementListActivity.class);
                        startActivity(intent2);
                        break;
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_delete_menu, menu);
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
        } else if (id == R.id.menu_edit) {
            // Navigate to activity for deleting class

        } else if (id == R.id.menu_delete) {
            final ClassModel currentClass = Repository.getCurrentClass();
            final ClassController controller = new ClassController(this, this);

            DeleteModelInterface deleter = new DeleteModelInterface() {
                @Override
                public void Delete() {
                    controller.DeleteClass(currentClass.Id);
                }
            };

            DeleteModelFragment deleteFragment = new DeleteModelFragment();
            deleteFragment.setTitle(String.format("Delete %s?", currentClass.Title));
            deleteFragment.setBody("This will delete all data associated with this class including announcements" +
                    " and work items and cannot be undone. Continue?");
            deleteFragment.setDeleter(deleter);

            deleteFragment.show(getSupportFragmentManager(), "delete_class");
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void DisplayResult(Object result) {
        finish();
    }
}