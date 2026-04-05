package seedu.address.logic.commands;

import java.util.List;
import java.util.Objects;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Marks one or more students' payment status as paid.
 */
public class MarkCommand extends BatchCommand {

    public static final String COMMAND_WORD = "mark";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks the payment status of the student(s) identified by index number(s)"
            + " in the displayed student list as paid.\n"
            + "Parameters: INDEX [INDEX]... (must be positive integers)\n"
            + "Example: " + COMMAND_WORD + " 1 2 3";

    public static final String MESSAGE_MARK_PERSON_SUCCESS = "Marked student as paid: %1$s";
    public static final String MESSAGE_MARK_PERSONS_SUCCESS = "Marked %1$d students as paid: %2$s";
    public static final String MESSAGE_ALREADY_PAID = "This student has already been marked as paid.";

    /**
     * Creates a MarkCommand to mark the persons at {@code targetIndices} as paid.
     */
    public MarkCommand(List<Index> targetIndices) {
        super(targetIndices);
    }

    @Override
    protected void checkPreconditions(List<Person> targetPersons) throws CommandException {
        for (Person person : targetPersons) {
            if (person.isPaid()) {
                throw new CommandException(MESSAGE_ALREADY_PAID);
            }
        }
    }

    @Override
    protected void executeBatch(List<Person> targetPersons, Model model) {
        for (Person personToMark : targetPersons) {
            Person markedPerson = new Person(
                    personToMark.getName(), personToMark.getPhone(), personToMark.getEmail(),
                    personToMark.getAddress(), personToMark.getDay(), personToMark.getStartTime(),
                    personToMark.getEndTime(), personToMark.getRate(), true, personToMark.getTags());
            model.setPerson(personToMark, markedPerson);
        }
    }

    @Override
    protected String formatSuccessMessage(List<Person> processedPersons) {
        if (processedPersons.size() == 1) {
            return String.format(MESSAGE_MARK_PERSON_SUCCESS, Messages.format(processedPersons.get(0)));
        }
        return String.format(MESSAGE_MARK_PERSONS_SUCCESS, processedPersons.size(), joinNames(processedPersons));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof MarkCommand)) {
            return false;
        }
        MarkCommand otherMarkCommand = (MarkCommand) other;
        return getTargetIndices().equals(otherMarkCommand.getTargetIndices());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTargetIndices());
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndices", getTargetIndices())
                .toString();
    }
}
