package android.app.rgs.com.raidergrader.dialogs;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.rgs.com.raidergrader.models.OnDatePickedListener;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

/**
 * @author Frank Ibem
 */

/**
 * Fragment that allows the user to select a Date
 */
public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {
    private int mYear, mMonth, mDay;
    private OnDatePickedListener dateListener;

    /**
     * Sets the start date for the DatePickerDialog
     *
     * @param year  The year
     * @param month The month (1 ~ 12)
     * @param day   The day of the month
     */
    public void setStartDate(int year, int month, int day) {
        mYear = year;
        mMonth = month;
        mDay = day;
    }

    /**
     * Sets the listener that should be called after the user has picked a date
     *
     * @param dateListener The listener to listen for the DateSet event
     */
    public void setDateListener(OnDatePickedListener dateListener) {
        this.dateListener = dateListener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new DatePickerDialog(getActivity(), this, mYear, mMonth, mDay);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        if (dateListener != null) {
            dateListener.onDatePicked(year, monthOfYear, dayOfMonth);
        }
    }
}