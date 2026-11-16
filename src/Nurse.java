import java.util.List;

/**
 * Represents a nurse.
 *
 * <p>A nurse inherits the common professional data from HealthProfessional and
 * adds ward and registration details. Nurses do not prescribe in this system,
 * so this class deliberately does not implement the Prescriber interface; it
 * provides nursing-specific behaviour instead.</p>
 *
 * @author &lt;Your Name&gt;
 * @version 1.0
 */
public class Nurse extends HealthProfessional {

    private final String ward;
    private final String registrationNumber;

    /**
     * Creates a nurse with an explicit roster.
     *
     * @param id                 the nurse's numeric ID
     * @param name               the nurse's full name
     * @param availableDays      the days the nurse works
     * @param ward               the ward where the nurse works
     * @param registrationNumber the nurse's registration number
     * @throws IllegalArgumentException if a shared value or a nursing value is
     *                                  invalid
     */
    public Nurse(String id, String name, List<Day> availableDays,
                 String ward, String registrationNumber) {
        super(id, name, availableDays);
        Validation.requireText(ward, "Ward");
        Validation.requireText(registrationNumber, "Nursing registration number");
        this.ward = ward.trim();
        this.registrationNumber = registrationNumber.trim();
    }

    /**
     * Creates a nurse who works the default Monday-to-Friday week.
     *
     * @param id                 the nurse's numeric ID
     * @param name               the nurse's full name
     * @param ward               the ward where the nurse works
     * @param registrationNumber the nurse's registration number
     */
    public Nurse(String id, String name, String ward, String registrationNumber) {
        this(id, name, Day.weekdays(), ward, registrationNumber);
    }

    /**
     * Returns the ward where the nurse works.
     *
     * @return the ward name
     */
    public String getWard() {
        return ward;
    }

    /**
     * Returns the nurse's registration number.
     *
     * @return the registration number
     */
    public String getRegistrationNumber() {
        return registrationNumber;
    }

    /**
     * Returns the professional type used in displays.
     *
     * @return "Nurse"
     */
    @Override
    public String getProfessionalType() {
        return "Nurse";
    }

    /**
     * Returns the details that are specific to a nurse.
     *
     * @return the ward and registration number
     */
    @Override
    public String getRoleDetails() {
        return "Ward: " + ward + " | Nursing registration: " + registrationNumber;
    }

    /**
     * Records that the nurse has collected a blood sample from a patient.
     *
     * @param patientName the patient's name
     * @return a message describing the procedure
     */
    public String collectBloodSample(String patientName) {
        Validation.requireText(patientName, "Patient name");
        return "Nurse " + getName() + " collected a blood sample from "
                + patientName.trim() + ".";
    }

    /**
     * Records a patient's vital signs.
     *
     * @param patientName the patient's name
     * @param heartRate   the heart rate in beats per minute
     * @param temperature the body temperature in degrees Celsius
     * @return a message describing the recorded observations
     * @throws IllegalArgumentException if the observations are outside a
     *                                  sensible range
     */
    public String recordVitalSigns(String patientName, int heartRate, double temperature) {
        Validation.requireText(patientName, "Patient name");
        if (heartRate <= 0 || heartRate > 300) {
            throw new IllegalArgumentException("Heart rate must be between 1 and 300 bpm.");
        }
        if (temperature < 30.0 || temperature > 45.0) {
            throw new IllegalArgumentException("Temperature must be between 30.0 and 45.0 degrees Celsius.");
        }
        return "Nurse " + getName() + " recorded vital signs for " + patientName.trim()
                + ": heart rate " + heartRate + " bpm, temperature "
                + String.format("%.1f", temperature) + " C.";
    }
}
