package android.app.rgs.com.raidergrader.dialogs;

import android.app.Dialog;
import android.app.rgs.com.raidergrader.models.ClassModel;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

/**
 * @author Frank Ibem.
 */

/**
 * Fragment that confirms that a class has been created
 */
public class ClassCreatedFragment extends DialogFragment {
    private ClassModel model;

    /**
     * Sets the model that is to be used to create the fragment
     *
     * @param model Model containing details of the created class
     */
    public void setModel(ClassModel model) {
        this.model = model;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(String.format("Class created - ID %d", model.Id));
        builder.setMessage(String.format("%s has been created with ID, %d", model.Title, model.Id));
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getActivity().setResult(1);
                getActivity().finish();
            }
        });
        return builder.create();
    }
}