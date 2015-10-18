package android.app.rgs.com.raidergrader.helpers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by Frank Ibem on 10/18/2015.
 */
public class JsonHelpers {
    /**
     * Returns a Gson object that's setup to serialize nulls
     * @return
     */
    public static Gson getGson(){
        GsonBuilder builder = new GsonBuilder();
        builder.serializeNulls();

        return builder.create();
    }
}