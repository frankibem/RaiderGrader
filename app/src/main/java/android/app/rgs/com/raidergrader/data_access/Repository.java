package android.app.rgs.com.raidergrader.data_access;

import android.app.rgs.com.raidergrader.models.AnnouncementModel;
import android.app.rgs.com.raidergrader.models.ClassModel;
import android.app.rgs.com.raidergrader.models.EnrollmentModel;
import android.app.rgs.com.raidergrader.models.StudentModel;
import android.app.rgs.com.raidergrader.models.WorkItemModel;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.List;

/**
 * @author Frank Ibem
 */
public class Repository {
    //TODO: Change to https in final application
    public static final String baseUrl = "http://raidergrader.azurewebsites.net/";
    public static final String PREFERENCES_KEY = "com.raidergrader.settings";

    public static final String ACCESS_TOKEN_KEY = "ACCESS_TOKEN";
    public static String ACCESS_TOKEN = null;

    public static final String USERNAME_KEY = "USERNAME";
    public static String USERNAME = "";

    private static ClassModel currentClass;
    private static WorkItemModel currentWorkItem;
    private static AnnouncementModel currentAnnouncement;
    private static StudentModel currentStudent;

    private static List<ClassModel> classList = null;
    private static List<WorkItemModel> workItemList = null;
    private static List<AnnouncementModel> announcementList = null;
    private static List<EnrollmentModel> studentEnrollmentList = null;

    public static ClassModel getCurrentClass() {
        return currentClass;
    }

    public static void setCurrentClass(ClassModel currentClass) {
        Repository.currentClass = currentClass;
    }

    public static WorkItemModel getCurrentWorkItem() {
        return currentWorkItem;
    }

    public static void setCurrentWorkItem(WorkItemModel currentWorkItem) {
        Repository.currentWorkItem = currentWorkItem;
    }

    public static AnnouncementModel getCurrentAnnouncement() {
        return currentAnnouncement;
    }

    public static void setCurrentAnnouncement(AnnouncementModel currentAnnouncement) {
        Repository.currentAnnouncement = currentAnnouncement;
    }

    public static StudentModel getCurrentStudent() {
        return currentStudent;
    }

    public static void setCurrentStudent(StudentModel currentStudent) {
        Repository.currentStudent = currentStudent;
    }

    public static List<ClassModel> getClassList() {
        return classList;
    }

    public static void setClassList(List<ClassModel> classList) {
        Repository.classList = classList;
    }

    public static List<WorkItemModel> getWorkItemList() {
        return workItemList;
    }

    public static void setWorkItemList(List<WorkItemModel> workItemList) {
        Repository.workItemList = workItemList;
    }

    public static List<AnnouncementModel> getAnnouncementList() {
        return announcementList;
    }

    public static void setAnnouncementList(List<AnnouncementModel> announcementList) {
        Repository.announcementList = announcementList;
    }

    public static List<EnrollmentModel> getStudentEnrollmentList() {
        return studentEnrollmentList;
    }

    public static void setStudentEnrollmentList(List<EnrollmentModel> studentEnrollmentList) {
        Repository.studentEnrollmentList = studentEnrollmentList;
    }

    /**
     * Clears all cached data
     */
    public static void clearData() {
        setCurrentClass(null);
        setCurrentAnnouncement(null);
        setCurrentStudent(null);
        setCurrentWorkItem(null);

        setAnnouncementList(null);
        setClassList(null);
        setStudentEnrollmentList(null);
        setWorkItemList(null);
    }

    /**
     * Saves the users credentials locally
     *
     * @param username The user's username
     * @param token    The access_token obtained for the user
     * @param context  Context through which the application sharedPreferences can be obtained
     */
    public static void saveUserCredentials(String username, String token, Context context) {
        ACCESS_TOKEN = token;
        USERNAME = username;

        SharedPreferences settings = context.getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(Repository.ACCESS_TOKEN_KEY, Repository.ACCESS_TOKEN);
        editor.putString(Repository.USERNAME_KEY, Repository.USERNAME);
        editor.apply();
    }

    /**
     * Clears the local copy of the user's credentials
     *
     * @param context Context through which the application sharedPreferences can be obtained
     */
    public static void clearUserCredentials(Context context) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.remove(Repository.ACCESS_TOKEN_KEY);
        editor.remove(Repository.USERNAME_KEY);
        editor.apply();
    }
}