package android.app.rgs.com.raidergrader.activities.student;

import android.app.rgs.com.raidergrader.R;
import android.app.rgs.com.raidergrader.adapters.StudentWorkItemListAdapter;
import android.app.rgs.com.raidergrader.controllers.AccountController;
import android.app.rgs.com.raidergrader.controllers.WorkItemController;
import android.app.rgs.com.raidergrader.data_access.Repository;
import android.app.rgs.com.raidergrader.models.ControllerCallback;
import android.app.rgs.com.raidergrader.models.WorkItemModel;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

/**
 * @author Lauren Joness
 */

public class StudentWorkItemListActivity extends AppCompatActivity
        implements ControllerCallback<List<WorkItemModel>> {

    private ListView listView;
    private StudentWorkItemListActivity activity;
    private List<WorkItemModel> workitems;
    private WorkItemController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_work_item_list);
        activity = this;

        listView = (ListView) findViewById(R.id.listView);
        controller = new WorkItemController(this, this);
        fetchData();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                WorkItemModel item = workitems.get(position);
                Repository.setCurrentWorkItem(item);

                Intent intent = new Intent(activity, StudentWorkItemDetailActivity.class);
                startActivity(intent);
            }
        });
    }


    private void fetchData() {
        controller.GetWorkItemsForClass(Repository.getCurrentClass().Id);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.item_logout) {
            AccountController accountController = new AccountController(this, null);
            accountController.LogUserOut();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void DisplayResult(List<WorkItemModel> result){
        workitems = result;

        StudentWorkItemListAdapter adapter = new StudentWorkItemListAdapter(this, result);
        listView.setAdapter(adapter);
    }
}