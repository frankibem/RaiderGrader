package android.app.rgs.com.raidergrader.activities.teacher;

import android.app.rgs.com.raidergrader.R;
import android.app.rgs.com.raidergrader.adapters.TeacherAnnouncementListAdapter;
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

public class TeacherAnnouncementListActivity extends AppCompatActivity
        implements ControllerCallback<List<AnnouncementModel>> {
    private ListView listView;
    private TextView emptyText;
    private TeacherAnnouncementListActivity activity;
    private List<AnnouncementModel> _announcements;

    private AnnouncementController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_announcement_list);
        activity = this;

        listView = (ListView) findViewById(R.id.listView);
        emptyText = (TextView) findViewById(R.id.text_empty);
        controller = new AnnouncementController(this, this);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AnnouncementModel announce = _announcements.get(position);
                Repository.setCurrentAnnouncement(announce);

                Intent intent = new Intent(activity, TeacherAnnouncementDetailActivity.class);
                startActivity(intent);
            }
        });

        fetchData();
    }

    public void createAnnouncement(View view) {
        Intent intent = new Intent(getApplicationContext(), TeacherCreateAnnouncementActivity.class);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // New announcement was created - refresh data
        if (resultCode == 1) {
            Repository.setAnnouncementList(null);
            fetchData();
        }
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
        Repository.setAnnouncementList(result);
        if (result.isEmpty()) {
            emptyText.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
            return;
        }

        emptyText.setVisibility(View.GONE);
        listView.setVisibility(View.VISIBLE);

        _announcements = result;
        TeacherAnnouncementListAdapter adapter = new TeacherAnnouncementListAdapter(this, result);
        listView.setAdapter(adapter);
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