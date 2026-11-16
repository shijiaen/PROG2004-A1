import java.util.ArrayList;
import java.util.List;

/**
 * Represents the fixed daily schedule of appointment slots at the clinic.
 *
 * <p>The schedule is built from an opening time, a closing time and a slot
 * length instead of being hard-coded as 08:00, 08:30, 09:00 and so on. If the
 * clinic changes its hours or its appointment length, only the constructor
 * arguments change and the rest of the system keeps working because it asks
 * this object whether a slot is valid.</p>
 *
 * @author Shi Jiaen (25259230)
 * @version 1.0
 */
public class ClinicSchedule {

    private static final int MINUTES_PER_HOUR = 60;

    private final String clinicName;
    private final TimeSlot openingTime;
    private final TimeSlot closingTime;
    private final int slotLengthMinutes;
    private final List<TimeSlot> slots;

    /**
     * Creates a clinic schedule and generates every appointment slot.
     *
     * <p>A slot is included when it starts at or after the opening time and
     * finishes at or before the closing time. For example, a clinic open from
     * 08:00 to 16:00 with 30-minute slots has its last appointment at
     * 15:30.</p>
     *
     * @param clinicName        the name of the clinic
     * @param openHour          the opening hour
     * @param openMinute        the opening minute
     * @param closeHour         the closing hour
     * @param closeMinute       the closing minute
     * @param slotLengthMinutes the length of one appointment in minutes
     * @throws IllegalArgumentException if the name is blank, the slot length
     *                                  is not positive, or the closing time
     *                                  is not after the opening time
     */
    public ClinicSchedule(String clinicName, int openHour, int openMinute,
                          int closeHour, int closeMinute, int slotLengthMinutes) {
        Validation.requireText(clinicName, "Clinic name");
        if (slotLengthMinutes <= 0) {
            throw new IllegalArgumentException("Slot length must be greater than zero minutes.");
        }

        this.openingTime = new TimeSlot(openHour, openMinute);
        this.closingTime = new TimeSlot(closeHour, closeMinute);
        if (closingTime.getMinutesFromMidnight() <= openingTime.getMinutesFromMidnight()) {
            throw new IllegalArgumentException("Closing time must be after opening time.");
        }

        this.clinicName = clinicName.trim();
        this.slotLengthMinutes = slotLengthMinutes;
        this.slots = new ArrayList<>();

        int startMinute = openingTime.getMinutesFromMidnight();
        int endMinute = closingTime.getMinutesFromMidnight();
        for (int minute = startMinute;
             minute + slotLengthMinutes <= endMinute;
             minute += slotLengthMinutes) {
            slots.add(new TimeSlot(minute / MINUTES_PER_HOUR, minute % MINUTES_PER_HOUR));
        }
    }

    /**
     * Creates the standard clinic schedule: 08:00 to 16:00 in 30-minute
     * slots. This is the second way of constructing the object and keeps the
     * common case short.
     *
     * @param clinicName the name of the clinic
     */
    public ClinicSchedule(String clinicName) {
        this(clinicName, 8, 0, 16, 0, 30);
    }

    /**
     * Returns the clinic's name.
     *
     * @return the clinic name
     */
    public String getClinicName() {
        return clinicName;
    }

    /**
     * Returns the opening time.
     *
     * @return the opening time
     */
    public TimeSlot getOpeningTime() {
        return openingTime;
    }

    /**
     * Returns the closing time.
     *
     * @return the closing time
     */
    public TimeSlot getClosingTime() {
        return closingTime;
    }

    /**
     * Returns the length of one appointment slot.
     *
     * @return the slot length in minutes
     */
    public int getSlotLengthMinutes() {
        return slotLengthMinutes;
    }

    /**
     * Returns a copy of every appointment slot in the day.
     *
     * @return a new list of slots, from earliest to latest
     */
    public List<TimeSlot> getSlots() {
        return new ArrayList<>(slots);
    }

    /**
     * Returns the number of appointment slots in the day.
     *
     * @return the slot count
     */
    public int getSlotCount() {
        return slots.size();
    }

    /**
     * Checks whether a time is one of the clinic's scheduled slots.
     *
     * @param timeSlot the time to check
     * @return true when the time is a valid slot
     */
    public boolean containsSlot(TimeSlot timeSlot) {
        return timeSlot != null && slots.contains(timeSlot);
    }

    /**
     * Returns the opening hours and slot length in a readable form.
     *
     * @return a description such as "08:00 to 16:00, 30-minute slots"
     */
    public String getOpeningHoursText() {
        return openingTime + " to " + closingTime + ", " + slotLengthMinutes + "-minute slots";
    }

    /**
     * Returns a readable description of the schedule.
     *
     * @return the clinic name, opening hours and number of slots
     */
    @Override
    public String toString() {
        return clinicName + " (" + getOpeningHoursText() + ", " + slots.size() + " slots)";
    }
}
