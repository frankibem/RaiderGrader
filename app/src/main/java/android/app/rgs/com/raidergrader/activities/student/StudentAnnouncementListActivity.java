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
import android.widget.TextView;

import java.util.List;

/**
 * @author Michael Arroyo
 */

public class StudentAnnouncementListActivity extends AppCompatActivity
        implements ControllerCallback<List<AnnouncementModel>> {
    private ListView listView;
    private StudentAnnouncementListActivity activity;
    private TextView emptyText;
    private List<AnnouncementModel> announcements;

    private AnnouncementController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_announcement_list);
        activity = this;

        emptyText = (TextView) findViewById(R.id.text_empty);
        listView = (ListView) findViewById(R.id.listView);
        controller = new AnnouncementController(this, this);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AnnouncementModel announce = announcements.get(position);
                Repository.setCurrentAnnouncement(announce);

                Intent intent = new Intent(activity, StudentAnnouncementDetailActivity.class);
                startActivity(intent);
            }
        });

        fetchData();
    }

    private void fetchData() {
        List<AnnouncementModel> announcements = Repository.getAnnouncementList();
        if (announcements == null) {
            controller.GetAnnouncementsForClass(Repository.getCurrentClass().Id);
        } else {
            DisplayResult(announcements);
        }
    }

    @Override
    public void DisplayResult(List<AnnouncementModel> result) {
        // Cache list of announcements
        Repository.setAnnouncementList(result);
        if (result.isEmpty()) {
            emptyText.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
            return;
        }

        announcements = result;
        StudentAnnouncementListAdapter adapter = new StudentAnnouncementListAdapter(this, result);
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
            Repository.setAnnouncementList(null);
            fetchData();
        }

        return super.onOptionsItemSelected(item);
    }
}