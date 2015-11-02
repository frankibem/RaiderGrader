package android.app.rgs.com.raidergrader.data_access;

import android.util.Pair;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;

public class RestUtil {
    /**
     * Returns a task that can be used to log a user into the application
     *
     * @param url      Url for authentication
     * @param formBody Name-Value pair Collection containing users username, password and grant_type
     * @throws IOException
     */
    public static RestTask obtainLoginTask(String url, List<Pair<String, String>> formBody)
            throws IOException {
        HttpURLConnection connection = createDefaultConnection(url);
        connection.setDoOutput(true);

        RestTask task = new RestTask(connection);
        task.setFormBody(formBody, Charset.defaultCharset());
        return task;
    }

    /**
     * Returns a RestTask for a HTTP GET
     *
     * @param url GET url
     * @throws MalformedURLException
     * @throws IOException
     */
    public static RestTask obtainGetTask(String url)
            throws IOException {
        HttpURLConnection connection = createDefaultConnection(url);
        attachAuthentication(connection);
        connection.setDoInput(true);

        return new RestTask(connection);
    }

    /**
     * Returns a RestTask for a HTTP POST
     *
     * @param url POST url
     * @param formData List of pairs representing form data
     * @throws MalformedURLException
     * @throws IOException
     */
    public static RestTask obtainFormPostTask(String url, List<Pair<String, String>> formData)
            throws IOException {
        HttpURLConnection connection = createDefaultConnection(url);
        attachAuthentication(connection);
        connection.setDoOutput(true);

        RestTask task = new RestTask(connection);
        task.setFormBody(formData, Charset.defaultCharset());
        return task;
    }

    /**
     * Returns a RestTask for a HTTP POST with a JSON body
     *
     * @param url POST url
     * @param jsonBody Body of the request in JSON format
     * @throws MalformedURLException
     * @throws IOException
     */
    public static RestTask obtainJSONPostTask(String url, String jsonBody)
            throws IOException {
        HttpURLConnection connection = createDefaultConnection(url);
        attachAuthentication(connection);
        connection.setDoOutput(true);

        RestTask task = new RestTask(connection);
        task.setFormBody(jsonBody);
        return task;
    }

    /**
     * Returns a RestTask for a HTTP POST with url-encoded form data
     *
     * @param url POST url
     * @param formData URL-encoded request body
     * @throws MalformedURLException
     * @throws IOException
     */
    public static RestTask obtainFormPutTask(String url, List<Pair<String, String>> formData)
            throws IOException {
        HttpURLConnection connection = createDefaultConnection(url);
        attachAuthentication(connection);
        connection.setRequestMethod("PUT");
        connection.setDoOutput(true);

        RestTask task = new RestTask(connection);
        task.setFormBody(formData, Charset.defaultCharset());
        return task;
    }

    /**
     * Returns a RestTask for a HTTP PUT with a JSON body
     *
     * @param url PUT url
     * @param jsonBody Body of the request in JSON format
     * @throws MalformedURLException
     * @throws IOException
     */
    public static RestTask obtainJSONPutTask(String url, String jsonBody)
            throws IOException {
        HttpURLConnection connection = createDefaultConnection(url);
        attachAuthentication(connection);
        connection.setRequestMethod("PUT");
        connection.setDoOutput(true);

        RestTask task = new RestTask(connection);
        task.setFormBody(jsonBody);
        return task;
    }

    /**
     * Returns a RestTask for a HTTP DELETE
     *
     * @param url DELETE url
     * @throws IOException
     */
    public static RestTask obtainDeleteTask(String url)
            throws IOException {
        HttpURLConnection connection = createDefaultConnection(url);
        attachAuthentication(connection);
        connection.setRequestMethod("DELETE");
        connection.setDoInput(true);

        return new RestTask(connection);
    }

    /**
     * Returns a RestTask for a HTTP DELETE with a JSON body
     *
     * @param url DELETE url
     * @param jsonBody Body of the request in JSON format
     * @throws MalformedURLException
     * @throws IOException
     */

    public static RestTask obtainJSONDeleteTask(String url, String jsonBody)
            throws IOException {
        HttpURLConnection connection = createDefaultConnection(url);
        attachAuthentication(connection);
        connection.setRequestMethod("DELETE");
        connection.setDoInput(true);

        RestTask task = new RestTask(connection);
        task.setFormBody(jsonBody);
        return task;
    }

    /**
     * Creates a connection with default time out values
     *
     * @param url Url of request
     * @throws IOException
     */
    private static HttpURLConnection createDefaultConnection(String url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) (new URL(url)).openConnection();
        connection.setReadTimeout(10000);
        connection.setConnectTimeout(15000);
        return connection;
    }

    /**
     * Attaches the user's access token to the request
     *
     * @param connection Connection to attach the authentication parameters to
     */
    private static void attachAuthentication(HttpURLConnection connection) {
        connection.setRequestProperty("Authorization", "Bearer " + Repository.ACCESS_TOKEN);
    }
}