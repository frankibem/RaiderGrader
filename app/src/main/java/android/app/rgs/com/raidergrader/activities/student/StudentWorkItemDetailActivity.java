package android.app.rgs.com.raidergrader.activities.student;

import android.app.rgs.com.raidergrader.R;
import android.app.rgs.com.raidergrader.controllers.AccountController;
import android.app.rgs.com.raidergrader.data_access.Repository;
import android.app.rgs.com.raidergrader.models.WorkItemModel;
import android.app.rgs.com.raidergrader.utilities.TimeUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.joda.time.LocalDateTime;

/**
 * @author Michael Arroyo
 * @author Noris Rogers
 */

public class StudentWorkItemDetailActivity extends AppCompatActivity {
    private WorkItemModel workItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_work_item_detail);

        workItem = Repository.getCurrentWorkItem();

        TextView titleView = (TextView) findViewById(R.id.title);
        titleView.setText(workItem.Title);

        TextView descriptionView = (TextView) findViewById(R.id.description);
        descriptionView.setText(workItem.Description);

        TextView dateView = (TextView) findViewById(R.id.date);
        LocalDateTime dateTime = TimeUtils.GetLocalTime(workItem.DueDate);
        dateView.setText(TimeUtils.ToLocaleString(dateTime));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.logout_menu, menu);
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
        }

        return super.onOptionsItemSelected(item);
    }
}
