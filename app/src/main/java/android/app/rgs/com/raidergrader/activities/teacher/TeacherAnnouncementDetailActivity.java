package android.app.rgs.com.raidergrader.activities.teacher;

import android.app.rgs.com.raidergrader.R;
import android.app.rgs.com.raidergrader.controllers.AccountController;
import android.app.rgs.com.raidergrader.data_access.Repository;
import android.app.rgs.com.raidergrader.models.AnnouncementModel;
import android.app.rgs.com.raidergrader.utilities.TimeUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.joda.time.LocalDateTime;

import java.util.Locale;

public class TeacherAnnouncementDetailActivity extends AppCompatActivity {

    private AnnouncementModel announcement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_announcement_detail);

        announcement = Repository.getCurrentAnnouncement();

        TextView titleView = (TextView) findViewById(R.id.title);
        titleView.setText(announcement.Title);

        TextView descriptionView = (TextView) findViewById(R.id.description);
        descriptionView.setText(announcement.Description);

        TextView dateView = (TextView) findViewById(R.id.date);
        LocalDateTime dateTime = TimeUtils.GetLocalTime(announcement.CreatedOn);
        String timeStr = dateTime.toString("hh:mm a", Locale.getDefault())
                + "\n" + dateTime.toString("MMM d, yyyy", Locale.getDefault());
        dateView.setText(timeStr);
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
}
