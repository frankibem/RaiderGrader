package android.app.rgs.com.raidergrader.activities.teacher;

import android.app.rgs.com.raidergrader.R;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

public class TeacherClassHomeActivity extends AppCompatActivity {
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_class_home);

        listView = (ListView) findViewById(R.id.listView);
    }

    public void createClass(View view){
        Intent intent = new Intent(this, TeacherCreateClassActivity.class);
        startActivity(intent);
    }
}
