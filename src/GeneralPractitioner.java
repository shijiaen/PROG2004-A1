import java.util.List;

/**
 * Represents a general practitioner (GP).
 *
 * <p>A GP inherits the ID, name and weekly roster from HealthProfessional,
 * adds the Medicare details that are relevant to general practice, and
 * implements the Prescriber interface because GPs can prescribe medication.</p>
 *
 * @author Shi Jiaen (25259230)
 * @version 1.0
 */
public class GeneralPractitioner extends HealthProfessional implements Prescriber {

    private final String medicareProviderNumber;
    private final boolean bulkBilling;

    /**
     * Creates a GP with an explicit roster.
     *
     * @param id                     the GP's numeric ID
     * @param name                   the GP's full name
     * @param availableDays          the days the GP works
     * @param medicareProviderNumber the GP's Medicare provider number
     * @param bulkBilling            whether the GP bulk bills patients
     * @throws IllegalArgumentException if a shared value or the provider
     *                                  number is invalid
     */
    public GeneralPractitioner(String id, String name, List<Day> availableDays,
                               String medicareProviderNumber, boolean bulkBilling) {
        super(id, name, availableDays);
        Validation.requireText(medicareProviderNumber, "Medicare provider number");
        this.medicareProviderNumber = medicareProviderNumber.trim();
        this.bulkBilling = bulkBilling;
    }

    /**
     * Creates a GP who works the default Monday-to-Friday week and bulk bills.
     *
     * @param id                     the GP's numeric ID
     * @param name                   the GP's full name
     * @param medicareProviderNumber the GP's Medicare provider number
     */
    public GeneralPractitioner(String id, String name, String medicareProviderNumber) {
        this(id, name, Day.weekdays(), medicareProviderNumber, true);
    }

    /**
     * Returns the GP's Medicare provider number.
     *
     * @return the provider number
     */
    public String getMedicareProviderNumber() {
        return medicareProviderNumber;
    }

    /**
     * Reports whether the GP bulk bills patients.
     *
     * @return true when the GP bulk bills
     */
    public boolean isBulkBilling() {
        return bulkBilling;
    }

    /**
     * Returns the professional type used in displays.
     *
     * @return "General Practitioner"
     */
    @Override
    public String getProfessionalType() {
        return "General Practitioner";
    }

    /**
     * Returns the details that are specific to a GP.
     *
     * @return the Medicare provider number and bulk-billing status
     */
    @Override
    public String getRoleDetails() {
        return "Medicare provider number: " + medicareProviderNumber
                + " | Bulk billing: " + (bulkBilling ? "yes" : "no");
    }

    /**
     * Prescribes a medication for a patient.
     *
     * @param patientName the patient's name
     * @param medication  the medication name
     * @param dosage      the dosage instructions
     * @return a message describing the prescription
     */
    @Override
    public String prescribe(String patientName, String medication, String dosage) {
        Validation.requireText(patientName, "Patient name");
        Validation.requireText(medication, "Medication");
        Validation.requireText(dosage, "Dosage");
        return getSummary() + " prescribes " + medication.trim() + " "
                + dosage.trim() + " for " + patientName.trim() + ".";
    }
}
