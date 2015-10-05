package android.app.rgs.com.raidergrader.data_access;

import android.app.rgs.com.raidergrader.view_models.ClassViewModel;

import java.util.List;

public class Repository {
    //TODO: Change to https in final application
    public static String baseUrl = "http://raidergrader.azurewebsites.net/";

    public static String ACCESS_TOKEN_KEY = "ACCESS_TOKEN";
    public static String ACCESS_TOKEN = null;

    public static List<ClassViewModel> studentClasses;
}