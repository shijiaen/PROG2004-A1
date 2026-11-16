/**
 * Shared validation helpers used by the model classes.
 *
 * <p>The rules for text values, numeric IDs and phone numbers are written once
 * in this class so that every object is protected by the same checks. All
 * methods are static because a Validation object never needs to exist.</p>
 *
 * @author &lt;Your Name&gt;
 * @version 1.0
 */
public final class Validation {

    /**
     * Private constructor stops other classes from creating a Validation
     * object; this class only provides static helpers.
     */
    private Validation() {
        // No instances are needed.
    }

    /**
     * Checks that a text value contains at least one non-space character.
     *
     * @param value     the value to check
     * @param fieldName the field name used in the error message
     * @throws IllegalArgumentException if the value is null or blank
     */
    public static void requireText(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " cannot be empty.");
        }
    }

    /**
     * Checks whether a text value contains digits only.
     *
     * @param value the value to check
     * @return true when the value is not null, not empty and contains only 0-9
     */
    public static boolean isDigitsOnly(String value) {
        if (value == null || value.isEmpty()) {
            return false;
        }
        for (int i = 0; i < value.length(); i++) {
            char character = value.charAt(i);
            // Check the ASCII range explicitly so that only the digits 0-9
            // are accepted for IDs and phone numbers.
            if (character < '0' || character > '9') {
                return false;
            }
        }
        return true;
    }

    /**
     * Removes the separators that people commonly type in a phone number, so
     * that "0412 345 678", "0412-345-678" and "(0412) 345678" can all be
     * compared with the same stored value.
     *
     * @param value the text to clean; null is treated as an empty string
     * @return the text with spaces, hyphens and brackets removed
     */
    public static String removePhoneSeparators(String value) {
        if (value == null) {
            return "";
        }
        StringBuilder cleaned = new StringBuilder();
        for (int i = 0; i < value.length(); i++) {
            char character = value.charAt(i);
            if (character != ' ' && character != '-' && character != '(' && character != ')') {
                cleaned.append(character);
            }
        }
        return cleaned.toString();
    }
}
