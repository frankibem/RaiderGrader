package android.app.rgs.com.raidergrader.models;

/**
 * Created by Frank Ibem on 10/16/2015.
 */

/**
 * This model is used to request as well as accept/reject enrollment of a
 * student into a class
 */
public class EnrollmentBindingModel {
    public String StudentUserName;
    public int ClassId;
    public boolean Accept;

    public EnrollmentBindingModel() {
    }

    public EnrollmentBindingModel(boolean accept, int classId, String studentUserName) {
        Accept = accept;
        ClassId = classId;
        StudentUserName = studentUserName;
    }
}