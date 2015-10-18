package android.app.rgs.com.raidergrader.models;

/**
 * Model to represent an enrollment in a class
 */
public class EnrollmentModel {
    public int Id;
    public ClassModel Class;
    public boolean Pending;
    public StudentModel Student;
    public float Grade;
}