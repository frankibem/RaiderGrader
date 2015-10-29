package android.app.rgs.com.raidergrader.activities.teacher;

import android.app.rgs.com.raidergrader.R;
import android.app.rgs.com.raidergrader.activities.student.StudentWorkItemDetailActivity;
import android.app.rgs.com.raidergrader.adapters.TeacherWorkItemListAdapter;
import android.app.rgs.com.raidergrader.controllers.WorkItemController;
import android.app.rgs.com.raidergrader.data_access.Repository;
import android.app.rgs.com.raidergrader.models.ControllerCallback;
import android.app.rgs.com.raidergrader.models.WorkItemModel;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

/**
 * @author Claire Gray
 */

public class TeacherWorkItemListActivity extends AppCompatActivity
        implements ControllerCallback<List<WorkItemModel>> {

    private ListView listView;
    private WorkItemController controller = new WorkItemController(this, this);

    private TeacherWorkItemListAdapter adapter;
    private List<WorkItemModel> workItems;

    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_work_item_list);

        listView = (ListView) findViewById(R.id.listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                WorkItemModel model = workItems.get(position);

                Repository.setCurrentWorkItem(model);
                Intent intent = new Intent(getApplicationContext(), StudentWorkItemDetailActivity.class);
                startActivity(intent);
            }
        });

        fab = (FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TeacherCreateWorkItemActivity.class);
                startActivity(intent);
            }
        });
        fetchData();
    }

    private void fetchData() {
        controller.GetWorkItemsForClass(Repository.getCurrentClass().Id);
    }

    @Override
    public void DisplayResult(List<WorkItemModel> result) {
        workItems = result;
        adapter = new TeacherWorkItemListAdapter(this, result);
        listView.setAdapter(adapter);
    }
}