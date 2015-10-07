package android.app.rgs.com.raidergrader.adapters;

import android.app.rgs.com.raidergrader.view_models.ClassViewModel;
import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ClassListAdapter extends ArrayAdapter<ClassViewModel> {
    private final List<ClassViewModel> classes;
    private final Context context;

    public ClassListAdapter(Context context, List<ClassViewModel> classes) {
        super(context, -1, classes);

        this.context = context;
        this.classes = classes;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);

        View rowView = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
        TextView textView = (TextView) rowView.findViewById(android.R.id.text1);

        ClassViewModel cvm = classes.get(position);
        textView.setText(cvm.Title);
        textView.setTextColor(Color.BLACK);
        textView.setEllipsize(TextUtils.TruncateAt.END);

        return rowView;
    }
}