package android.app.rgs.com.raidergrader.models;

/**
 * Created by Frank Ibem on 10/17/2015.
 */

/**
 * A class to represent the weight distribution of the different types of work-items
 */
public class GradeDistribution {
    public float Exam;
    public float Quiz;
    public float Project;
    public float Homework;
    public float Other;

    public GradeDistribution() {
    }

    public GradeDistribution(float exam, float homework, float other, float project, float quiz) {
        Exam = exam;
        Homework = homework;
        Other = other;
        Project = project;
        Quiz = quiz;
    }
}