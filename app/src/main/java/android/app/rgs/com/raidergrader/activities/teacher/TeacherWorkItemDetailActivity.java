package android.app.rgs.com.raidergrader.activities.teacher;

import android.app.rgs.com.raidergrader.R;
import android.app.rgs.com.raidergrader.controllers.AccountController;
import android.app.rgs.com.raidergrader.controllers.WorkItemController;
import android.app.rgs.com.raidergrader.data_access.Repository;
import android.app.rgs.com.raidergrader.models.ControllerCallback;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class TeacherWorkItemDetailActivity extends AppCompatActivity
        implements ControllerCallback {
    private static final String UPDATE = "Update work-item";
    private static final String GRADE = "Assign Grades";
    private static final String DELETE = "Delete";

    private static String[] items = {UPDATE, GRADE, DELETE};
    private ListView listView;
    private TeacherWorkItemDetailActivity activity;

    private WorkItemController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_work_item_detail);
        activity = this;

        controller = new WorkItemController(this, this);

        listView = (ListView) findViewById(R.id.listView);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = null;
                switch ((String) parent.getSelectedItem()) {
                    case UPDATE:
                        intent = new Intent(getApplicationContext(), TeacherUpdateWorkItemActivity.class);
                        break;
                    case GRADE:
                        intent = new Intent(getApplicationContext(), TeacherGradeWorkItemActivity.class);
                        break;
                    case DELETE: {
                        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                        builder.setTitle(String.format("Delete \"%s\"", Repository.getCurrentWorkItem().Title));
                        builder.setMessage("This will delete all related grades assigned" +
                                "and cannot be undone. Continue?");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Delete current work-item
                                controller.DeleteWorkItem(Repository.getCurrentWorkItem().Id);
                            }
                        });
                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Do nothing
                            }
                        });

                        AlertDialog dialog = builder.create();
                        dialog.show();
                        return;
                    }
                }

                if (intent != null) {
                    startActivity(intent);
                }
            }
        });
    }

    // Navigate to work-item list when deletion is successful
    @Override
    public void DisplayResult(Object result) {
        Repository.setCurrentWorkItem(null);
        Intent intent = new Intent(this, TeacherWorkItemListActivity.class);
        startActivity(intent);
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
        }else if(id == R.id.menu_edit){
            // Navigate to activity for deleting work item

        }else if(id == R.id.menu_delete){
            // Place code to delete work item here
        }

        return super.onOptionsItemSelected(item);
    }
}