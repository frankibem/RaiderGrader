package android.app.rgs.com.raidergrader.models;

/**
 * Created by Frank Ibem on 10/17/2015.
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