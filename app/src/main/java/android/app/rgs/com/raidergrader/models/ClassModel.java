package android.app.rgs.com.raidergrader.models;

/**
 * This model contains details about a class
 */
public class ClassModel {
    public int Id;
    public String Title;
    public String Prefix;
    public int CourseNumber;
    public int Section;
    public GradeDistribution GradeDistribution;
    public UserModel Teacher;

    public ClassModel() {
    }

    public ClassModel(int courseNumber, GradeDistribution gradeDistribution, int id,
                      String prefix, int section, UserModel teacher, String title) {
        CourseNumber = courseNumber;
        GradeDistribution = gradeDistribution;
        Id = id;
        Prefix = prefix;
        Section = section;
        Teacher = teacher;
        Title = title;
    }
}