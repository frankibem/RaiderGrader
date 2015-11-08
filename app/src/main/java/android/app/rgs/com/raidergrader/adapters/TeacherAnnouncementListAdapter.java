package android.app.rgs.com.raidergrader.adapters;

import android.app.rgs.com.raidergrader.R;
import android.app.rgs.com.raidergrader.models.AnnouncementModel;
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
 * @author Michael Arroyo
 */
public class TeacherAnnouncementListAdapter extends ArrayAdapter<AnnouncementModel> {
    private List<AnnouncementModel> announcements;
    private final Context context;

    public TeacherAnnouncementListAdapter(Context context, List<AnnouncementModel> announcements) {
        super(context, -1, announcements);

        this.context = context;
        this.announcements = announcements;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AnnouncementModel announce = announcements.get(position);

        LayoutInflater inflater = LayoutInflater.from(context);
        View rowView = inflater.inflate(R.layout.teacher_announcement_list_item, parent, false);

        TextView titleView = (TextView) rowView.findViewById(R.id.title);
        titleView.setText(announce.Title);

        TextView descriptionView = (TextView) rowView.findViewById(R.id.description);
        descriptionView.setText(announce.Description);

        TextView dateView = (TextView) rowView.findViewById(R.id.date);
        LocalDateTime dateTime = TimeUtils.GetLocalTime(announce.CreatedOn);
        String timeStr = dateTime.toString("hh:mm a", Locale.getDefault())
                + "\n" + dateTime.toString("MMM d, yyyy", Locale.getDefault());
        dateView.setText(timeStr);

        return rowView;
    }
}
