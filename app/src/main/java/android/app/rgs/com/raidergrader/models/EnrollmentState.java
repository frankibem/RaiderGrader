package android.app.rgs.com.raidergrader.models;

/**
 * @author Frank Ibem.
 */

/**
 * Enumeration to represent the states of an enrollment
 */
public enum EnrollmentState {
    /**
     * The enrollment is yet to be accepted/rejected
     */
    UNKNOWN,
    /**
     * The student has been accepted
     */
    ACCEPT,
    /**
     * The student has been rejected
     */
    REJECT
}