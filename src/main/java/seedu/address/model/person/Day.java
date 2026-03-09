package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Arrays;

public class Day {

    public static final String MESSAGE_CONSTRAINTS =
            "Lesson Day should only be a day of the week.";

    public String value;

    /**
     * Constructs a {@code Tuition Rate}.
     *
     * @param lessonDay A valid tuition rate.
     */
    public Day(String lessonDay) {
        requireNonNull(lessonDay);
        checkArgument(isValidDay(lessonDay), MESSAGE_CONSTRAINTS);
        value = lessonDay;
    }

    /**
     * Returns true if a given string is a valid tuition rate.
     */
    public static boolean isValidDay(String test) {
        return seedu.address.model.person.Day.Day.isValid(test);
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
        if (!(other instanceof seedu.address.model.person.Day)) {
            return false;
        }

        seedu.address.model.person.Day otherDay = (seedu.address.model.person.Day) other;
        return value.equals(otherDay.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    public enum Day {
        MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY;

        /**
         * Returns true if a given string matches any of the enum names (case-insensitive).
         */
        public static boolean isValid(String test) {
            return Arrays.stream(seedu.address.model.person.Day.Day.values())
                    .anyMatch(day -> day.name().equalsIgnoreCase(test));
        }
    }


}
