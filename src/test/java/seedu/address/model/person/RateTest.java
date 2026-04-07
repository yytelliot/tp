package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class RateTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Rate(null));
    }

    @Test
    public void constructor_invalidRate_throwsIllegalArgumentException() {
        String invalidRate = "";
        assertThrows(IllegalArgumentException.class, () -> new Rate(invalidRate));
    }

    @Test
    public void constructor_leadingZeros_normalizesValue() {
        // Verification that internal 'value' is stored without leading zeros
        assertEquals("40", new Rate("0040").value);
        assertEquals("0", new Rate("000").value);
    }

    @Test
    public void isValidRate() {
        // null Rate number
        assertThrows(NullPointerException.class, () -> Rate.isValidRate(null));

        // invalid Rate numbers
        assertFalse(Rate.isValidRate(""), "Empty string should be invalid.");
        assertFalse(Rate.isValidRate(" "), "Spaces should be invalid.");
        assertFalse(Rate.isValidRate("45.00"), "Decimals should be invalid.");
        assertFalse(Rate.isValidRate("Rate"), "Non-numeric strings should be invalid.");
        assertFalse(Rate.isValidRate("$40"), "Symbols should be invalid.");
        assertFalse(Rate.isValidRate("-40"), "Negative numbers should be invalid.");

        // invalid Rate: boundary checks for max limit (5000)
        assertFalse(Rate.isValidRate("5001"), "Rate above 5000 should be invalid.");
        assertFalse(Rate.isValidRate("9999999999"), "Very large numbers should be invalid.");

        // valid Rate numbers
        assertTrue(Rate.isValidRate("0"), "Boundary Min (0) should be valid.");
        assertTrue(Rate.isValidRate("1"), "Small positive rate should be valid.");
        assertTrue(Rate.isValidRate("40"), "Standard rate should be valid.");
        assertTrue(Rate.isValidRate("5000"), "Boundary Max (5000) should be valid.");
        assertTrue(Rate.isValidRate("0040"), "Input with leading zeros should be valid.");

        // long tuition rate is invalid
        assertFalse(Rate.isValidRate("99999999999999"),
                "Error: 99999999999999 should not be allowed. Max Rate is 5000.");
    }

    @Test
    public void equals() {
        Rate rate = new Rate("40");

        // same values -> returns true
        assertTrue(rate.equals(new Rate("40")));

        // leading zeroes but same value -> returns true
        assertTrue(rate.equals(new Rate("000040")));

        // same object -> returns true
        assertTrue(rate.equals(rate));

        // null -> returns false
        assertFalse(rate.equals(null));

        // different types -> returns false
        assertFalse(rate.equals(40));

        // different values -> returns false
        assertFalse(rate.equals(new Rate("45")));
    }
}
