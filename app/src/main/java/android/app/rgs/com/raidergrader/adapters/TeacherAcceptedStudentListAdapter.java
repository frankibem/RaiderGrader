package android.app.rgs.com.raidergrader.adapters;

import android.app.rgs.com.raidergrader.R;
import android.app.rgs.com.raidergrader.models.EnrollmentModel;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by lauren on 10/30/2015.
 */
public class TeacherAcceptedStudentListAdapter extends ArrayAdapter<EnrollmentModel> {

    private List<EnrollmentModel> students;
    private Context context;

    public TeacherAcceptedStudentListAdapter(Context context, List<EnrollmentModel> models){
        super(context, -1, models);

        this.context = context;
        students = models;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = LayoutInflater.from(context);
        View rowView = inflater.inflate(R.layout.teacher_accepted_student_list_item, parent, false);

        EnrollmentModel item = students.get(position);
        TextView name = (TextView) rowView.findViewById(R.id.text_name);
        name.setText(item.Student.LastName + ", " + item.Student.FirstName);

        TextView grade = (TextView) rowView.findViewById(R.id.text_grade);
        grade.setText(Float.toString(item.Grade));

        return rowView;
    }
}
