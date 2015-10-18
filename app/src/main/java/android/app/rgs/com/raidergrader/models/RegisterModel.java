package android.app.rgs.com.raidergrader.models;

/**
 * This model contains details that are used to register a user in the Raider Grader system
 */
public class RegisterModel {
    public String FirstName;
    public String LastName;
    public String Email;
    public String Password;
    public String ConfirmPassword;
    public String Role;

    public RegisterModel() {
    }

    public RegisterModel(String confirmPassword, String email, String firstName, String lastName, String password, String role) {
        ConfirmPassword = confirmPassword;
        Email = email;
        FirstName = firstName;
        LastName = lastName;
        Password = password;
        Role = role;
    }
}