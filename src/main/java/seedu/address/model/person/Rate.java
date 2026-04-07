package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's tuition rate in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidRate(String)}
 */
public class Rate {

    public static final int MAX_RATE = 5000; // Reasonable cap for hourly tutoring
    public static final String MESSAGE_CONSTRAINTS =
            "Tuition Rate should be a non-negative integer between 0 and " + MAX_RATE;

    public static final String VALIDATION_REGEX = "^\\d+$";

    public final String value;

    /**
     * Constructs a {@code Tuition Rate}.
     *
     * @param tuitionRate A valid tuition rate.
     */
    public Rate(String tuitionRate) {
        requireNonNull(tuitionRate);
        checkArgument(isValidRate(tuitionRate), MESSAGE_CONSTRAINTS);
        // Replace all leading zeros, but keep a single "0" if the string is just "000"
        this.value = tuitionRate.replaceFirst("^0+(?!$)", "");
    }

    /**
     * Returns true if a given string is a valid tuition rate.
     */
    public static boolean isValidRate(String test) {
        if (!test.matches(VALIDATION_REGEX)) {
            return false;
        }
        try {
            int val = Integer.parseInt(test);
            return val >= 0 && val <= MAX_RATE;
        } catch (NumberFormatException e) {
            return false; // Catches numbers longer than 10 digits
        }
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Rate)) {
            return false;
        }

        Rate otherRate = (Rate) other;
        return value.equals(otherRate.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
