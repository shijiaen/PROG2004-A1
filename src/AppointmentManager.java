import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Manages the clinic's collection of appointments.
 *
 * <p>An ArrayList is used to store the appointments because the clinic needs
 * to keep every booking, including two appointments at the same time for
 * different professionals, and it needs to keep adding and removing entries
 * while the day runs. A sorted set would not be suitable: appointments are
 * compared by time only, so a set would treat two same-time appointments for
 * different professionals as duplicates and lose one of them.</p>
 *
 * <p>Every operation returns a message. A successful action starts with
 * "SUCCESS: " and a failed action starts with "WARNING: ", so the caller can
 * show the result to the user without the manager printing anything itself.</p>
 *
 * @author &lt;Your Name&gt;
 * @version 1.0
 */
public class AppointmentManager {

    private static final String SUCCESS_PREFIX = "SUCCESS: ";
    private static final String WARNING_PREFIX = "WARNING: ";
    private static final int MOBILE_DIGIT_COUNT = 10;

    private final ArrayList<Appointment> appointments;
    private final ClinicSchedule schedule;

    /**
     * Creates an appointment manager for a clinic schedule.
     *
     * @param schedule the clinic's daily schedule
     * @throws IllegalArgumentException if the schedule is null
     */
    public AppointmentManager(ClinicSchedule schedule) {
        if (schedule == null) {
            throw new IllegalArgumentException("A clinic schedule is required.");
        }
        this.schedule = schedule;
        this.appointments = new ArrayList<>();
    }

    /**
     * Adds a new appointment after checking all required information and the
     * clinic's double-booking rule.
     *
     * <p>The method accepts the general HealthProfessional type, so a caller
     * can book a GP, specialist, nurse or dietitian without the manager
     * needing to know which one it is.</p>
     *
     * @param patient      the patient who is booking
     * @param professional the health professional to be seen
     * @param timeSlot     the requested time
     * @return a success or warning message
     */
    public String addAppointment(Patient patient, HealthProfessional professional, TimeSlot timeSlot) {
        if (patient == null) {
            return WARNING_PREFIX + "an appointment requires a patient.";
        }
        if (professional == null) {
            return WARNING_PREFIX + "an appointment requires a health professional.";
        }
        if (timeSlot == null) {
            return WARNING_PREFIX + "an appointment requires a time slot.";
        }
        if (!schedule.containsSlot(timeSlot)) {
            return WARNING_PREFIX + timeSlot + " is not a clinic time slot. The clinic runs "
                    + schedule.getOpeningHoursText() + ".";
        }
        // Bookings are for the current day only, as required by the brief, so
        // the weekly roster is display information rather than a booking
        // rule here. The rule that must be enforced is no double booking.
        if (isProfessionalBooked(professional, timeSlot)) {
            return WARNING_PREFIX + professional.getSummary()
                    + " is already booked at " + timeSlot + ".";
        }

        appointments.add(new Appointment(patient, professional, timeSlot));
        return SUCCESS_PREFIX + "booked " + patient.getName() + " with "
                + professional.getSummary() + " at " + timeSlot + ".";
    }

    /**
     * Returns every appointment in the order it was added.
     *
     * @return a new list containing every appointment
     */
    public List<Appointment> getAllAppointments() {
        return new ArrayList<>(appointments);
    }

    /**
     * Returns the appointments for one health professional.
     *
     * @param professionalId the professional's ID
     * @return a list of matching appointments, or an empty list when there are
     *         no matches or the ID is blank
     */
    public List<Appointment> getAppointmentsForProfessional(String professionalId) {
        List<Appointment> matches = new ArrayList<>();
        if (professionalId == null || professionalId.trim().isEmpty()) {
            return matches;
        }
        String id = professionalId.trim();
        for (Appointment appointment : appointments) {
            if (appointment.matchesProfessionalId(id)) {
                matches.add(appointment);
            }
        }
        return matches;
    }

    /**
     * Returns the appointments for one patient phone number.
     *
     * @param mobileNumber the patient's mobile number; separators are allowed
     * @return a list of matching appointments, or an empty list when the
     *         number is invalid or there are no matches
     */
    public List<Appointment> getAppointmentsForPatient(String mobileNumber) {
        List<Appointment> matches = new ArrayList<>();
        String cleanedNumber = Validation.removePhoneSeparators(mobileNumber);
        if (!Validation.isDigitsOnly(cleanedNumber) || cleanedNumber.length() != MOBILE_DIGIT_COUNT) {
            return matches;
        }
        for (Appointment appointment : appointments) {
            if (appointment.matchesMobileNumber(cleanedNumber)) {
                matches.add(appointment);
            }
        }
        return matches;
    }

    /**
     * Returns a time-ordered copy of an appointment list.
     *
     * <p>The method takes a list as a parameter, so the same sorting operation
     * works on all appointments or on a filtered subset.</p>
     *
     * @param appointmentsToSort the appointments to sort; it is not modified
     * @return a new list sorted from earliest to latest
     */
    public List<Appointment> sortByTime(List<Appointment> appointmentsToSort) {
        List<Appointment> sorted = new ArrayList<>(appointmentsToSort);
        Collections.sort(sorted);
        return sorted;
    }

    /**
     * Cancels the appointment for a professional at a particular time.
     *
     * @param professionalId the professional's ID
     * @param timeSlot       the appointment time
     * @return a success or warning message
     */
    public String cancelAppointment(String professionalId, TimeSlot timeSlot) {
        if (professionalId == null || professionalId.trim().isEmpty()) {
            return WARNING_PREFIX + "a health professional ID is required to cancel an appointment.";
        }
        if (timeSlot == null) {
            return WARNING_PREFIX + "an appointment time is required to cancel an appointment.";
        }

        String id = professionalId.trim();
        for (int i = 0; i < appointments.size(); i++) {
            Appointment appointment = appointments.get(i);
            if (appointment.matchesProfessionalId(id) && appointment.getTimeSlot().equals(timeSlot)) {
                appointments.remove(i);
                return SUCCESS_PREFIX + "cancelled the " + timeSlot + " appointment for "
                        + appointment.getPatient().getName() + " with "
                        + appointment.getProfessional().getSummary() + ".";
            }
        }
        return WARNING_PREFIX + "no appointment found for health professional ID "
                + id + " at " + timeSlot + ".";
    }

    /**
     * Cancels an appointment using a time written as text.
     *
     * <p>This overloaded version demonstrates how a text command can be
     * converted into the TimeSlot object used by the main cancel operation.
     * Invalid text produces a warning instead of an exception.</p>
     *
     * @param professionalId the professional's ID
     * @param timeText       the appointment time in HH:mm form
     * @return a success or warning message
     */
    public String cancelAppointment(String professionalId, String timeText) {
        if (timeText == null || timeText.trim().isEmpty()) {
            return WARNING_PREFIX + "an appointment time is required to cancel an appointment.";
        }
        TimeSlot timeSlot;
        try {
            timeSlot = TimeSlot.parse(timeText);
        } catch (IllegalArgumentException error) {
            return WARNING_PREFIX + "cannot cancel the appointment: " + error.getMessage();
        }
        return cancelAppointment(professionalId, timeSlot);
    }

    /**
     * Cancels every appointment that belongs to a patient phone number.
     *
     * @param mobileNumber the patient's mobile number; separators are allowed
     * @return a success or warning message
     */
    public String cancelAppointmentsByPhone(String mobileNumber) {
        String cleanedNumber = Validation.removePhoneSeparators(mobileNumber);
        if (!Validation.isDigitsOnly(cleanedNumber) || cleanedNumber.length() != MOBILE_DIGIT_COUNT) {
            return WARNING_PREFIX + "a valid 10-digit patient mobile number is required.";
        }

        int cancelled = 0;
        // Walk backwards so that removing an element does not skip the next
        // one in the list.
        for (int i = appointments.size() - 1; i >= 0; i--) {
            if (appointments.get(i).matchesMobileNumber(cleanedNumber)) {
                appointments.remove(i);
                cancelled++;
            }
        }

        String formattedNumber = Patient.formatMobileNumber(cleanedNumber);
        if (cancelled == 0) {
            return WARNING_PREFIX + "no appointments found for mobile number "
                    + formattedNumber + ".";
        }
        String appointmentWord = cancelled == 1 ? "appointment" : "appointments";
        return SUCCESS_PREFIX + "cancelled " + cancelled + " " + appointmentWord
                + " for mobile number " + formattedNumber + ".";
    }

    /**
     * Formats an appointment list for display.
     *
     * @param heading the heading shown above the list
     * @param appointmentList the appointments to display
     * @return a formatted block of text
     */
    public String formatAppointments(String heading, List<Appointment> appointmentList) {
        StringBuilder output = new StringBuilder();
        output.append(heading).append(" (").append(appointmentList.size()).append(")").append("\n");

        if (appointmentList.isEmpty()) {
            output.append("  (no appointments)\n");
            return output.toString();
        }

        for (int i = 0; i < appointmentList.size(); i++) {
            Appointment appointment = appointmentList.get(i);
            output.append("  ").append(i + 1).append(". ")
                    .append(appointment.getTimeSlot()).append(" | ")
                    .append(appointment.getPatient().getName()).append(" (")
                    .append(appointment.getPatient().getFormattedMobileNumber()).append(") | ")
                    .append(appointment.getProfessional().getSummary())
                    .append("\n");
        }
        return output.toString();
    }

    /**
     * Checks whether a professional is already booked at a time.
     *
     * @param professional the professional to check
     * @param timeSlot     the time to check
     * @return true when a matching appointment exists
     */
    private boolean isProfessionalBooked(HealthProfessional professional, TimeSlot timeSlot) {
        for (Appointment appointment : appointments) {
            if (appointment.isBookedFor(professional, timeSlot)) {
                return true;
            }
        }
        return false;
    }
}
