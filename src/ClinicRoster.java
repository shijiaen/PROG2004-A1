import java.util.ArrayList;
import java.util.List;

/**
 * Stores the clinic's health professionals in one collection.
 *
 * <p>A single ArrayList&lt;HealthProfessional&gt; holds general practitioners,
 * specialists, nurses and dietitians together. The roster can therefore work
 * with the whole team through the common supertype, while specific behaviour
 * is still available by looking up and checking a particular object.</p>
 *
 * @author &lt;Your Name&gt;
 * @version 1.0
 */
public class ClinicRoster {

    private final ArrayList<HealthProfessional> professionals;

    /**
     * Creates an empty roster.
     */
    public ClinicRoster() {
        professionals = new ArrayList<>();
    }

    /**
     * Adds a health professional to the roster.
     *
     * @param professional the professional to add
     * @throws IllegalArgumentException if the professional is null or their
     *                                  ID is already in use
     */
    public void addProfessional(HealthProfessional professional) {
        if (professional == null) {
            throw new IllegalArgumentException("A health professional is required.");
        }
        if (findById(professional.getId()) != null) {
            throw new IllegalArgumentException(
                    "A health professional with ID " + professional.getId() + " already exists.");
        }
        professionals.add(professional);
    }

    /**
     * Returns every professional in the order they were added.
     *
     * @return a new list containing the whole roster
     */
    public List<HealthProfessional> getAllProfessionals() {
        return new ArrayList<>(professionals);
    }

    /**
     * Finds a professional by ID.
     *
     * @param id the ID to search for
     * @return the matching professional, or null when no match exists
     */
    public HealthProfessional findById(String id) {
        if (id == null) {
            return null;
        }
        String trimmedId = id.trim();
        for (HealthProfessional professional : professionals) {
            if (professional.getId().equals(trimmedId)) {
                return professional;
            }
        }
        return null;
    }

    /**
     * Returns every professional who can prescribe medication.
     *
     * <p>The method checks each object with instanceof, which allows the
     * roster to discover the Prescriber capability at run time instead of
     * storing separate lists for each type.</p>
     *
     * @return a list of prescribers
     */
    public List<Prescriber> getPrescribers() {
        List<Prescriber> prescribers = new ArrayList<>();
        for (HealthProfessional professional : professionals) {
            if (professional instanceof Prescriber) {
                prescribers.add((Prescriber) professional);
            }
        }
        return prescribers;
    }

    /**
     * Returns the number of professionals on the roster.
     *
     * @return the roster size
     */
    public int size() {
        return professionals.size();
    }
}
