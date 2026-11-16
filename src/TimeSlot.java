/**
 * Represents a time of day to the nearest minute.
 *
 * <p>The clinic books appointments in fixed slots, and a day's appointments
 * must be listed from earliest to latest. Storing the time as a single
 * "minutes from midnight" value makes that ordering a simple integer
 * comparison, while the hour and minute fields keep the object easy to read
 * and validate.</p>
 *
 * @author &lt;Your Name&gt;
 * @version 1.0
 */
public class TimeSlot implements Comparable<TimeSlot> {

    private static final int MINUTES_PER_HOUR = 60;
    private static final int HOURS_PER_DAY = 24;

    private final int hour;
    private final int minute;

    /**
     * Creates a time slot from an hour and a minute.
     *
     * @param hour   the hour of the day, from 0 to 23
     * @param minute the minute of the hour, from 0 to 59
     * @throws IllegalArgumentException if the hour or minute is out of range
     */
    public TimeSlot(int hour, int minute) {
        if (hour < 0 || hour >= HOURS_PER_DAY) {
            throw new IllegalArgumentException("Hour must be between 0 and 23.");
        }
        if (minute < 0 || minute >= MINUTES_PER_HOUR) {
            throw new IllegalArgumentException("Minute must be between 0 and 59.");
        }
        this.hour = hour;
        this.minute = minute;
    }

    /**
     * Creates a time slot from text in the form HH:mm, for example "09:30".
     *
     * @param text the text to parse
     * @return the matching time slot
     * @throws IllegalArgumentException if the text is not a valid HH:mm time
     */
    public static TimeSlot parse(String text) {
        if (text == null) {
            throw new IllegalArgumentException("Time text cannot be null.");
        }
        String trimmed = text.trim();
        if (trimmed.length() != 5 || trimmed.charAt(2) != ':') {
            throw new IllegalArgumentException("Time must be written as HH:mm, for example 09:30.");
        }
        try {
            int parsedHour = Integer.parseInt(trimmed.substring(0, 2));
            int parsedMinute = Integer.parseInt(trimmed.substring(3, 5));
            return new TimeSlot(parsedHour, parsedMinute);
        } catch (NumberFormatException error) {
            throw new IllegalArgumentException("Time must be written as HH:mm, for example 09:30.");
        }
    }

    /**
     * Returns the hour of this slot.
     *
     * @return the hour, from 0 to 23
     */
    public int getHour() {
        return hour;
    }

    /**
     * Returns the minute of this slot.
     *
     * @return the minute, from 0 to 59
     */
    public int getMinute() {
        return minute;
    }

    /**
     * Returns this time as the number of minutes since midnight.
     *
     * <p>For example, 09:30 becomes 570. This is the value used when two time
     * slots are compared.</p>
     *
     * @return the number of minutes since midnight
     */
    public int getMinutesFromMidnight() {
        return (hour * MINUTES_PER_HOUR) + minute;
    }

    /**
     * Compares this time with another time.
     *
     * @param other the time to compare with
     * @return a negative number if this time is earlier, zero if the times are
     *         equal, and a positive number if this time is later
     */
    @Override
    public int compareTo(TimeSlot other) {
        int thisMinute = getMinutesFromMidnight();
        int otherMinute = other.getMinutesFromMidnight();
        if (thisMinute < otherMinute) {
            return -1;
        }
        if (thisMinute > otherMinute) {
            return 1;
        }
        return 0;
    }

    /**
     * Checks whether another object is a time slot at the same time.
     *
     * @param other the object to compare with
     * @return true when both objects represent the same minute of the day
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof TimeSlot)) {
            return false;
        }
        TimeSlot slot = (TimeSlot) other;
        return getMinutesFromMidnight() == slot.getMinutesFromMidnight();
    }

    /**
     * Returns a hash code consistent with equals.
     *
     * @return the number of minutes since midnight
     */
    @Override
    public int hashCode() {
        return getMinutesFromMidnight();
    }

    /**
     * Returns the time in HH:mm form.
     *
     * @return the formatted time, for example "09:30"
     */
    @Override
    public String toString() {
        return String.format("%02d:%02d", hour, minute);
    }
}
