/**
 * Represents a single appointment between a patient and a health
 * professional at a particular time.
 *
 * <p>An appointment holds a Patient object, a HealthProfessional object and a
 * TimeSlot. Because the professional field has the general type
 * HealthProfessional, one appointment class can link a patient to any of the
 * GP, specialist, nurse or dietitian objects.</p>
 *
 * @author Shi Jiaen (25259230)
 * @version 1.0
 */
public class Appointment implements Comparable<Appointment> {

    private final Patient patient;
    private final HealthProfessional professional;
    private final TimeSlot timeSlot;

    /**
     * Creates an appointment after checking that all required details are
     * present. An appointment can never be created in an incomplete state.
     *
     * @param patient      the patient who is booking
     * @param professional the health professional who will be seen
     * @param timeSlot     the time of the appointment
     * @throws IllegalArgumentException if any argument is null
     */
    public Appointment(Patient patient, HealthProfessional professional, TimeSlot timeSlot) {
        if (patient == null) {
            throw new IllegalArgumentException("An appointment requires a patient.");
        }
        if (professional == null) {
            throw new IllegalArgumentException("An appointment requires a health professional.");
        }
        if (timeSlot == null) {
            throw new IllegalArgumentException("An appointment requires a time slot.");
        }
        this.patient = patient;
        this.professional = professional;
        this.timeSlot = timeSlot;
    }

    /**
     * Returns the patient for this appointment.
     *
     * @return the patient
     */
    public Patient getPatient() {
        return patient;
    }

    /**
     * Returns the health professional for this appointment.
     *
     * @return the health professional
     */
    public HealthProfessional getProfessional() {
        return professional;
    }

    /**
     * Returns the time of this appointment.
     *
     * @return the time slot
     */
    public TimeSlot getTimeSlot() {
        return timeSlot;
    }

    /**
     * Checks whether this appointment is for a particular professional ID.
     *
     * @param professionalId the ID to compare with
     * @return true when the ID matches the professional's ID
     */
    public boolean matchesProfessionalId(String professionalId) {
        return professionalId != null && professional.getId().equals(professionalId.trim());
    }

    /**
     * Checks whether this appointment belongs to a particular patient phone
     * number.
     *
     * @param mobileNumber the number to compare with
     * @return true when the numbers match after separators are removed
     */
    public boolean matchesMobileNumber(String mobileNumber) {
        return patient.hasMobileNumber(mobileNumber);
    }

    /**
     * Checks whether this appointment already occupies a given professional
     * and time slot. This is the rule used to prevent double bookings.
     *
     * @param otherProfessional the professional to check
     * @param otherTimeSlot     the time slot to check
     * @return true when this appointment is for the same professional at the
     *         same time
     */
    public boolean isBookedFor(HealthProfessional otherProfessional, TimeSlot otherTimeSlot) {
        return professional.equals(otherProfessional) && timeSlot.equals(otherTimeSlot);
    }

    /**
     * Compares this appointment with another by time.
     *
     * <p>Only the time is compared, which is exactly what the clinic needs
     * when it lists a day from earliest to latest. Two appointments at the
     * same time for different professionals are deliberately considered
     * equal here; the brief allows them to appear in any order, and
     * Collections.sort keeps their original order because it is a stable
     * sort.</p>
     *
     * @param other the appointment to compare with
     * @return a negative number if this appointment is earlier, zero if the
     *         times are equal, and a positive number if it is later
     */
    @Override
    public int compareTo(Appointment other) {
        return timeSlot.compareTo(other.timeSlot);
    }

    /**
     * Returns the appointment's details, including the chosen health
     * professional's identifying information and role details.
     *
     * @return a readable description of the appointment
     */
    @Override
    public String toString() {
        return "Appointment at " + timeSlot
                + "\n  Patient            : " + patient
                + "\n  Health professional: " + professional.getSummary()
                + "\n                       " + professional.getRoleDetails();
    }
}
