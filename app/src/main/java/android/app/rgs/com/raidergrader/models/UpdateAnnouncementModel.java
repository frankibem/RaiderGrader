package android.app.rgs.com.raidergrader.models;

/**
 * @author Frank Ibem.
 */

/**
 * This model is used to update the details of an announcement
 */
public class UpdateAnnouncementModel {
    public int Id;
    public String Title;
    public String Description;

    public UpdateAnnouncementModel() {
    }

    public UpdateAnnouncementModel(String description, int id, String title) {
        Description = description;
        Id = id;
        Title = title;
    }
}
