package android.app.rgs.com.raidergrader.models;

/**
 * @author Frank Ibem.
 */
public interface ControllerCallback<T> {
    void DisplayResult(T result);
}