package android.app.rgs.com.raidergrader.adapters;

import android.app.rgs.com.raidergrader.models.ClassModel;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * @author Lauren Joness
 */
public class TeacherClassListAdapter extends ArrayAdapter<ClassModel> {

    private List<ClassModel> classes;
    private Context context;

    public TeacherClassListAdapter(Context context, List<ClassModel> models) {
        super(context, -1, models);

        this.context = context;
        classes = models;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View rowView = inflater.inflate(android.R.layout.simple_list_item_1, parent);

        ClassModel item = classes.get(position);
        TextView title = (TextView) rowView.findViewById(android.R.id.text1);
        title.setText(item.Title);
        title.setEllipsize(TextUtils.TruncateAt.END);

        return rowView;
    }
}
