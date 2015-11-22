package android.app.rgs.com.raidergrader.activities.teacher;

import android.app.rgs.com.raidergrader.R;
import android.app.rgs.com.raidergrader.adapters.TeacherWorkItemListAdapter;
import android.app.rgs.com.raidergrader.controllers.AccountController;
import android.app.rgs.com.raidergrader.controllers.WorkItemController;
import android.app.rgs.com.raidergrader.data_access.Repository;
import android.app.rgs.com.raidergrader.models.ControllerCallback;
import android.app.rgs.com.raidergrader.models.WorkItemModel;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
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
 * @author Claire  Gray
 */

public class TeacherWorkItemListActivity extends AppCompatActivity
        implements ControllerCallback<List<WorkItemModel>> {

    private TextView emptyText;
    private ListView listView;
    private FloatingActionButton fab;
    private WorkItemController controller = new WorkItemController(this, this);

    private TeacherWorkItemListAdapter adapter;
    private List<WorkItemModel> workItems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_work_item_list);

        emptyText = (TextView) findViewById(R.id.text_empty);
        listView = (ListView) findViewById(R.id.listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                WorkItemModel model = workItems.get(position);

                Repository.setCurrentWorkItem(model);
                Intent intent = new Intent(getApplicationContext(), TeacherWorkItemDetailActivity.class);
                startActivity(intent);
            }
        });

        fab = (FloatingActionButton) findViewById(R.id.fab);
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
        List<WorkItemModel> workItems = Repository.getWorkItemList();
        if (workItems == null) {
            controller.GetWorkItemsForClass(Repository.getCurrentClass().Id);
        } else {
            DisplayResult(workItems);
        }
    }

    @Override
    public void DisplayResult(List<WorkItemModel> result) {
        Repository.setWorkItemList(result);
        if (result.isEmpty()) {
            emptyText.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
            return;
        }

        workItems = result;
        adapter = new TeacherWorkItemListAdapter(this, result);
        listView.setAdapter(adapter);

        emptyText.setVisibility(View.GONE);
        listView.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.refresh_logout_menu, menu);
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
        } else if (id == R.id.menu_refresh) {
            Repository.setWorkItemList(null);
            fetchData();
        }

        return super.onOptionsItemSelected(item);
    }
}