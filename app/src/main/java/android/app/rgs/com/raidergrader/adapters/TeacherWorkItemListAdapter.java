package android.app.rgs.com.raidergrader.adapters;

import android.app.rgs.com.raidergrader.R;
import android.app.rgs.com.raidergrader.models.WorkItemModel;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

/**
 * @author Claire Gray
 */
public class TeacherWorkItemListAdapter extends ArrayAdapter<WorkItemModel> {
    private List<WorkItemModel> workItems;
    private final Context mContext;

    public TeacherWorkItemListAdapter(Context context, List<WorkItemModel> workItems) {
        super(context, -1, workItems);

        this.workItems = workItems;
        mContext = context;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        WorkItemModel item = workItems.get(position);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View rowView = inflater.inflate(R.layout.teacher_work_item_list_item, parent, false);

        TextView textTitle = (TextView) rowView.findViewById(R.id.text_title);
        textTitle.setText(item.Title);

        TextView text_score = (TextView) rowView.findViewById(R.id.text_score);
        text_score.setText(String.format(Locale.getDefault(), "%.2f", item.MaxPoints));

        return rowView;
    }
}