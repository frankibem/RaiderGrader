package android.app.rgs.com.raidergrader.models;

/**
 * Created by Frank Ibem on 10/17/2015.
 */

/**
 * This model contains details that are used to create a work-item
 */
public class CreateWorkItemModel {
    public String Title;
    public String Description;
    public String DueDate;
    public float MaxPoints;
    public int ClassId;
    public int Type;

    public CreateWorkItemModel() {
    }

    public CreateWorkItemModel(int classId, String description, String dueDate,
                               float maxPoints, String title, int type) {
        ClassId = classId;
        Description = description;
        DueDate = dueDate;
        MaxPoints = maxPoints;
        Title = title;
        Type = type;
    }
}