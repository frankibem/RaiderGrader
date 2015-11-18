package android.app.rgs.com.raidergrader.models;

/**
 * @author Frank Ibem.
 */

/**
 * Model to represent a student
 */
public class StudentModel extends UserModel {
    public float Grade;

    public StudentModel() {
    }

    public StudentModel(String firstName, String lastName, String userName, float grade) {
        super(firstName, lastName, userName);
        Grade = grade;
    }
}