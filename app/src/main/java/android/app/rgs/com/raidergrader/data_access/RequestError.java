package android.app.rgs.com.raidergrader.data_access;

/**
 * @author Frank Ibem.
 */
public class RequestError {
    private int _statusCode;
    private String _message;

    public RequestError() {
    }

    public RequestError(int statusCode, String message) {
        _message = message;
        _statusCode = statusCode;
    }

    public String getMessage() {
        return _message;
    }

    public void setMessage(String message) {
        _message = message;
    }

    public int getStatusCode() {
        return _statusCode;
    }

    public void setStatusCode(int statusCode) {
        this._statusCode = statusCode;
    }
}
