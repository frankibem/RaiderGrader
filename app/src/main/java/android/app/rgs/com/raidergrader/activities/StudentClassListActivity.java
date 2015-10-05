package android.app.rgs.com.raidergrader.activities;

import android.app.ProgressDialog;
import android.app.rgs.com.raidergrader.R;
import android.app.rgs.com.raidergrader.adapters.ClassListAdapter;
import android.app.rgs.com.raidergrader.data_access.Repository;
import android.app.rgs.com.raidergrader.data_access.RestTask;
import android.app.rgs.com.raidergrader.data_access.RestUtil;
import android.app.rgs.com.raidergrader.view_models.ClassViewModel;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class StudentClassListActivity extends AppCompatActivity
        implements RestTask.ProgressCallback, RestTask.ResponseCallback {
    ProgressDialog mProgress;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_class_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listView = (ListView) findViewById(R.id.listView);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        try {
            RestTask task = RestUtil.obtainGetTask(Repository.baseUrl + "api/classes");
            task.setProgressCallback(this);
            task.setResponseCallback(this);

            task.execute();
            mProgress = ProgressDialog.show(this, "Loading", "Fetching your data", true);
        } catch (IOException e) {
            onRequestError(e);
        }
    }

    @Override
    public void onProgressUpdate(int progress) {

    }

    @Override
    public void onRequestSuccess(String response) {
        if (mProgress != null) {
            mProgress.dismiss();
        }

        Type listType = new TypeToken<List<ClassViewModel>>() {
        }.getType();
        Gson gson = new Gson();
        List<ClassViewModel> cvms = gson.fromJson(response, listType);

        ClassListAdapter adapter = new ClassListAdapter(getApplicationContext(), cvms);
        listView.setAdapter(adapter);
    }

    @Override
    public void onRequestError(Exception error) {
        if (mProgress != null) {
            mProgress.dismiss();
        }

        Toast.makeText(this, error.getMessage(), Toast.LENGTH_SHORT);
    }
}
