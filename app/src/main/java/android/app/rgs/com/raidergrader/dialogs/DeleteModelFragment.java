package android.app.rgs.com.raidergrader.dialogs;

import android.app.Dialog;
import android.app.rgs.com.raidergrader.models.DeleteModelInterface;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

/**
 * @author lauren
 */
public class DeleteModelFragment extends DialogFragment {
    private String title, body;

    public DeleteModelInterface getDeleter() {
        return deleter;
    }

    public void setDeleter(DeleteModelInterface deleter) {
        this.deleter = deleter;
    }

    private DeleteModelInterface deleter;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title);
        builder.setMessage(body);
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleter.Delete();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        return builder.create();
    }
}