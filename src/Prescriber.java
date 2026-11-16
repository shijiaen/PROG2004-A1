/**
 * Defines the behaviour shared by health professionals who can prescribe
 * medication.
 *
 * <p>Prescribing is not common to every health professional, so it does not
 * belong in HealthProfessional. Putting it in an interface lets the general
 * practitioner and the specialist share this capability without forcing
 * nurses or dietitians to inherit behaviour they do not have.</p>
 *
 * @author Shi Jiaen (25259230)
 * @version 1.0
 */
public interface Prescriber {

    /**
     * Prescribes a medication for a patient.
     *
     * @param patientName the name of the patient receiving the medication
     * @param medication  the name of the medication
     * @param dosage      how the medication should be taken
     * @return a message describing the prescription
     * @throws IllegalArgumentException if any text value is blank
     */
    String prescribe(String patientName, String medication, String dosage);
}
