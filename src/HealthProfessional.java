import java.util.ArrayList;
import java.util.List;

/**
 * Represents the information and behaviour shared by every health
 * professional in the service.
 *
 * <p>The class is abstract because a "plain" health professional with no
 * specialty does not exist in this domain. Declaring it abstract stops the
 * class from being instantiated directly and forces every subclass to
 * provide its professional type and its role-specific details.</p>
 *
 * @author Shi Jiaen (25259230)
 * @version 1.0
 */
public abstract class HealthProfessional {

    private final String id;
    private final String name;
    private final List<Day> availableDays;

    /**
     * Creates a health professional with an explicit weekly roster.
     *
     * @param id            the professional's numeric ID
     * @param name          the professional's full name
     * @param availableDays the days on which the professional works
     * @throws IllegalArgumentException if the ID is not numeric, the name is
     *                                  blank, or no available days are given
     */
    protected HealthProfessional(String id, String name, List<Day> availableDays) {
        if (!Validation.isDigitsOnly(id)) {
            throw new IllegalArgumentException("Health professional ID must contain numbers only.");
        }
        Validation.requireText(name, "Health professional name");
        if (availableDays == null || availableDays.isEmpty()) {
            throw new IllegalArgumentException("A health professional must have at least one available day.");
        }

        this.id = id;
        this.name = name.trim();
        // Copy the list so that later changes to the caller's list cannot
        // change the state of this object. Duplicate days are ignored because
        // the roster is a set of days.
        this.availableDays = new ArrayList<>();
        for (Day day : availableDays) {
            if (day == null) {
                throw new IllegalArgumentException("Available days cannot contain null.");
            }
            if (!this.availableDays.contains(day)) {
                this.availableDays.add(day);
            }
        }
    }

    /**
     * Creates a health professional who works the standard Monday-to-Friday
     * week. This is the second way of constructing the object: the caller
     * does not have to supply a roster when the default is appropriate.
     *
     * @param id   the professional's numeric ID
     * @param name the professional's full name
     * @throws IllegalArgumentException if the ID is not numeric or the name
     *                                  is blank
     */
    protected HealthProfessional(String id, String name) {
        this(id, name, Day.weekdays());
    }

    /**
     * Returns the professional's numeric ID.
     *
     * @return the ID
     */
    public String getId() {
        return id;
    }

    /**
     * Returns the professional's name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns a copy of the days on which this professional works.
     *
     * <p>A copy is returned so that external code cannot add or remove days
     * and leave the object in an invalid state.</p>
     *
     * @return a new list of available days
     */
    public List<Day> getAvailableDays() {
        return new ArrayList<>(availableDays);
    }

    /**
     * Checks whether this professional works on a given day.
     *
     * @param day the day to check
     * @return true when the professional is rostered on that day
     */
    public boolean isAvailableOn(Day day) {
        return availableDays.contains(day);
    }

    /**
     * Returns the available days as a readable, comma-separated string.
     *
     * @return the roster, for example "Monday, Wednesday, Friday"
     */
    public String getAvailableDaysAsText() {
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < availableDays.size(); i++) {
            if (i > 0) {
                text.append(", ");
            }
            text.append(availableDays.get(i));
        }
        return text.toString();
    }

    /**
     * Returns a one-line description that identifies this professional.
     *
     * @return the type, name and ID, for example
     *         "General Practitioner Dr Alice Nguyen (ID 1001)"
     */
    public String getSummary() {
        return getProfessionalType() + " " + name + " (ID " + id + ")";
    }

    /**
     * Returns the name of this professional's type.
     *
     * <p>Every subclass must supply its own value, which is what allows the
     * program to print a mixed collection of professionals correctly.</p>
     *
     * @return the professional type, for example "Nurse"
     */
    public abstract String getProfessionalType();

    /**
     * Returns the details that are specific to this professional's role.
     *
     * <p>The base class calls this method from toString(), so the shared
     * details are printed once and each subclass adds its own information.</p>
     *
     * @return a one-line description of the role-specific details
     */
    public abstract String getRoleDetails();

    /**
     * Returns all details of this professional in a readable form.
     *
     * @return the type, ID, name, roster and role-specific details
     */
    @Override
    public String toString() {
        return getProfessionalType() + " | ID: " + id
                + "\n  Name: " + name
                + "\n  Available days: " + getAvailableDaysAsText()
                + "\n  " + getRoleDetails();
    }

    /**
     * Compares this professional with another object.
     *
     * <p>Two health professionals are considered the same person when they
     * have the same ID. IDs are unique across the clinic, so this is the
     * natural identity for the class and it is what the appointment manager
     * uses when checking for a double booking.</p>
     *
     * @param other the object to compare with
     * @return true when the other object is a health professional with the
     *         same ID
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof HealthProfessional)) {
            return false;
        }
        HealthProfessional professional = (HealthProfessional) other;
        return id.equals(professional.id);
    }

    /**
     * Returns a hash code consistent with equals.
     *
     * @return a hash code based on the ID
     */
    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
