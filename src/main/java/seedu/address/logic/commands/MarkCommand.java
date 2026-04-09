package seedu.address.logic.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
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

    public static final String MESSAGE_MARK_SUCCESS = "Marked %1$d student(s) as paid: %2$s";
    public static final String MESSAGE_ALREADY_PAID = "This student has already been marked as paid: %1$s";
    public static final String MESSAGE_ALREADY_PAID_PLURAL =
            "These students have already been marked as paid: %1$s";

    /**
     * Creates a MarkCommand to mark the persons at {@code targetIndices} as paid.
     */
    public MarkCommand(List<Index> targetIndices) {
        super(targetIndices);
    }

    @Override
    protected void checkPreconditions(List<Person> targetPersons) throws CommandException {
        List<Index> distinctTargetIndices = getDistinctTargetIndices();
        List<String> alreadyPaidNames = new ArrayList<>();
        for (int i = 0; i < targetPersons.size(); i++) {
            Person person = targetPersons.get(i);
            if (person.isPaid()) {
                Index index = distinctTargetIndices.get(i);
                alreadyPaidNames.add("(" + index.getOneBased() + ") " + person.getName());
            }
        }
        if (!alreadyPaidNames.isEmpty()) {
            String names = String.join(", ", alreadyPaidNames);
            String message = alreadyPaidNames.size() == 1
                    ? String.format(MESSAGE_ALREADY_PAID, names)
                    : String.format(MESSAGE_ALREADY_PAID_PLURAL, names);
            throw new CommandException(message);
        }
    }

    @Override
    protected void executeBatch(List<Person> targetPersons, Model model) {
        for (Person targetPerson : targetPersons) {
            boolean isPaid = true;
            Person markedPerson = new Person(
                    targetPerson.getName(), targetPerson.getPhone(), targetPerson.getEmail(),
                    targetPerson.getAddress(), targetPerson.getDay(), targetPerson.getStartTime(),
                    targetPerson.getEndTime(), targetPerson.getRate(), isPaid, targetPerson.getTags());
            model.setPerson(targetPerson, markedPerson);
        }
    }

    @Override
    protected String formatSuccessMessage(List<Person> targetPersons) {
        return String.format(MESSAGE_MARK_SUCCESS, targetPersons.size(), joinNamesWithIndices(targetPersons));
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
