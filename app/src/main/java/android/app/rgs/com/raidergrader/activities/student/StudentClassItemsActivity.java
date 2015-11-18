package android.app.rgs.com.raidergrader.activities.student;

import android.app.rgs.com.raidergrader.R;
import android.app.rgs.com.raidergrader.controllers.AccountController;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class StudentClassItemsActivity extends AppCompatActivity {
    private static final String WORKITEMS = "Work items";
    private static final String ANNOUNCEMENTS = "Announcements";
    private static String[] data = {WORKITEMS, ANNOUNCEMENTS};
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_class_items);

        listView = (ListView) findViewById(R.id.listView);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, data);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = data[position];
                switch (item) {
                    case WORKITEMS:
                        Intent intentWorkItems = new Intent(getApplicationContext(), StudentWorkItemListActivity.class);
                        startActivity(intentWorkItems);
                        break;
                    case ANNOUNCEMENTS:
                        Intent intentAnnouncement = new Intent(getApplicationContext(), StudentAnnouncementListActivity.class);
                        startActivity(intentAnnouncement);
                        break;
                    default:
                }
            }
        });
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
