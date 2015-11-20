package android.app.rgs.com.raidergrader.activities.teacher;

import android.app.Activity;
import android.app.rgs.com.raidergrader.R;
import android.app.rgs.com.raidergrader.controllers.AccountController;
import android.app.rgs.com.raidergrader.controllers.AnnouncementController;
import android.app.rgs.com.raidergrader.data_access.Repository;
import android.app.rgs.com.raidergrader.dialogs.DeleteModelFragment;
import android.app.rgs.com.raidergrader.models.AnnouncementModel;
import android.app.rgs.com.raidergrader.models.ControllerCallback;
import android.app.rgs.com.raidergrader.models.DeleteModelInterface;
import android.app.rgs.com.raidergrader.utilities.TimeUtils;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.joda.time.LocalDateTime;

import java.util.Locale;

/**
 * @author lauren
 */

public class TeacherAnnouncementDetailActivity extends AppCompatActivity
implements ControllerCallback{

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
            Intent intent = new Intent(this, TeacherUpdateAnnouncementActivity.class);
            startActivity(intent);
            finish();
        }else if(id == R.id.menu_delete){
            // Delete work item
            TeacherAnnouncementDetailActivity activity = this;
            DeleteModelFragment deleteFragment = new DeleteModelFragment();
            deleteFragment.setTitle("Delete?");
            deleteFragment.setBody(String.format("You are about to delete \"%s\". This process cannot be reversed. Continue?"));
            deleteFragment.setDeleter(new DeleteModelInterface() {
                @Override
                public void Delete() {
//                    AnnouncementController controller = new AnnouncementController(activity, activity);
//                    controller.DeleteAnnouncement(Repository.getCurrentAnnouncement().Id);
                }
            });
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void DisplayResult(Object result) {

    }
}
