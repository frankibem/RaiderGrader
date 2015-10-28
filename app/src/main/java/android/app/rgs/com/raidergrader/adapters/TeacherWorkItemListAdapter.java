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

/**
 * Created by Claire on 10/27/2015.
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

        //// TODO: 10/28/2015 Change 'txt...' to 'text...'
        //put the title
        TextView textTitle = (TextView) rowView.findViewById(R.id.text_title);
        textTitle.setText(item.Title);
        //put the max point
        TextView text_score = (TextView) rowView.findViewById(R.id.text_score);
        text_score.setText(Float.toString(item.MaxPoints));

        return rowView;
    }

}
