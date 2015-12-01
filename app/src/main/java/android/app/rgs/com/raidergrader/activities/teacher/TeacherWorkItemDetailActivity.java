package android.app.rgs.com.raidergrader.activities.teacher;

import android.app.rgs.com.raidergrader.R;
import android.app.rgs.com.raidergrader.controllers.AccountController;
import android.app.rgs.com.raidergrader.controllers.AnnouncementController;
import android.app.rgs.com.raidergrader.controllers.WorkItemController;
import android.app.rgs.com.raidergrader.data_access.Repository;
import android.app.rgs.com.raidergrader.dialogs.DeleteModelFragment;
import android.app.rgs.com.raidergrader.models.AnnouncementModel;
import android.app.rgs.com.raidergrader.models.ControllerCallback;
import android.app.rgs.com.raidergrader.models.DeleteModelInterface;
import android.app.rgs.com.raidergrader.models.WorkItemModel;
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
    private TeacherWorkItemDetailActivity activity;

    private WorkItemController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_work_item_detail);
        activity = this;

        controller = new WorkItemController(this, this);
    }

    // Navigate to work-item list when deletion is successful
    @Override
    public void DisplayResult(Object result) {
        Repository.setCurrentWorkItem(null);
        finish();
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
            Intent intent = new Intent(this, TeacherUpdateWorkItemActivity.class);
            startActivity(intent);

        }else if(id == R.id.menu_delete){
            final WorkItemModel currentWorkItem = Repository.getCurrentWorkItem();
            final WorkItemController controller = new WorkItemController(this, this);

            DeleteModelInterface deleter = new DeleteModelInterface() {
                @Override
                public void Delete() {
                    controller.DeleteWorkItem(currentWorkItem.Id);
                }
            };

            DeleteModelFragment deleteFragment = new DeleteModelFragment();
            deleteFragment.setTitle(String.format("Delete \"%s\"?", currentWorkItem.Title));
            deleteFragment.setBody("This action cannot be reverted. Continue?");
            deleteFragment.setDeleter(deleter);

            deleteFragment.show(getSupportFragmentManager(), "delete_class");
        }

        return super.onOptionsItemSelected(item);
    }
}