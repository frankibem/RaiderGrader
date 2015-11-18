package android.app.rgs.com.raidergrader.activities.teacher;

import android.app.rgs.com.raidergrader.R;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * @author Noris Rogers
 * @author Lauren Joness
 */

public class TeacherStudentItemsActivity extends AppCompatActivity {

    private ListView listView;
    private String[] studentItems = {"Current", "Wait list"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_student_items);

        listView = (ListView) findViewById(R.id.listView);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, studentItems);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        Intent asIntent = new Intent(getApplicationContext(), TeacherClassAcceptedStudentsActivity.class);
                        startActivity(asIntent);
                        break;
                    case 1:
                        Intent wlIntent = new Intent(getApplicationContext(), TeacherClassWaitlistActivity.class);
                        startActivity(wlIntent);
                        break;
                }
            }


        });
    }
}