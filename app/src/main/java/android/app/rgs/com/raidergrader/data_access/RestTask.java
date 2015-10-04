package android.app.rgs.com.raidergrader.data_access;

import android.os.AsyncTask;
import android.util.Log;
import android.util.Pair;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.List;

public class RestTask extends AsyncTask<Void, Integer, Object> {
    private static final String TAG = "RestTask";

    /**
     * Users of RestTask should implement this interface in order to receive
     * results and respond to errors
     */
    public interface ResponseCallback {
        /**
         * This method processes the string returened by a RestTask
         *
         * @param response
         */
        void onRequestSuccess(String response);

        /**
         * This method processes an exception that occurred as a result of a http call
         *
         * @param error
         */
        void onRequestError(Exception error);
    }

    /**
     * Users of RestTask should implement this interface in order to track
     * the progress of a HTTP call
     */
    public interface ProgressCallback {
        void onProgressUpdate(int progress);
    }

    private HttpURLConnection mConnection;
    private String mFormBody;

    private WeakReference<ResponseCallback> mResponseCallback;
    private WeakReference<ProgressCallback> mProgressCallback;

    /**
     * Creates a RestTask with the given connection
     *
     * @param connection
     */
    public RestTask(HttpURLConnection connection) {
        this.mConnection = connection;
    }

    /**
     * Constructs the body of the request from a list of name-value pairs.
     * Also sets the 'Content-Type' header to 'application/x-www-form-encoded; charset=<chset>'
     *
     * @param formData
     * @throws UnsupportedEncodingException
     */
    public void setFormBody(List<Pair<String, String>> formData, Charset chset) throws UnsupportedEncodingException {
        if (formData == null) {
            mFormBody = null;
            return;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < formData.size(); i++) {
            sb.append(URLEncoder.encode(formData.get(i).first, chset.displayName()));
            sb.append("=");
            sb.append(URLEncoder.encode(formData.get(i).second, chset.displayName()));
            if (i != formData.size() - 1) {
                sb.append("&");
            }
        }

        mFormBody = sb.toString();

        mConnection.setRequestProperty("Content-Type", "application/x-www-form-encoded;charset=" + chset);
    }

    /**
     * Set the body of the request to a properly formatted JSON string.
     * Also sets the 'Content-Type' header to 'application/json'
     *
     * @param jsonBody JSON string representing the body of the request
     */
    public void setFormBody(String jsonBody) {
        if (jsonBody != null) {
            mFormBody = jsonBody;
            mConnection.setRequestProperty("Content-Type", "application/json");
        }
    }

    /**
     * Sets the callback for responses received
     *
     * @param callback
     */
    public void setResponseCallback(ResponseCallback callback) {
        mResponseCallback = new WeakReference<>(callback);
    }

    /**
     * Sets the callback for the progress of the HTTP call
     *
     * @param callback
     */
    public void setProgressCallback(ProgressCallback callback) {
        mProgressCallback = new WeakReference<>(callback);
    }

    private void writeFormData(String charset, OutputStream output) throws IOException {
        try {
            output.write(mFormBody.getBytes());
            output.flush();
        } finally {
            if (output != null) {
                output.close();
            }
        }
    }

    @Override
    protected Object doInBackground(Void... params) {
        String charset = Charset.defaultCharset().displayName();

        try {
            // If form body is supplied, add headers and write body to output stream
            if (mFormBody != null) {
                mConnection.setFixedLengthStreamingMode(mFormBody.length());
                OutputStream out = mConnection.getOutputStream();
                writeFormData(charset, out);
            }

            mConnection.connect();

            // Get response data
            int status = mConnection.getResponseCode();
            if (status >= 300) {
                String message = mConnection.getResponseMessage();
                //TODO: Replace with custom exception class to allow embedding status codes
                return new Exception(message);
            }

            InputStream in = mConnection.getInputStream();
            String encoding = mConnection.getContentEncoding();
            int contentLength = mConnection.getContentLength();
            if (encoding == null) {
                encoding = "UTF-8";
            }

            byte[] buffer = new byte[1024];
            int length = contentLength > 0 ? contentLength : 0;
            ByteArrayOutputStream out = new ByteArrayOutputStream(length);

            int downloadedBytes = 0;
            int read;
            while ((read = in.read(buffer)) != -1) {
                downloadedBytes += read;
                publishProgress((downloadedBytes * 100) / contentLength);
                out.write(buffer, 0, read);
            }

            return new String(out.toByteArray(), encoding);

        } catch (Exception e) {
            Log.w(TAG, e);
            return e;
        } finally {
            if (mConnection != null) {
                mConnection.disconnect();
            }
        }
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        // Update Progress UI
        if (mProgressCallback != null && mProgressCallback.get() != null) {
            mProgressCallback.get().onProgressUpdate(values[0]);
        }
    }

    @Override
    protected void onPostExecute(Object result) {
        if (mResponseCallback != null && mResponseCallback.get() != null) {
            final ResponseCallback cb = mResponseCallback.get();
            if (result instanceof String) {
                cb.onRequestSuccess((String) result);
                //TODO: Create exception class for http exceptions so status code can be embedded.
            } else if (result instanceof Exception) {
                cb.onRequestError((Exception) result);
            } else {
                cb.onRequestError(new IOException("Unknown Error Contacting Host"));
            }
        }
    }
}