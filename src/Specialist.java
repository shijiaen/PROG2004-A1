import java.util.List;

/**
 * Represents a medical specialist, such as a cardiologist or paediatrician.
 *
 * <p>A specialist inherits the common professional data from
 * HealthProfessional, adds a specialty and a base hospital, and can prescribe
 * medication as well as order diagnostic tests.</p>
 *
 * @author &lt;Your Name&gt;
 * @version 1.0
 */
public class Specialist extends HealthProfessional implements Prescriber {

    private final String specialty;
    private final String hospital;

    /**
     * Creates a specialist with an explicit roster.
     *
     * @param id            the specialist's numeric ID
     * @param name          the specialist's full name
     * @param availableDays the days the specialist works
     * @param specialty     the specialist's area, for example "Cardiologist"
     * @param hospital      the specialist's base hospital
     * @throws IllegalArgumentException if a shared value or a specialist
     *                                  value is invalid
     */
    public Specialist(String id, String name, List<Day> availableDays,
                      String specialty, String hospital) {
        super(id, name, availableDays);
        Validation.requireText(specialty, "Specialty");
        Validation.requireText(hospital, "Base hospital");
        this.specialty = specialty.trim();
        this.hospital = hospital.trim();
    }

    /**
     * Creates a specialist who works the default Monday-to-Friday week.
     *
     * @param id        the specialist's numeric ID
     * @param name      the specialist's full name
     * @param specialty the specialist's area
     * @param hospital  the specialist's base hospital
     */
    public Specialist(String id, String name, String specialty, String hospital) {
        this(id, name, Day.weekdays(), specialty, hospital);
    }

    /**
     * Returns the specialist's area of medicine.
     *
     * @return the specialty
     */
    public String getSpecialty() {
        return specialty;
    }

    /**
     * Returns the specialist's base hospital.
     *
     * @return the hospital name
     */
    public String getHospital() {
        return hospital;
    }

    /**
     * Returns the professional type used in displays.
     *
     * @return "Specialist" followed by the specialty
     */
    @Override
    public String getProfessionalType() {
        return "Specialist (" + specialty + ")";
    }

    /**
     * Returns the details that are specific to a specialist.
     *
     * @return the specialty and base hospital
     */
    @Override
    public String getRoleDetails() {
        return "Specialty: " + specialty + " | Base hospital: " + hospital;
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

    /**
     * Orders a diagnostic test for a patient.
     *
     * @param patientName the patient's name
     * @param testName    the test being ordered
     * @return a message describing the order
     */
    public String orderDiagnosticTest(String patientName, String testName) {
        Validation.requireText(patientName, "Patient name");
        Validation.requireText(testName, "Test name");
        return getSummary() + " orders " + testName.trim() + " for " + patientName.trim() + ".";
    }
}
