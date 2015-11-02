package android.app.rgs.com.raidergrader.models;

/**
 * @author Frank Ibem.
 */

/**
 * This model is used to create a new announcement in a class
 */
public class CreateAnnouncementModel {
    public String Title;
    public String Description;
    public int ClassId;

    public CreateAnnouncementModel() {
    }

    public CreateAnnouncementModel(int classId, String description, String title) {
        ClassId = classId;
        Description = description;
        Title = title;
    }
}