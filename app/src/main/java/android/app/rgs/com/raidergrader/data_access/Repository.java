package android.app.rgs.com.raidergrader.data_access;

import android.app.rgs.com.raidergrader.models.ClassModel;

import java.util.List;

public class Repository {
    //TODO: Change to https in final application
    public static String baseUrl = "http://raidergrader.azurewebsites.net/";

    public static String ACCESS_TOKEN_KEY = "ACCESS_TOKEN";
    public static String ACCESS_TOKEN = null;

    public static String USERNAME_KEY = "USERNAME";
    public static String USERNAME = "";

    public static List<ClassModel> studentClasses;
    public static ClassModel selectedEnrollClass;
}