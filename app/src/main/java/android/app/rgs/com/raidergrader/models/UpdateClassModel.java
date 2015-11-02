package android.app.rgs.com.raidergrader.models;

/**
 * @author Frank Ibem.
 */

/**
 * This model contains details that are used to update a class
 */
public class UpdateClassModel {
    public int Id;
    public String Title;
    public String Prefix;
    public int CourseNumber;
    public int Section;
    public GradeDistribution GradeDistribution;

    public UpdateClassModel() {
    }

    public UpdateClassModel(int courseNumber, GradeDistribution gradeDistribution, int id,
                            String prefix, int section, String title) {
        CourseNumber = courseNumber;
        GradeDistribution = gradeDistribution;
        Id = id;
        Prefix = prefix;
        Section = section;
        Title = title;
    }
}