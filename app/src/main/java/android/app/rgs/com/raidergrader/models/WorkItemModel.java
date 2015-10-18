package android.app.rgs.com.raidergrader.models;

/**
 * Created by Frank Ibem on 10/17/2015.
 */

/**
 * This model contains details about a work-item assigned in a class
 */
public class WorkItemModel {
    public int Id;
    public String Title;
    public String Description;
    public String DueDate;
    public float MaxPoints;
    public int Type;
    public ClassModel Class;

    public WorkItemModel() {
    }

    public WorkItemModel(ClassModel aClass, String description, String dueDate,
                         int id, float maxPoints, String title, int type) {
        Class = aClass;
        Description = description;
        DueDate = dueDate;
        Id = id;
        MaxPoints = maxPoints;
        Title = title;
        Type = type;
    }
}