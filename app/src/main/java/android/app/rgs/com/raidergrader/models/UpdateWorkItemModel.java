package android.app.rgs.com.raidergrader.models;

/**
 * Created by Frank Ibem on 10/17/2015.
 */

/**
 * This model stores details that are used to update a work-item
 */
public class UpdateWorkItemModel {
    public int Id;
    public String Title;
    public String Description;
    public String DueDate;
    public float MaxPoints;
    public int Type;

    public UpdateWorkItemModel() {
    }

    public UpdateWorkItemModel(String description, String dueDate, int id, float maxPoints, String title, int type) {
        Description = description;
        DueDate = dueDate;
        Id = id;
        MaxPoints = maxPoints;
        Title = title;
        Type = type;
    }
}