package android.app.rgs.com.raidergrader.adapters;

import android.app.rgs.com.raidergrader.R;
import android.app.rgs.com.raidergrader.activities.teacher.TeacherClassWaitlistActivity;
import android.app.rgs.com.raidergrader.models.EnrollmentState;
import android.app.rgs.com.raidergrader.models.WaitListItem;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * @author Frank Ibem.
 */

/**
 * Adapter for wait-list activity
 */
public class WaitListAdapter
        extends ArrayAdapter<WaitListItem> {
    private Context context;
    private List<WaitListItem> viewModels;
    private LayoutInflater inflater;

    /**
     * Returns the data associated with the adapter
     */
    public List<WaitListItem> getViewModels() {
        return viewModels;
    }

    /**
     * Constructs an adapter for the given view-models
     *
     * @param context              Context to use for the adapter
     * @param enrollmentViewModels List of view-models to populate list with
     */
    public WaitListAdapter(Context context,
                           List<WaitListItem> enrollmentViewModels) {
        super(context, -1, enrollmentViewModels);
        this.context = context;
        this.viewModels = enrollmentViewModels;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        WaitListItem item = viewModels.get(position);
        View rowView = inflater.inflate(R.layout.accept_enrollment_list_item, parent, false);

        TextView name = (TextView) rowView.findViewById(R.id.name);
        name.setText(item.Name);
        TextView email = (TextView) rowView.findViewById(R.id.email);
        email.setText(item.Email);

        ImageView acceptImage = (ImageView) rowView.findViewById(R.id.accept);
        ImageView rejectImage = (ImageView) rowView.findViewById(R.id.reject);

        // Set color
        switch (item.State) {
            case ACCEPT:
                acceptImage.setImageResource(R.drawable.accept_selected);
                rejectImage.setImageResource(R.drawable.reject);
                break;
            case REJECT:
                acceptImage.setImageResource(R.drawable.accept);
                rejectImage.setImageResource(R.drawable.reject_selected);
                break;
        }

        // Set listeners for accept and reject buttons
        ImageView accept = (ImageView) rowView.findViewById(R.id.accept);
        accept.setOnClickListener(onAcceptClickListener);
        ImageView reject = (ImageView) rowView.findViewById(R.id.reject);
        reject.setOnClickListener(onRejectClickListener);

        rowView.setTag(item);
        return rowView;
    }

    private View.OnClickListener onAcceptClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            View listItem = (View) v.getParent();
            WaitListItem model = (WaitListItem) listItem.getTag();

            // Mark item for acceptance and update background color
            ImageView acceptImage = (ImageView) listItem.findViewById(R.id.accept);
            ImageView rejectImage = (ImageView) listItem.findViewById(R.id.reject);
            acceptImage.setImageResource(R.drawable.accept_selected);
            rejectImage.setImageResource(R.drawable.reject);

            model.State = EnrollmentState.ACCEPT;
        }
    };

    private View.OnClickListener onRejectClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            View listItem = (View) v.getParent();
            WaitListItem model = (WaitListItem) listItem.getTag();

            // Mark item for rejection and update background color
            ImageView acceptImage = (ImageView) listItem.findViewById(R.id.accept);
            ImageView rejectImage = (ImageView) listItem.findViewById(R.id.reject);
            acceptImage.setImageResource(R.drawable.accept);
            rejectImage.setImageResource(R.drawable.reject_selected);

            model.State = EnrollmentState.REJECT;
        }
    };
}