package android.app.rgs.com.raidergrader.models;

/**
 * @author Frank Ibem.
 */

/**
 * Base model for representing application users
 */
public class UserModel {
    public String FirstName;
    public String LastName;
    public String UserName;

    public UserModel() {
    }

    public UserModel(String firstName, String lastName, String userName) {
        FirstName = firstName;
        LastName = lastName;
        UserName = userName;
    }
}