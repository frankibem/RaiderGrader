package android.app.rgs.com.raidergrader.utilities;

import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;

import java.util.Locale;

/**
 * Created by Frank Ibem on 10/22/2015.
 */
public class TimeUtils {
    /**
     * Converts the given string to a LocalDateTime object. The string must be in the ISO-8601 format
     *
     * @param time String containing the time to parse
     * @return
     */
    public static LocalDateTime GetLocalTime(String time) {
        DateTime dateTime = DateTime.parse(time);
        return dateTime.toLocalDateTime();
    }

    /**
     * Returns the string representation of the given date-time in ISO-8601 format
     *
     * @param localDateTime Local time to convert to string
     * @return
     */
    public static String ToISO8601String(LocalDateTime localDateTime) {
        return localDateTime.toDateTime().toString();
    }

    /**
     * Converts the given local date-time to a string with the format (HH:mm a MMM d, yyyy)
     * e.g. 11:34 AM Oct 5, 2016
     *
     * @param localDateTime
     * @return
     */
    public static String ToLocaleString(LocalDateTime localDateTime) {
        return localDateTime.toString("hh:mm a MMM d, yyyy", Locale.getDefault());
    }

    /**
     * Uses the given components to construct a LocalDateTime object
     *
     * @param year   The year
     * @param month  The time (1 - 12)
     * @param day    The day of the month
     * @param hour   The hour of the day (0 - 23)
     * @param minute The minute of the hour (0 - 59)
     * @return
     */
    public static LocalDateTime LocalDateTimeFromComponents(int year, int month, int day, int hour, int minute) {
        return new LocalDateTime(year, month, day, hour, minute);
    }
}