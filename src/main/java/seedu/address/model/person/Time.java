package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

public class Time {

    public static final String MESSAGE_CONSTRAINTS =
            "Lesson Time should be in 24-hour (HH:mm) format e.g. 18:00.";
    public static final String VALIDATION_REGEX = "^([0-1][0-9]|2[0-3]):[0-5][0-9]$";
    public final String value;

    /**
     * Constructs a {@code LessonTime}.
     *
     * @param lessonTime A valid LessonTime number.
     */
    public Time(String lessonTime) {
        requireNonNull(lessonTime);
        checkArgument(isValidLessonTime(lessonTime), MESSAGE_CONSTRAINTS);
        value = lessonTime;
    }

    /**
     * Returns true if a given string is a valid LessonTime number.
     */
    public static boolean isValidLessonTime(String test) {
        return test.matches(VALIDATION_REGEX);
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
        if (!(other instanceof Time)) {
            return false;
        }

        Time otherTime = (Time) other;
        return value.equals(otherTime.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
