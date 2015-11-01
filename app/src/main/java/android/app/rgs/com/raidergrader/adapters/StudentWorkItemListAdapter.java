package android.app.rgs.com.raidergrader.adapters;

import android.app.rgs.com.raidergrader.R;
import android.app.rgs.com.raidergrader.models.WorkItemModel;
import android.app.rgs.com.raidergrader.utilities.TimeUtils;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.joda.time.LocalDateTime;

import java.util.List;
import java.util.Locale;

/**
 * Created by lauren on 11/1/2015.
 */
public class StudentWorkItemListAdapter extends ArrayAdapter<WorkItemModel> {
    private List<WorkItemModel> workitems;
    private final Context context;

    public StudentWorkItemListAdapter(Context context, List<WorkItemModel> workitems){
        super(context, -1, workitems);
        this.context = context;
        this.workitems = workitems;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        WorkItemModel workitem = workitems.get(position);

        LayoutInflater inflater = LayoutInflater.from(context);
        View rowView = inflater.inflate(R.layout.student_announcement_list_item, parent, false);

        TextView titleView = (TextView) rowView.findViewById(R.id.title);
        titleView.setText(workitem.Title);

        TextView descriptionView = (TextView) rowView.findViewById(R.id.description);
        descriptionView.setText(workitem.Description);

        TextView dateView = (TextView) rowView.findViewById(R.id.date);
        LocalDateTime dateTime = TimeUtils.GetLocalTime(workitem.DueDate);
        String timeStr = dateTime.toString("hh:mm a", Locale.getDefault())
                + "\n" + dateTime.toString("MMM d, yyyy", Locale.getDefault());
        dateView.setText(timeStr);

        return rowView;
    }

}
