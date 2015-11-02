package android.app.rgs.com.raidergrader.utilities;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author Frank Ibem.
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