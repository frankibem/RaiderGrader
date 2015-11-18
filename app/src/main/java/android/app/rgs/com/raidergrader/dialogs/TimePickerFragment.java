package android.app.rgs.com.raidergrader.dialogs;

/**
 * @author Frank Ibem.
 */

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.app.rgs.com.raidergrader.models.OnTimePickedListener;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.widget.TimePicker;

/**
 * Fragment that allows the user to select a Time
 */
public class TimePickerFragment extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {

    private int mHour, mMinute;
    private OnTimePickedListener timeListener;

    /**
     * Sets the start time for the TimePickerDialog
     *
     * @param hour   Hour of the day (0 ~ 23)
     * @param minute Minute of the day (0 ~ 59)
     */
    public void setStartTime(int hour, int minute) {
        mHour = hour;
        mMinute = minute;
    }

    /**
     * Sets the listener that should be called after the user has picked a time
     *
     * @param listener Listener to listen for the TimeSet event
     */
    public void setTimeListener(OnTimePickedListener listener) {
        timeListener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new TimePickerDialog(getActivity(), this, mHour, mMinute, true);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        if (timeListener != null) {
            timeListener.onTimePicked(mHour, mMinute);
        }
    }
}