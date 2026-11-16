import java.util.List;

/**
 * Represents a dietitian.
 *
 * <p>A dietitian inherits the common professional data from
 * HealthProfessional and adds nutrition-specific data and behaviour, such as
 * creating a meal plan and calculating a body mass index (BMI).</p>
 *
 * @author Shi Jiaen (25259230)
 * @version 1.0
 */
public class Dietitian extends HealthProfessional {

    private final String dietarySpecialty;

    /**
     * Creates a dietitian with an explicit roster.
     *
     * @param id               the dietitian's numeric ID
     * @param name             the dietitian's full name
     * @param availableDays    the days the dietitian works
     * @param dietarySpecialty the dietitian's area of nutrition
     * @throws IllegalArgumentException if a shared value or the specialty is
     *                                  invalid
     */
    public Dietitian(String id, String name, List<Day> availableDays,
                     String dietarySpecialty) {
        super(id, name, availableDays);
        Validation.requireText(dietarySpecialty, "Dietary specialty");
        this.dietarySpecialty = dietarySpecialty.trim();
    }

    /**
     * Creates a dietitian who works the default Monday-to-Friday week.
     *
     * @param id               the dietitian's numeric ID
     * @param name             the dietitian's full name
     * @param dietarySpecialty the dietitian's area of nutrition
     */
    public Dietitian(String id, String name, String dietarySpecialty) {
        this(id, name, Day.weekdays(), dietarySpecialty);
    }

    /**
     * Returns the dietitian's area of nutrition.
     *
     * @return the dietary specialty
     */
    public String getDietarySpecialty() {
        return dietarySpecialty;
    }

    /**
     * Returns the professional type used in displays.
     *
     * @return "Dietitian"
     */
    @Override
    public String getProfessionalType() {
        return "Dietitian";
    }

    /**
     * Returns the details that are specific to a dietitian.
     *
     * @return the dietary specialty
     */
    @Override
    public String getRoleDetails() {
        return "Dietary specialty: " + dietarySpecialty;
    }

    /**
     * Creates a meal plan for a patient.
     *
     * @param patientName the patient's name
     * @param goal        the patient's nutrition goal
     * @return a message describing the meal plan
     */
    public String createMealPlan(String patientName, String goal) {
        Validation.requireText(patientName, "Patient name");
        Validation.requireText(goal, "Nutrition goal");
        return "Dietitian " + getName() + " created a meal plan for "
                + patientName.trim() + " with the goal: " + goal.trim() + ".";
    }

    /**
     * Calculates a patient's body mass index.
     *
     * @param weightKg     the patient's weight in kilograms
     * @param heightMetres the patient's height in metres
     * @return the BMI value
     * @throws IllegalArgumentException if the weight or height is not positive
     */
    public double calculateBMI(double weightKg, double heightMetres) {
        if (weightKg <= 0) {
            throw new IllegalArgumentException("Weight must be greater than zero.");
        }
        if (heightMetres <= 0) {
            throw new IllegalArgumentException("Height must be greater than zero.");
        }
        return weightKg / (heightMetres * heightMetres);
    }

    /**
     * Classifies a body mass index using the standard adult ranges.
     *
     * @param bmi the BMI value
     * @return the classification of the BMI
     * @throws IllegalArgumentException if the BMI is not positive
     */
    public String classifyBMI(double bmi) {
        if (bmi <= 0) {
            throw new IllegalArgumentException("BMI must be greater than zero.");
        }
        if (bmi < 18.5) {
            return "Underweight";
        }
        if (bmi < 25.0) {
            return "Healthy range";
        }
        if (bmi < 30.0) {
            return "Overweight";
        }
        return "Obese";
    }
}
