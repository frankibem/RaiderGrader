package android.app.rgs.com.raidergrader.adapters;

import android.app.rgs.com.raidergrader.R;
import android.app.rgs.com.raidergrader.models.EnrollmentModel;
import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class StudentEnrollmentListAdapter extends ArrayAdapter<EnrollmentModel> {
    private final List<EnrollmentModel> enrollments;
    private final Context context;

    public StudentEnrollmentListAdapter(Context context, List<EnrollmentModel> enrollments) {
        super(context, -1, enrollments);

        this.context = context;
        this.enrollments = enrollments;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        EnrollmentModel enroll = enrollments.get(position);

        LayoutInflater inflater = LayoutInflater.from(context);
        View rowView = inflater.inflate(R.layout.student_enrollment_list_item, parent, false);

        if (!enroll.Pending) {
            TextView pending = (TextView) rowView.findViewById(R.id.state);
            pending.setVisibility(View.GONE);
        }

        TextView textView = (TextView) rowView.findViewById(R.id.title);
        textView.setText(enroll.Class.Title);
        textView.setEllipsize(TextUtils.TruncateAt.END);

        return rowView;
    }
}