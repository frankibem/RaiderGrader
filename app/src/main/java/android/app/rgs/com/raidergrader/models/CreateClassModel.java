package android.app.rgs.com.raidergrader.models;

/**
 * @author Frank Ibem.
 */

/**
 * This model contains details that are used to create a class
 */
public class CreateClassModel {
    public String Title;
    public String Prefix;
    public int CourseNumber;
    public int Section;
    public String TeacherUserName;
    public GradeDistribution GradeDistribution;

    public CreateClassModel() {
    }


    public CreateClassModel(int courseNumber, GradeDistribution gradeDistribution, String prefix,
                            int section, String teacherUserName, String title) {
        CourseNumber = courseNumber;
        GradeDistribution = gradeDistribution;
        Prefix = prefix;
        Section = section;
        TeacherUserName = teacherUserName;
        Title = title;
    }
}