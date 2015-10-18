package android.app.rgs.com.raidergrader.models;

/**
 * Created by Frank Ibem on 10/17/2015.
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