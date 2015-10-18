package android.app.rgs.com.raidergrader.models;

/**
 * Created by Frank Ibem on 10/17/2015.
 */

/**
 * This model represents an announcement made in a class
 */
public class AnnouncementModel {
    public int Id;
    public String Title;
    public String Description;
    public String CreatedOn;
    public ClassModel Class;

    public AnnouncementModel() {
    }

    public AnnouncementModel(ClassModel aClass, String createdOn, String description, int id, String title) {
        Class = aClass;
        CreatedOn = createdOn;
        Description = description;
        Id = id;
        Title = title;
    }
}