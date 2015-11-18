package android.app.rgs.com.raidergrader.dialogs;

import android.app.Dialog;
import android.app.rgs.com.raidergrader.models.EnrollmentModel;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

/**
 * @author Frank Ibem.
 */

/**
 * This fragment is displayed when a student tries to view a class for which their enrollment is
 * still pending
 */
public class EnrollmentPendingFragment extends DialogFragment {
    private EnrollmentModel model;

    /**
     * Sets the model that is to used to create the fragment
     *
     * @param model Model containing details of a student's enrollment
     */
    public void setModel(EnrollmentModel model) {
        this.model = model;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Enrollment is pending");
        builder.setMessage(String.format("You cannot view the contents of \"%s\" because your enrollment has not " +
                "yet been approved", model.Class.Title));
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        return builder.create();
    }
}