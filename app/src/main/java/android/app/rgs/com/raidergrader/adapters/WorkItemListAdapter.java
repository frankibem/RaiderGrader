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
public class WorkItemListAdapter extends ArrayAdapter<WorkItemModel> {
    private List<WorkItemModel> workItem;
    private final Context mContext;
    public WorkItemListAdapter(Context context, List<WorkItemModel> aClass) {
        super(context, -1, aClass);

        workItem = aClass;
        mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        WorkItemModel item = workItem.get(position);

        LayoutInflater inflater = LayoutInflater.from(mContext);

        View rowView = inflater.inflate(R.layout.teacher_work_item_list_adapter, parent, false);
        //put the title
        TextView text_title = (TextView) rowView.findViewById(R.id.txt_title);
        text_title.setText(item.Title);
        //put the max point
        TextView text_score = (TextView) rowView.findViewById(R.id.txt_score);
        text_score.setText(Float.toString(item.MaxPoints));

        return rowView;
    }

}
