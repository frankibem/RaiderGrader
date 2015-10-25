package android.app.rgs.com.raidergrader.activities.teacher;

import android.app.rgs.com.raidergrader.R;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class TeacherWorkItemDetailActivity extends AppCompatActivity {
    private String[] items = {"Update work-item", "Assign Grades", "Delete"};
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_work_item_detail);

        listView = (ListView) findViewById(R.id.listView);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = null;
                switch (position) {
                    case 0:
                        intent = new Intent(getApplicationContext(), TeacherUpdateWorkItemActivity.class);
                        break;
                    case 1:
                        intent = new Intent(getApplicationContext(), TeacherGradeWorkItemActivity.class);
                        break;
                    case 2:
                        //// TODO: 10/24/2015 Implement dialog for deleting a work-item
                        break;
                }

                if (intent != null) {
                    startActivity(intent);
                }
            }
        });
    }
}