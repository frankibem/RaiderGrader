package android.app.rgs.com.raidergrader.models;

/**
 * @author Frank Ibem.
 */

/**
 * View-model for EnrollmentModels
 */
public class WaitListItem {
    public String Name;
    public String Email;
    public EnrollmentState State;

    /**
     * Create the view model from an existing model
     *
     * @param model EnrollmentModel to be converted to a view-model
     */
    public WaitListItem(EnrollmentModel model) {
        Email = model.Student.UserName;
        Name = String.format("%s, %s", model.Student.LastName, model.Student.FirstName);
        State = EnrollmentState.UNKNOWN;
    }
}