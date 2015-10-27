package android.app.rgs.com.raidergrader.activities.teacher;

import android.app.rgs.com.raidergrader.R;
import android.app.rgs.com.raidergrader.adapters.WorkItemListAdapter;
import android.app.rgs.com.raidergrader.controllers.WorkItemController;
import android.app.rgs.com.raidergrader.models.WorkItemModel;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.List;

public class TeacherWorkItemListActivity extends AppCompatActivity {

    private ListView listView;
    private WorkItemController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_work_item_list);

        List<WorkItemModel> = //need something to put in here;
        listView = (ListView)findViewById(R.id.listView);
        WorkItemListAdapter adapter = new WorkItemListAdapter(this,/* need something to put in here.*/ );
        listView.setAdapter(adapter);
    }

    public void onClickButton(View v) {

    }
}
