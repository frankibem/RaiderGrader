package android.app.rgs.com.raidergrader.activities.student;

import android.app.rgs.com.raidergrader.R;
import android.app.rgs.com.raidergrader.adapters.StudentAnnouncementListAdapter;
import android.app.rgs.com.raidergrader.controllers.AccountController;
import android.app.rgs.com.raidergrader.controllers.AnnouncementController;
import android.app.rgs.com.raidergrader.data_access.Repository;
import android.app.rgs.com.raidergrader.models.AnnouncementModel;
import android.app.rgs.com.raidergrader.models.ControllerCallback;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class StudentAnnouncementListActivity extends AppCompatActivity
        implements ControllerCallback<List<AnnouncementModel>> {
    private ListView listView;
    private StudentAnnouncementListActivity activity;
    private List<AnnouncementModel> announcements;

    private AnnouncementController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_announcement_list);
        activity = this;

        listView = (ListView) findViewById(R.id.listView);
        controller = new AnnouncementController(this, this);
        fetchData();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AnnouncementModel announce = announcements.get(position);
                Repository.setCurrentAnnouncement(announce);

                Intent intent = new Intent(activity, StudentAnnouncementDetailActivity.class);
                startActivity(intent);
            }
        });
    }

    private void fetchData() {
        controller.GetAnnouncementsforClass(Repository.getCurrentClass().Id);
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
    public void DisplayResult(List<AnnouncementModel> result) {
        announcements = result;

        StudentAnnouncementListAdapter adapter = new StudentAnnouncementListAdapter(this, result);
        listView.setAdapter(adapter);
    }
}
