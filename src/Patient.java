/**
 * Represents a patient who can book an appointment.
 *
 * <p>Patient is its own class rather than two loose fields inside
 * Appointment: a patient has a name and a contact number that belong
 * together, and keeping them in one object makes the appointment model
 * simpler and stops the same pair of values from being passed around
 * separately.</p>
 *
 * @author &lt;Your Name&gt;
 * @version 1.0
 */
public class Patient {

    private static final int MOBILE_DIGIT_COUNT = 10;

    private final String name;
    private final String mobileNumber;

    /**
     * Creates a patient after validating the supplied details.
     *
     * @param name         the patient's full name
     * @param mobileNumber the patient's mobile number; spaces, hyphens and
     *                     brackets are accepted
     * @throws IllegalArgumentException if the name is blank or the mobile
     *                                  number does not contain exactly ten
     *                                  digits
     */
    public Patient(String name, String mobileNumber) {
        Validation.requireText(name, "Patient name");

        String cleanedNumber = Validation.removePhoneSeparators(mobileNumber);
        if (!Validation.isDigitsOnly(cleanedNumber) || cleanedNumber.length() != MOBILE_DIGIT_COUNT) {
            throw new IllegalArgumentException("Patient mobile number must contain exactly 10 digits.");
        }

        this.name = name.trim();
        this.mobileNumber = cleanedNumber;
    }

    /**
     * Returns the patient's name.
     *
     * @return the patient name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the stored mobile number as ten digits.
     *
     * @return the mobile number without separators
     */
    public String getMobileNumber() {
        return mobileNumber;
    }

    /**
     * Returns the mobile number in a readable format.
     *
     * @return the number formatted as "0412 345 678"
     */
    public String getFormattedMobileNumber() {
        return formatMobileNumber(mobileNumber);
    }

    /**
     * Formats a ten-digit mobile number for display.
     *
     * @param mobileNumber a string containing exactly ten digits
     * @return the number formatted as "0412 345 678"
     * @throws IllegalArgumentException if the value is not exactly ten digits
     */
    public static String formatMobileNumber(String mobileNumber) {
        if (!Validation.isDigitsOnly(mobileNumber) || mobileNumber.length() != MOBILE_DIGIT_COUNT) {
            throw new IllegalArgumentException("A mobile number must contain exactly 10 digits.");
        }
        return mobileNumber.substring(0, 4)
                + " " + mobileNumber.substring(4, 7)
                + " " + mobileNumber.substring(7);
    }

    /**
     * Checks whether a supplied phone number belongs to this patient.
     *
     * <p>Separators are removed before the comparison, so the caller can
     * search with "0412 345 678" even though the number is stored as
     * "0412345678".</p>
     *
     * @param otherMobileNumber the number to compare with
     * @return true when both numbers contain the same ten digits
     */
    public boolean hasMobileNumber(String otherMobileNumber) {
        String cleanedNumber = Validation.removePhoneSeparators(otherMobileNumber);
        return mobileNumber.equals(cleanedNumber);
    }

    /**
     * Checks whether another object is a patient with the same name and
     * mobile number.
     *
     * @param other the object to compare with
     * @return true when both values match
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Patient)) {
            return false;
        }
        Patient patient = (Patient) other;
        return name.equals(patient.name) && mobileNumber.equals(patient.mobileNumber);
    }

    /**
     * Returns a hash code consistent with equals.
     *
     * @return a hash code based on the name and mobile number
     */
    @Override
    public int hashCode() {
        return (name.hashCode() * 31) + mobileNumber.hashCode();
    }

    /**
     * Returns the patient's name and formatted contact number.
     *
     * @return a readable description of the patient
     */
    @Override
    public String toString() {
        return name + " (mobile " + getFormattedMobileNumber() + ")";
    }
}
