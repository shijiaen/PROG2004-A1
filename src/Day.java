import java.util.ArrayList;
import java.util.List;

/**
 * Represents a day of the week on which a health professional can work.
 *
 * <p>An enum is used instead of a String because the roster only ever contains
 * real days. This makes invalid values impossible, gives type safety when the
 * days are compared, and allows the display name to live in one place.</p>
 *
 * @author Shi Jiaen (25259230)
 * @version 1.0
 */
public enum Day {

    /** Monday. */
    MONDAY("Monday"),
    /** Tuesday. */
    TUESDAY("Tuesday"),
    /** Wednesday. */
    WEDNESDAY("Wednesday"),
    /** Thursday. */
    THURSDAY("Thursday"),
    /** Friday. */
    FRIDAY("Friday"),
    /** Saturday. */
    SATURDAY("Saturday"),
    /** Sunday. */
    SUNDAY("Sunday");

    private final String displayName;

    /**
     * Creates a day constant with its printable name.
     *
     * @param displayName the name shown in the user interface
     */
    Day(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Returns the printable name of this day.
     *
     * @return the day name, for example "Monday"
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Returns the five standard Monday-to-Friday working days.
     *
     * <p>This is used as the sensible default when a health professional is
     * created without an explicit roster.</p>
     *
     * @return a new list containing Monday to Friday
     */
    public static List<Day> weekdays() {
        List<Day> days = new ArrayList<>();
        days.add(MONDAY);
        days.add(TUESDAY);
        days.add(WEDNESDAY);
        days.add(THURSDAY);
        days.add(FRIDAY);
        return days;
    }

    /**
     * Returns the printable name of the day.
     *
     * @return the display name
     */
    @Override
    public String toString() {
        return displayName;
    }
}
